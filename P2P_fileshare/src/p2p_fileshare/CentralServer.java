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
  public static InetAddress LocalIP; 
  Hash directory = new Hash();
  
  public static void main(String[] args) 
  throws UnknownHostException, IOException, SocketException, InterruptedException{
    System.out.println("Running server.");
    LocalIP = InetAddress.getLocalHost(); 
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
    System.out.println("now on an infinite loop of RDT.listen");
    
    while (true){
      HTTP received = RDT.listen( Globals.MSG_PORT  );
      requestHandler(received);
    }
  }
  
  //THIS PART BECOMES A THREAD AT SOME POINT
  private static void requestHandler(HTTP received) throws IOException, InterruptedException{
    HTTP response = new HTTP();
    System.out.println("HTTP received as follows: \n");
    System.out.println(received.display());
    switch (received.getCode()) {
      case "L": // LOGIN request, which is really just checking for a server on the IP used.
                response = new HTTP("200","O",LocalIP.getHostAddress(),"1","Okay doesn't need a payload.");
                RDT.transmit( received.getIPaddress(), Globals.ACK_PORT, response.asString() );
                break;
      case "I": // INFORM/UPDATE
                System.out.println("Need to add the files to the hash table");
                updateDirectory(received);
                response = new HTTP("201","D",LocalIP.getHostAddress(),"1","SERVER DIRECTORY AS STRING GOES HERE");
                break;
      case "Q": // QUERY FOR CONTENT.  Bad request.  Query for content should go to other peers.
                response = new HTTP("400","B",LocalIP.getHostAddress(),"1","Query a peer, not the server");
                break;
      case "D": // DIRECTORY QUERY.  <do we really need this?  can't the user just inform/update?>
                // if we used it, put code here to search and respond with either 200:O or 404:F
                break;
      default:  // request did not match any of the expected cases.
                response = new HTTP("400","B",LocalIP.getHostAddress(),"1","Unknown code in request.");
                break;
                
      }
    RDT.transmit( received.getIPaddress(), Globals.ACK_PORT, response.asString() );
    //Request Codes/Phrase:
    //L: L Log in attempt.   
    //I: I  Inform and update
    //Q: Q query for content  
    //D: D directory query 
    
  }
  
  public static void updateDirectory(HTTP received){
    //  REMOVE OLD ENTRIES FROM THIS received.getIPaddress()
    //  ADD SONGS IN received.getPayload()
    System.out.println("Inside updateDirectory method");
  }

}
