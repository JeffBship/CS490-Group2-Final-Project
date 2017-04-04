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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import static p2p_fileshare.UDPSender.makePacket;

class RDT {

  public static void transmit(String transmitIP, int transmitPort, String transmitMessage) throws IOException{
    ArrayList<Packet> packetList = new ArrayList<>();
    
    //This block creates header data to use in the packets
    String LocalPeerID = "1";  //temporary.  need to figure out how to manage this.
    InetAddress LocalIP = InetAddress.getLocalHost();
    String LocalIPString = LocalIP.getHostAddress();
    String sequence = "0";
    String packetsRemaining = "";
    int packetsRemainingInt = 0;
    String packetData = "";
    
    packetsRemainingInt = (int) Math.ceil( transmitMessage.length() / 95.0 ) ; 
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
      Packet newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining, newPacketData);
      packetList.add(newPacket); //adds newPacket to the end of the list.
      if (sequence.equals("0")) 
        { sequence = "1";} 
        else {sequence = "0";}
      packetsRemainingInt--;
    }
    
    
    System.out.println("In Transmit,  to IP " + transmitIP);
    
    for (int i=0;i<packetList.size();i++){
      System.out.println("-----SENDING NEXT PACKET-----");
//000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000      
      rdt_send(transmitIP, transmitPort, packetList.get(i));
      //System.out.println("getpeerID           : " + packetList.get(i).getpeerID() );
      //System.out.println("getIPAddress        : " + packetList.get(i).getIPAddress() );
      System.out.println("getSequence         : " + packetList.get(i).getSequence() );
      //System.out.println("getPacketsRemaining : " + packetList.get(i).getPacketsRemaining() );
      //System.out.println("getData             : " + packetList.get(i).getData() );
      //System.out.println( packetList.get(i).asString()    );
    }
  }
        
  

  private static void rdt_send(String rdt_sendIP, int rdt_sendPort, 
                               Packet rdt_sendPacket) throws IOException{
    // - extract sequence from packet
    String sequence = rdt_sendPacket.getSequence();
    
    // - send the packet UDT_SEND
    udt_send(rdt_sendIP, rdt_sendPort, rdt_sendPacket); 
    
    // - Start Timer
    
    // - IF timeout send udt_send again
    // - if ACK of correct sequence # , finished
  }

  private static Packet rdt_rcv() throws UnsupportedEncodingException{
    Packet result = new Packet();
    return result;
  }

  private static Packet make_pkt(char state, String data) throws UnsupportedEncodingException{
    Packet result = new Packet();
    return result;
  }

  private static void udt_send(String udtIP, int destinationPort, 
                               Packet packetPar) 
                               throws IOException{
    System.out.println("##################### udt_send ######################");
      
        //udtIP = Globals.JEFF_PC_IP; // jeff pc local ip
        //String destinationIP = "192.168.1.118"; // jeff laptop local ip
        //String destinationIP = "127.0.0.1";  // local host
        int destinationPortx = Globals.PORT;

        String message = "this is stupid";
        DatagramSocket datagramSocket = new DatagramSocket();
        DatagramPacket outgoingPacket = makePacket(packetPar.asString(), udtIP, destinationPortx);
        datagramSocket.send(outgoingPacket);
        datagramSocket.close();
        
    }
  
  
    


  
      

      
      



  public static void main(String[] args) {
    System.out.println("RDT_Sender class compiled and ran successfully.");
  }

}
  