  /**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * 
 */
package p2p_fileshare;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

class RDT_Sender {

  public static void transmit(String destinationIP, int destinationPort, String message) throws IOException{
           
    System.out.println("In Transmit to IP " + destinationIP);
    ArrayList packetList = new ArrayList(0);
    
    DatagramSocket datagramSocket = new DatagramSocket();
    
    datagramSocket.close();
  }
        
  

  private void rdt_send(){
      }

  private static packet rdt_rcv(){
        packet result = new packet();
        return result;
      }

  private static packet make_pkt(char state, String data){
        packet result = new packet();
        return result;
      }

  private static void udt_send(String destinationIP, int destinationPort, 
                               packet packetPar, DatagramSocket datagramSocket) 
                               throws IOException{
    //Now that we have our own packet, we need to make them back into strings to use Datagram Packets :(
    String packetString = packetPar.toString();
    DatagramPacket outgoingPacket = makePacket(packetString, destinationIP, destinationPort);
    datagramSocket.send(outgoingPacket);
      }
      

  public static DatagramPacket makePacket(String msg, String destination, int port) throws UnknownHostException{
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
  