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
import java.util.Hashtable;
import static p2p_fileshare.Peer.centralServerIP;

public class CentralServer {
  public static InetAddress LocalIP; 
   static Hash directory = new Hash();
  
  
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
                System.out.println("processing L");
                response = new HTTP("200","O",LocalIP.getHostAddress(),"1","Okay doesn't need a payload.");
                break;
      case "I": // INFORM/UPDATE
                System.out.println("processing I");
                System.out.println("Need to add the files to the hash table");
                updateDirectory(received);
                response = new HTTP("201","D",LocalIP.getHostAddress(),"1",directory.makeDirectoryString());
                break;
      case "Q": // QUERY FOR CONTENT.  Bad request.  Query for content should go to other peers.
                System.out.println("processing Q");
                String result = directory.processQuery(directory.getTable(), received.getPayload());
                if (result.equals("")) {
                    response = new HTTP("404","F",LocalIP.getHostAddress(),"1","query is empty");
                } else {
                    response = new HTTP("200","O",LocalIP.getHostAddress(),"1",result);
                }
                    
                   
                
                
                
                break;
      case "D": // DIRECTORY QUERY.  <do we really need this?  can't the user just inform/update?>
                // if we used it, put code here to search and respond with either 200:O or 404:F
                System.out.println("processing D");
                break;
      case "R": // REQUEST FOR CONTENT  should have gone to a peer
                System.out.println("processing R on server");
                response = new HTTP("404","F",LocalIP.getHostAddress(),"1","File must be requested from Peers, not the server.");
                break; 
      case "E": // EXIT from the network
                // Put code here to delete the peers files from the directory.
                directory.clearAssociatedElements(received.getIPaddress() )  ;
                System.out.println("this is the server's hash table");
                Song.printDirectory(directory.getTable());
                System.out.println("processing E");
                response = new HTTP("200","O",LocalIP.getHostAddress(),"1","Exit complete");
                break;
      default:  // request did not match any of the expected cases.
                System.out.println("processing Default");
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
  
  public  static void updateDirectory(HTTP received){
    directory.clearAssociatedElements(received.getIPaddress() )  ;
    Song.processSongString(received.getPayload(), directory.getTable());
    System.out.println("this is the server's hash table");
    Song.printDirectory(directory.getTable());
    System.out.println();
    
    //  REMOVE OLD ENTRIES FROM THIS received.getIPaddress()
    //  ADD SONGS IN received.getPayload()
    System.out.println("Inside updateDirectory method");
  }

}