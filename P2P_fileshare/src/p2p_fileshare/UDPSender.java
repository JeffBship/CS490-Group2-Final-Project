/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringsender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSender {
    
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
  

  public static void main(String[] args) throws IOException {
    //String destinationIP = "149.152.23.48";
    //String destinationIP = "73.69.81.72"; //jeff pc public ip
    String destinationIP = "192.168.1.46"; // jeff pc local ip
    //String destinationIP = "192.168.1.118"; // jeff laptop local ip
    int destinationPort = 55000;

    String message = "This is a big long String that represents a message we want to send.";
    DatagramSocket datagramSocket = new DatagramSocket();
    DatagramPacket outgoingPacket = makePacket(message, destinationIP, destinationPort);
    datagramSocket.send(outgoingPacket);
    datagramSocket.close();
    }
  
}
