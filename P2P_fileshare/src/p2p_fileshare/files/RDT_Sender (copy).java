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

class RDT_Comms {

  public static void transmit(String destinationIP, int destinationPort, String message) throws IOException{
    ArrayList<Packet> packetList = new ArrayList<>();
    
    //This block creates header data to use in the packets
    String LocalPeerID = "1";  //temporary.  need to figure out how to manage this.
    InetAddress LocalIP = InetAddress.getLocalHost();
    String LocalIPString = LocalIP.getHostAddress();
    String sequence = "0";
    String packetsRemaining = "";
    int packetsRemainingInt = 0;
    String packetData = "";
    
    packetsRemainingInt = (int) Math.ceil( message.length() / 95.0 ) ; 
    //Build the packetList by taking 95 byte chunks of the message
    while (packetsRemainingInt>0){
      packetsRemaining = Integer.toString(packetsRemainingInt);
      int endIndex = 94;
      String newPacketData;
      if (message.length()>95){
        newPacketData = message.substring(0, 94); //first 95 bytes of message
        message = message.substring(94); //all after the first 95
        }else{
          newPacketData = message;
        }
      Packet newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining, newPacketData);
      packetList.add(newPacket); //adds newPacket to the end of the list.
      if (sequence.equals("0")) { 
        sequence = "1";
        } else { 
        sequence = "0"; 
        }
      packetsRemainingInt--;
    }
    
    
    System.out.println("In Transmit,  to IP " + destinationIP);
    DatagramSocket datagramSocket = new DatagramSocket();
    // do the udt_send stuff here
    for (int i=0;i<packetList.size();i++){
      System.out.println("-----SENDING NEXT PACKET-----");
      System.out.println("getpeerID           : " + packetList.get(i).getpeerID() );
      System.out.println("getIPAddress        : " + packetList.get(i).getIPAddress() );
      System.out.println("getSequence         : " + packetList.get(i).getSequence() );
      System.out.println("getPacketsRemaining : " + packetList.get(i).getPacketsRemaining() );
      System.out.println("getData             : " + packetList.get(i).getData() );
      //System.out.println( packetList.get(i).asString()    );
    }
    
    
    datagramSocket.close();
  }
        
  

  private void rdt_send(){
    // - extract sequence from packet
    // - send the packet UDT_SEND
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

  private static void udt_send(String destinationIP, int destinationPort, 
                               Packet packetPar, DatagramSocket datagramSocket) 
                               throws IOException{
    //Now that we have our own packet, we need to make them back into Datagram Packets
    String packetString = packetPar.toString();
    DatagramPacket outgoingPacket = makeDatagramPacket(packetString, destinationIP, destinationPort);
    datagramSocket.send(outgoingPacket);
  }
      

  public static DatagramPacket makeDatagramPacket(String msg, String destination, int port) throws UnknownHostException{
    InetAddress IPaddress =  InetAddress.getByName(destination);
    byte[] data = msg.getBytes();
    int length = msg.length();
    if (length>128) length = 128;
    DatagramPacket madePacket = new DatagramPacket(data,length);
    madePacket.setAddress(IPaddress);
    madePacket.setPort(port);
    return madePacket;
  }
      
      



  public static void main(String[] args) {
    System.out.println("RDT_Sender class compiled and ran successfully.");
  }

}
  