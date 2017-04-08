/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * Notes:
 * The last packet in the list has packetsRemaining = 1  (not 0)
 * 
 * THIS CLASS JUST LISTENS AND ACK FOR SENT PACKETS.  IT'S HERE FOR TESTING.
 * RUN IT FROM ANOTHER INSTANCE OF THE IDE.
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
import static p2p_fileshare.UDPSender.makePacket;

class RDTack {
  
  static int ACKpercent = 90;   //probability of no ACK to simulate a dropped packet.
  static long ACKtime = 200;   //base  time wait before ack (msec)
  static long ACKdev  = 50;    //amount of variability in time before ack (msec)
  static String ackIPstring = Globals.JEFF_PC_IP;   //IP address to use, as a String
  
  
  public static void main(String[] args) 
  throws UnknownHostException, SocketException, IOException, InterruptedException {
    System.out.println("RDTack running, will ack received packets as follows:");
    System.out.println("Percentage of packets to ack: " + ACKpercent);
    System.out.println("Average msec to ACK is: " + ACKtime + " +/- " + ACKdev);
    System.out.println("You can change those values in the code if you want.");
    System.out.println("\n \n THIS IS AN INFINITE LOOP, MANUAL KILL WHEN DONE.");
    
    ArrayList<Packet> packetList = new ArrayList<>();
    long delay = 0;
    String ACKseq = "";
    String oldACKseq = "";
    
    int ackPort = Globals.ACK_PORT;
    InetAddress ackIPinet = InetAddress.getByName(ackIPstring);
    boolean finished = false;
    while (!finished){
        //RECEIVE NEXT PACKET
          DatagramSocket MSGSocket = new DatagramSocket(Globals.MSG_PORT,ackIPinet);
          DatagramPacket MSGpacket = new DatagramPacket(new byte[128], 128);
          MSGSocket.receive(MSGpacket);
          MSGSocket.close();
          Packet newPacket = Packet.extractFromDatagram(MSGpacket);
          
          
          
          
          //System.out.println("Received sequence " + newPacket.getSequence());
        //DELAY BY THE Ack Time +/- ACKdev
          delay = (long) (ACKtime - ACKdev/2 + ACKdev * Math.random());
          Thread.sleep(delay);
        //SET THE ACK SEQ CORRECT ACKpercent of the time
          if ( Math.random() < ACKpercent/100.0) {
            ACKseq = newPacket.getSequence();
            //System.out.println("Sending good ACK");
          } else {
            if (newPacket.getSequence().equals("0")) ACKseq = "1";
            if (newPacket.getSequence().equals("1")) ACKseq = "0";
          }
        //SEND THE ACK WITH THE CHOSEN SEQ  
            DatagramSocket ACKSocket = new DatagramSocket();
            Packet ackPacket = new Packet("XX", "XX", ACKseq, "XX", "ACK"); 
            DatagramPacket ACKpacket = makePacket(ackPacket.asString(), ackIPinet, Globals.ACK_PORT);
            ACKSocket.send(ACKpacket);
            System.out.print("\nSeq " + newPacket.getSequence() + " received.  Seq "+ ACKseq+ " ACK'd, delay: " + delay +"  ");
            if (ACKseq.equals(newPacket.getSequence() ) ){
                System.out.println();
              }else{
                System.out.println("\t WRONG ACK SENT.");
              }
      }
  }
  
  private static DatagramPacket makePacket(String msg, InetAddress destination, int port) 
  throws UnknownHostException{
    byte[] data = msg.getBytes();
    int length = msg.length();
    if (length>128) length = 128;
    DatagramPacket madePacket = new DatagramPacket(data,length);
    madePacket.setAddress(destination);
    madePacket.setPort(port);
    return madePacket;
    }
  

}

/hello