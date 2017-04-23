/**
 * Peer gets file list
 *  Peer make songList -Adrian
 *      HTTPreq from songList -Jeff
 *          RDTtransmit HTTreq to server -Done
 *              Server  extracts songList from HTTPreq -Jeff
 *                      add songs to Dtab -Adrian
 *                      builds HTTP response from Dtab
 *                      RDTtransmit HTTPres back to Peer
 *              Peer extracts songList -Jeff
 *          Peer clears and builds new dTab from songList -Adrian
 *      Peer display dTab -Adrian
 *  Peer waits for more user input
 * 
 * TCP stuff -Adrian
 * Peer gets query
 *  user select song to request
 *      Peer creates HTTPreq for that song
 *          Peer RDTtransmits the HHTPreq
 *          Peer2   receives HTTPreq
 *                  checks for file in local directory
 *                  sends file via TCP to Peer1 -ADRIAN
 *                  build   HTTPresponse
 *          Peer2 RDT transmit HTTPresponse
 *      Peer1   receive response
 *              extract data from response
 *              display results
 *      close connection
 * wait for further user input
 * 
 * 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author Adrian Ward-Manthey and Jeff Blanekship
 * 
 *C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files
 * C:\Users\Surface Book\Desktop\Youtube Videos
 * C:\Users\Surface Book\Desktop
 */
//THIS CLASS WILL BE USED TO TEST PEER TRANSMIT
public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
      Hash MainServer = new Hash();
      //ArrayList<Song> song = new ArrayList<>();
      //String songList;
      
      Peer p1 = new Peer();
      p1.userInteraction(MainServer);
      System.out.println("MAIN CLASS SERVER CHECK");
      Song.printDirectory(MainServer.getTable());
     
    /*Use this to test TCPClient and TCPServer   
    TCPServer serverThread = null;
    TCPClient clientThread = null;
    try {
      // Start server
      serverThread = new TCPServer("Server", 49000);
      serverThread.start();

      // Create client
      byte[] targetAdddress = {127, 0, 0, 1};
      TCPClient client1 = new TCPClient("CLIENT1", 49000);
      TCPClient client2 = new TCPClient("CLIENT2", 49000);
      client1.start();
      client2.start();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    ///////////////////////////////////  
      
      
      
      /*DONT DELETE THESE COMMENTED LINES THEY WILL BE USEFUL FOR TESTING LATER*/
      /*
      System.out.println("PEER printDirectory Method");
      System.out.print(songList);
      System.out.println("String test");
      System.out.println(songList);
      MainServer.processSongString(songList, MainServer.getTable());
      System.out.println("HASH TABLE PRINT");
      MainServer.printServerDirectory(MainServer.getTable());
      MainServer.processQuery(MainServer.getTable());
      //p1.printDirectory(song);
      //method to send this directory to server 
      //below is just a test to make sure that the server properly processes the arraylist and places
      //the values into a hashtable
      /*
      
      MainServer.processSongArray(song, MainServer.getTable() );
      System.out.println("SERVER printServerDirectory Method");
      MainServer.printServerDirectory(MainServer.getTable());
      MainServer.processQuery(MainServer.getTable());
      
      //Process Query Not Working Yet
     // MainServer.processQuery(MainServer.getTable());
       //SO FAR SO GOOD!!!! NEED TO USE METHODS FROM RDT AND PACKET TO ACTUALLY SEND
       //ArrayList over UDP
      /*
      Peer p2 = new Peer();
      song = p1.getDirectory();
      p2.printDirectory(song);
      System.out.println("PEER2 printDirectory Method");
      p2.printDirectory(song);
      MainServer.processSongArray(song, MainServer.getTable() );
      System.out.println("SERVER printServerDirectory Method");
      MainServer.printServerDirectory(MainServer.getTable());
      //below is a test to make sure that the query works as intended...of course
      //in full application this will utilize RDT.java and Packet.java
      */
      
     
    
    }
}
