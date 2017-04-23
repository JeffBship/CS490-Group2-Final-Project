/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Dad
 */
public class CentralServer {
  
  
  public static void main(String[] args) throws UnknownHostException{
    System.out.println("Running server.");
    InetAddress LocalIP = InetAddress.getLocalHost(); 
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
  }
  
}
