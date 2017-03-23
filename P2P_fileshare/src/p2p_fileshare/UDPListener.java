/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * This is some test code to send and receive 128 byte strings via sockets.
 */


package p2p_fileshare;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class UDPListener {

  public static void main(String[] args) throws IOException {
    DatagramSocket datagramSocket = new DatagramSocket();
    DatagramPacket packet = null;
    datagramSocket.receive(packet);
    displayPacketDetails(packet);
    }  


public static void displayPacketDetails(DatagramPacket packet) {
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