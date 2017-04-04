/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * This is some test code to send and receive 128 byte strings via sockets.
 */

package p2p_fileshare;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPListener {

  public static void main(String[] args) throws IOException {
    Scanner keyboard = new Scanner(System.in);
    int port = Globals.PORT;
    InetAddress IP = InetAddress.getByName(Globals.JEFF_PC_IP); // jeff pc local ip
    
    DatagramSocket datagramSocket = new DatagramSocket(port,IP);
        
    System.out.println("Created UDP  server socket at "
        + datagramSocket.getLocalSocketAddress() + "...");
    boolean keepGoing = true;
    new Timer(20).start(); // don't really need this, just exercises multithreading and gives some status.
    do {
      System.out.println("Waiting for a  UDP  packet...");
      DatagramPacket packet = new DatagramPacket(new byte[128], 128);
      datagramSocket.receive(packet);
      System.out.println("Packet received.  Sending to handler thread.");
      new DisplayPacketDetails(packet).start();
    } while (keepGoing );
        
    datagramSocket.close();
  }  




public static class DisplayPacketDetails extends Thread {
    DatagramPacket packet = new DatagramPacket(new byte[128], 128);
    
    public DisplayPacketDetails(DatagramPacket packetPar) {
      this.packet = packetPar;
      }
    
    public void run() {
      byte[] msgBuffer = packet.getData();
      int length = packet.getLength();
      int offset = packet.getOffset();
      int remotePort = packet.getPort();
      InetAddress remoteAddr = packet.getAddress();
      String msg = new String(msgBuffer, offset, length);
      System.out.println("[Server at IP  Address=" + remoteAddr + ", port="
          + remotePort + "]: " + msg);
    }
}
}