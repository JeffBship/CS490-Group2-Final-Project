/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import static p2p_fileshare.Peer.centralServerIP;

/**
 *
 * @author Dad
 */
public class CentralServer {
  
  
  public static void main(String[] args) 
  throws UnknownHostException, IOException, SocketException, InterruptedException{
    System.out.println("Running server.");
    InetAddress LocalIP = InetAddress.getLocalHost(); 
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
    System.out.println("now on an infinite loop of RDT.listen");
    while (true){
      HTTP received = RDT.listen( Globals.MSG_PORT  );
      System.out.println("HTTP received as follows: \n");
      System.out.println(received.display());
      //SEND AND OKAY RESPONSE ---NEED TO ADD HANDLING TO SEND DIFFERENT RESPONSES.
      HTTP response = new HTTP("200","O",LocalIP.getHostAddress(),"1","This is an Okay response, no payload needed.");
      RDT.transmit( received.getIPaddress(), Globals.ACK_PORT, response.asString() );
    }
    
    
  }
  
}
