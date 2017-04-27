/**
 * Jeff Blankenship, Adrian Ward-Manthey CS 490 Final Project Prof Williams
 *
 * Notes: The last packet in the list has packetsRemaining = 1 (not 0)
 *
 * THIS IS THE RECEIVER END, IT RECEIVES, ACKS, BUILDS A STRING FROM THE PACKET
 * LIST, AND DISPLAYS THE RESULTS.
 * 
 * THIS CLASS JUST LISTENS AND ACK FOR SENT PACKETS. IT'S HERE FOR TESTING. RUN
 * IT FROM ANOTHER INSTANCE OF THE IDE.
 * 
 *
 */
package p2p_fileshare;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

//COULD USE THIS CLASS TO ACT AS SERVER

class RDTack {

    static double ACKpercent = 90.0;   //probability of correct ACK (incorrect is simulated dropped packet)./
    static long   ACKtime = 2;         //base  time wait before ack (msec)
    static long   ACKdev = 1;          //amount of variability in time before ack (msec)

    public static void main(String[] args)
            throws UnknownHostException, SocketException, IOException, InterruptedException {
        System.out.println("RDTack running, will ack received packets as follows:");
        System.out.println("Percentage of packets to ack: " + ACKpercent);
        System.out.println("Average msec to ACK is: " + ACKtime + " +/- " + ACKdev);
        System.out.println("You can change those values in the code if you want.");
        System.out.println("\n \n THIS IS AN INFINITE LOOP, MANUAL KILL WHEN DONE.");

        ArrayList<Packet> packetList = new ArrayList<>();

        long delay = 0;
        //INITIAL SEQ'S ARE IMPOSSIBLE, TO ENSURE FIRST PACKET WON'T BE A FALSE REPEAT.
        //ALSO, THIS LETS US CHECK FOR REPEATED FIN PACKETS.
        String ACKseq = "9";
        String lastSeq = "9";
        boolean goodPacket = false;
        boolean drop = false;

        int ackPort = Globals.ACK_PORT;
        
        
        boolean finished = false;
        while (!finished) {
            System.out.println("==================================================");

            //RECEIVE NEXT PACKET
            DatagramSocket MSGSocket = new DatagramSocket(Globals.MSG_PORT);
            DatagramPacket MSGpacket = new DatagramPacket(new byte[128], 128);
            MSGSocket.receive(MSGpacket);
            MSGSocket.close();
            Packet newPacket = Packet.extractFromDatagram(MSGpacket);
            System.out.println("Seq " + newPacket.getSequence() + " received...");

            //CHECK FOR GOOD PACKET, ADD FAULTS
            goodPacket = true;
            if (newPacket.isSYN()) {
                System.out.println("SYN packet received, resetting sequence.");
            } else if (newPacket.getSequence().equals(lastSeq)) {
                goodPacket = false;
                System.out.println("Duplicate sequence received.");
            } else {
                System.out.println("New sequence received.");
            }
            if (newPacket.isFIN()) {
                System.out.println("FIN received.");
                if (packetList.isEmpty()) goodPacket = false;
            }

            if (Math.random() > ACKpercent / 100.0) {
                goodPacket = false;
                drop = true;
                System.out.println("ACKpercent --> Simulating dropped packet ");
            }

            //IF GOODPACKET, ADD TO PACKETLIST
            if (goodPacket) {
              System.out.println("Adding packet to packetList.");
              packetList.add(newPacket);
              //System.out.println("getData: " + newPacket.getData() );
              lastSeq = newPacket.getSequence();
            } 
            //DELAY BY THE Ack Time +/- ACKdev
                delay = (long) (ACKtime - ACKdev / 2 + ACKdev * Math.random());
                Thread.sleep(delay);
            ACKseq = newPacket.getSequence();
            //SEND THE ACK 
            ACKseq = newPacket.getSequence();
            DatagramSocket ACKSocket = new DatagramSocket();
            Packet ackPacket = new Packet("XX", "XX", ACKseq, "XX", "ACK");
            //InetAddress ackIPinet = InetAddress.getByName(newPacket.getIPAddress());
            InetAddress ackIPinet = InetAddress.getByName("192.168.1.46");
            DatagramPacket ACKpacket = makePacket(ackPacket.asString(), ackIPinet, Globals.ACK_PORT);
            
            if (drop) {
                System.out.println("drop, no ACK.");
                drop = false;
            } else {
                ACKSocket.send(ACKpacket);
                System.out.println(" and  ACK'd, delay: " + delay + "  ");
                lastSeq = newPacket.getSequence();
            }

            //PRINT THE MESSAGE IF THE PACKET IS THE LAST
            if (newPacket.isFIN() && !packetList.isEmpty()) {
                String message = "";
                //Toss the SYN off the front
                packetList.remove(0);
                while (packetList.size() > 2) {
                    String data = packetList.remove(0).getData();
                    //System.out.println("Adding:  " + data);
                    message += data;
                }
                //There's a FIN on the end, so toss that too.
                lastSeq = "9"; //set an impossible sequence so the receiver will know it's done
                packetList.remove(0);
                //now message is the complete HTTP message as a string.
                HTTP http = new HTTP(message);
                System.out.println("Complete Message received.");
                if ( http.getCode().equals("I") ) {System.out.println("It's an inform/update, call that method here.");}
                System.out.println("------------------------------------------------");
                if (http.isRequest() ) { System.out.println("Received an HTTP request.");}
                if (http.isResponse()) { System.out.println("Received an HTTP response.");}
                System.out.println( "Code:      " + http.getCode()       );
                System.out.println( "Phrase:    " + http.getPhrase()     );
                System.out.println( "IPaddress: " + http.getIPaddress()  );
                System.out.println( "Version:   " + http.getVersion()    );
                System.out.println( "Payload:   " + http.getPayload()    );
                //System.out.println(message);
                System.out.println("------------------------------------------------");
                
            }
        }
    }

    public void processHTTP(){
      
    }
    
    private static DatagramPacket makePacket(String msg, InetAddress destination, int port)
            throws UnknownHostException {
        byte[] data = msg.getBytes();
        int length = msg.length();
        if (length > 128) {
            length = 128;
        }
        DatagramPacket madePacket = new DatagramPacket(data, length);
        madePacket.setAddress(destination);
        madePacket.setPort(port);
        return madePacket;
    }

}
