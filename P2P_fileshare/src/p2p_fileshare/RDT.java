/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * Notes:
 * The last packet in the list has packetsRemaining = 1  (not 0)
 * 
 * 
 */
package p2p_fileshare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.abs;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import static p2p_fileshare.UDPSender.makePacket;

class RDT {
  
  //Timeout calculation variables
  static double estimatedRTT;  
  static double timout;
  static double devRTT;
  
  
//Will modify to send arraylist<song> instead
  public static void transmit(String transmitIP, int transmitPort, String transmitMessage) throws IOException, InterruptedException{
    ArrayList<Packet> packetList = new ArrayList<>();
    
    //This block creates header data to use in the packets
    String LocalPeerID = "1";  //temporary.  need to figure out how to manage this.
    InetAddress LocalIP = InetAddress.getLocalHost();
    String LocalIPString = LocalIP.getHostAddress();
    String sequence = "0";
    String packetsRemaining = "";
    int packetsRemainingInt = 0;
    String packetData = "";
    
    //Timeout calculation data re-initializes for each packetList
    boolean finished = false;
    estimatedRTT = Globals.INIT_EST_RTT;  
    timout = Globals.INIT_TIMEOUT;
    devRTT = Globals.INIT_DEV_RTT;
    
    packetsRemainingInt = (int) Math.ceil( transmitMessage.length() / 95.0 ) +2 ; 
    // SYN packet isn't a handshake, it tells the receiver to start sequence count over
    // so if a previous message was dropped it can't jinx the sequence.
    sequence = "0";
    Packet newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining , "SYN");
    packetList.add(newPacket); 
    packetsRemainingInt--;
    sequence = "1";
    //Build the packetList by taking 95 byte chunks of the message
    while (packetsRemainingInt>0){
      packetsRemaining = Integer.toString(packetsRemainingInt);
      int endIndex = 94;
      String newPacketData;
      if (transmitMessage.length()>95){
        newPacketData = transmitMessage.substring(0, 94); //first 95 bytes of message
        transmitMessage = transmitMessage.substring(94); //all after the first 95
        }else{
          newPacketData = transmitMessage;
        }
      newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining, newPacketData);
      packetList.add(newPacket); //adds newPacket to the end of the list.
      if (sequence.equals("0")) 
        { sequence = "1";} 
        else {sequence = "0";}
      packetsRemainingInt--;
    }
    //add FIN packet on the end
    newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining, "FIN");
    packetList.add(newPacket); //adds newPacket to the end of the list.
    
    System.out.println("In Transmit,  to IP " + transmitIP);
    int totalPackets = packetList.size()-1;
    for (int i=0;i<packetList.size();i++){
      System.out.println("------------------------ NEXT PACKET----------------------------");
      String currentSeq = packetList.get(i).getSequence();
      System.out.println("Sending Packet " + i + " of " +  totalPackets + " with sequence number " + currentSeq);  
      rdt_send(transmitIP, transmitPort, packetList.get(i));
      //System.out.println("getpeerID           : " + packetList.get(i).getpeerID() );
      //System.out.println("getIPAddress        : " + packetList.get(i).getIPAddress() );
      //System.out.println("getSequence         : " + packetList.get(i).getSequence() );
      //System.out.println("getPacketsRemaining : " + packetList.get(i).getPacketsRemaining() );
      //System.out.println("getData             : " + packetList.get(i).getData() );
      //System.out.println( packetList.get(i).asString()    );
    }
  }
  
  private static void rdt_send(String rdt_sendIP, int rdt_sendPort, 
                               Packet rdt_sendPacket) throws IOException, InterruptedException{
    // - extract sequence from packet
    String sequence = rdt_sendPacket.getSequence();
    int port = Globals.ACK_PORT;
    //InetAddress IP = InetAddress.getByName(Globals.JEFF_PC_IP); // jeff pc local ip
    
    DatagramSocket ACKSocket = new DatagramSocket(Globals.ACK_PORT);
    boolean finished = false;
    while (!finished){
      try {
        // Delay when in SLOWMODE
          if (Globals.SLOWMODE){
            System.out.println(" <<< SLOW MODE IN EFFECT: 4 SEC DELAY >>> ");
            Thread.sleep(4000);
          }
          
        //  SEND THE CURRENT PACKET
          DatagramSocket MSGSocket = new DatagramSocket();
          System.out.print("Sent to udt, ");
          long startTime = System.currentTimeMillis();  // - Start Timer
          //System.out.println("startTime " + startTime % 10000);
          udt_send(MSGSocket, rdt_sendIP, rdt_sendPort, rdt_sendPacket); 
          MSGSocket.close();
        
        //  LISTEN FOR ACK UNTIL TIMEOUT
          
          DatagramPacket DGACKpacket = new DatagramPacket(new byte[128], 128);
          ACKSocket.setSoTimeout( (int)timout );
          System.out.println(" timeout = "+ (long)timout);
          ACKSocket.receive(DGACKpacket);
          
          //  UPDATE EstimatedRTT, and Timeout   
          long stopTime = System.currentTimeMillis();  // - Stop Timer
          long sampleRTT = stopTime - startTime;    
          
          //System.out.println("stopTime  " + stopTime % 10000);
          estimatedRTT = (1-Globals.ALPHA)*estimatedRTT + (Globals.ALPHA*sampleRTT);
          devRTT = (long) ((1-Globals.BETA)*devRTT + Globals.BETA * abs(sampleRTT- estimatedRTT ));
          timout = estimatedRTT + 4*devRTT;
          //System.out.println("Packet received on ACKSocket.");
          System.out.println("SampleRTT: " + sampleRTT + " New timeout: " + (long)timout);
        // CHECK SEQUENCE NUMBER OF ACK
        // - if ACK of correct sequence # , finished
          Packet response = Packet.extractFromDatagram(DGACKpacket);
          if ( response.isACK(sequence)  ){
            System.out.println("ACK received with sequence number " + response.getSequence() );
            finished=true;
          } else {
            System.out.println("Received bad Ack, sequence " + response.getSequence() + ", resending.");
          }
        } catch (SocketTimeoutException e) {
          timout *= 2;  // After a timeout, double the timer  USING A SMALLER NUMBER DURING TESTING
          System.out.println("Timeout occurred, resending.");
          }
    }
    ACKSocket.close();
  }
  
  private static Packet rdt_rcv() throws UnsupportedEncodingException{
    Packet result = new Packet();
    return result;
  }

  private static Packet make_pkt(char state, String data) throws UnsupportedEncodingException{
    Packet result = new Packet();
    return result;
  }

  private static void udt_send(DatagramSocket datagramSocket,
                               String udtIP, int destinationPort, 
                               Packet packetPar) 
                               throws IOException{
   
    
        DatagramPacket outgoingPacket = makePacket(packetPar.asString(), udtIP, destinationPort);
        datagramSocket.send(outgoingPacket);
    }



  public static void main(String[] args) {
    System.out.println("RDT_Sender class compiled and ran successfully.");
  }

}
