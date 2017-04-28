/*

I hate git
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor..
 */
package p2p_fileshare;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import static p2p_fileshare.Peer.centralServerIP;

public class CentralServer {
  public static InetAddress LocalIP; 
  public static HTTP received = new HTTP();
   static Hash directory = new Hash();
  
  
  public static void main(String[] args) 
  throws UnknownHostException, IOException, SocketException, InterruptedException{
    System.out.println("Running server.");
    LocalIP = InetAddress.getLocalHost(); 
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
    System.out.println("now on an infinite loop of RDT.listen");
    
    while (true){
      received = RDT.listen( Globals.MSG_PORT  );
      requestHandler handle = new requestHandler();
      handle.start();
    }
  }
}

  //THIS PART BECOMES A THREAD AT SOME POINT  n
  class requestHandler extends Thread {
    
    @Override
    public void run(){
    HTTP rcvd = CentralServer.received;
    HTTP response = new HTTP();
    String myIP = CentralServer.LocalIP.getHostAddress();
    System.out.println("Inside request Handler, new thread, with HTTP received as follows: \n");
    System.out.println(rcvd.display());
    switch (rcvd.getCode()) {
      case "L": // LOGIN request, which is really just checking for a server on the IP used.
                System.out.println("processing L");
                response = new HTTP("200","O",myIP,"1","Okay doesn't need a payload.");
                break;
      case "I": // INFORM/UPDATE
                System.out.println("processing I");
                System.out.println("Need to add the files to the hash table");
                updateDirectory(rcvd);
                response = new HTTP("201","D",myIP,"1",CentralServer.directory.makeDirectoryString());
                break;
      case "Q": // QUERY FOR CONTENT.  Bad request.  Query for content should go to other peers.
                System.out.println("processing Q");
                String result = CentralServer.directory.processQuery(CentralServer.directory.getTable(), rcvd.getPayload());
                if (result.equals("")) {
                    response = new HTTP("404","F",myIP,"1","query is empty");
                } else {
                    response = new HTTP("200","O",myIP,"1",result);
                }
                break;
      case "D": // DIRECTORY QUERY.  <do we really need this?  can't the user just inform/update?>
                // if we used it, put code here to search and respond with either 200:O or 404:F
                System.out.println("processing D");
                break;
      case "R": // REQUEST FOR CONTENT  should have gone to a peer
                System.out.println("processing R on server");
                response = new HTTP("404","F",myIP,"1","File must be requested from Peers, not the server.");
                break; 
      case "E": // EXIT from the network
                // Put code here to delete the peers files from the directory.
                CentralServer.directory.clearAssociatedElements(rcvd.getIPaddress() )  ;
                System.out.println("this is the server's hash table");
                Song.printDirectory(CentralServer.directory.getTable());
                System.out.println("processing E");
                response = new HTTP("200","O",myIP,"1","Exit complete");
                break;
      default:  // request did not match any of the expected cases.
                System.out.println("processing Default");
                response = new HTTP("400","B",myIP,"1","Unknown code in request.");
                break;
      }
      try {
        System.out.println("in request handler, about to transmit");
        RDT.transmit( rcvd.getIPaddress(), Globals.ACK_PORT, response.asString() );
      } catch (IOException ex) {
        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InterruptedException ex) {
        Logger.getLogger(requestHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  
  public  static void updateDirectory(HTTP received){
    // first get rid of any previous songs from this peerIP
    CentralServer.directory.clearAssociatedElements(received.getIPaddress());
    // Now add songs in the current update
    Song.processSongString(received.getPayload(), CentralServer.directory.getTable());
    System.out.println("this is the server's hash table");
    Song.printDirectory(CentralServer.directory.getTable());
    System.out.println();
  }

}