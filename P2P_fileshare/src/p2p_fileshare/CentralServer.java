/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor...
 */
package p2p_fileshare;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static p2p_fileshare.CentralServer.directory;
import static p2p_fileshare.CentralServer.updateDirectory;

public class CentralServer {
  public static InetAddress LocalIP; 
  static Hash directory = new Hash();
  
  public static void main(String[] args) 
  throws UnknownHostException, IOException, SocketException, InterruptedException{
    System.out.println("Running server.");
    int portOffset = 0;
    int ackPort;
    LocalIP = InetAddress.getLocalHost(); 
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
    System.out.println("now on an infinite loop of RDT.listen");
    autoPing ping = new autoPing();
    ping.start();
    while (true){
      HTTP received = RDT.listen( Globals.S_PORT  );
      ackPort = Globals.BASE_PORT + portOffset;  //portOffset for separation from other threads, peers
      portOffset = (portOffset + 1) % 100;
      serverRequestHandler newThread = new serverRequestHandler(received, ackPort);
      newThread.run();
      }
  }
  
  public  static void updateDirectory(HTTP received){
    // Get ArrayList of peer IP addresses
    ArrayList<String> peerIPlist = Hash.getListFromIP(directory.getTable());
    // ping each peer and if ping= false clearAssociatedElements from that ip
    while (!peerIPlist.isEmpty()){
      String nextIP = peerIPlist.remove(0);
      if ( !Peer.pingClient(nextIP) ){
        directory.clearAssociatedElements(nextIP)  ;
      }
    }
    
    directory.clearAssociatedElements(received.getIPaddress() )  ;
    Song.processSongString(received.getPayload(), directory.getTable());
    System.out.println("This is the server's new file list:");
    Song.printDirectory(directory.getTable());
    System.out.println();
    
    
    //System.out.println("Inside updateDirectory method");
  }
} //end class CentralServer


class autoPing extends Thread {
  
  public autoPing(){
  }

  @Override
  public void run(){
      
    while (true){  
        try {
          Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
        // Get ArrayList of peer IP addresses
        ArrayList<String> peerIPlist = Hash.getListFromIP(directory.getTable());
        // ping each peer and if ping= false clearAssociatedElements from that ip
        System.out.println("autoPing is checking for disconnected peers.");
        while (!peerIPlist.isEmpty()){
          String nextIP = peerIPlist.remove(0);
          if ( !Peer.pingClient(nextIP) ){
            directory.clearAssociatedElements(nextIP)  ;
            System.out.println("Peer at " + nextIP + " has disconnected.  Files removed from directory.");

          }
        }
    }
  }
}
    

class serverRequestHandler extends Thread {
  
  HTTP received;
  int ackPort; 
  
  public serverRequestHandler(HTTP received, int ackPort){
    this.received = received;
    this.ackPort = ackPort;
  }
  
  @Override
  @SuppressWarnings("null")
  public void run() {
    InetAddress LocalIP = null;  
    try {
      LocalIP = InetAddress.getLocalHost();
    } catch (UnknownHostException ex) {
    }
    HTTP response = new HTTP();
    System.out.println("HTTP received as follows: \n");
    System.out.println(received.display());
    int responsePort = Integer.parseInt(received.getPhrase() );
    switch (received.getCode()) {
      case "L": // LOGIN request, which is really just checking for a server on the IP used.
                System.out.println("processing L");
                response = new HTTP("200","O",LocalIP.getHostAddress(),"1","Okay doesn't need a payload.");
                break;
      case "I": // INFORM/UPDATE
                System.out.println("Processing inform/update.");
                updateDirectory(received);
                response = new HTTP("201","D",LocalIP.getHostAddress(),"1",directory.makeDirectoryString());
                break;
      case "Q": // QUERY FOR CONTENT.  Bad request.  Query for content should go to other peers.
                System.out.println("processing Q");
                String result = directory.processQuery(directory.getTable(), received.getPayload());
                if (result.equals("")) {
                    response = new HTTP("404","B",LocalIP.getHostAddress(),"1","File not found.");
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
                directory.clearAssociatedElements(received.getIPaddress());
                System.out.println("This is the server's new file list:");
                Song.printDirectory(directory.getTable());
                System.out.println("\nprocessing E");
                response = new HTTP("200","O",LocalIP.getHostAddress(),"1","Exit complete");
                break;
      default:  // request did not match any of the expected cases.
                System.out.println("processing Default");
                response = new HTTP("400","B",LocalIP.getHostAddress(),"1","Unknown code in request.");
                break;
      }
    try {
      RDT.transmit( received.getIPaddress(), responsePort, ackPort, response.asString() );
    } catch (IOException | InterruptedException ex) {
    }
  }
}
