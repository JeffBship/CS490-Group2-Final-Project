/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;
import java.util.*;
import java.io.*;
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
    public static void main(String[] args) throws UnknownHostException{
      Server MainServer = new Server();
      //ArrayList<Song> song = new ArrayList<>();
      String songList;
      Peer p1 = new Peer();
      songList = p1.getDirectory();
      //song = p1.getDirectory();
      System.out.println("PEER printDirectory Method");
      System.out.print(songList);
      System.out.println("String test");
      System.out.println(songList);
      MainServer.processSongArray(songList, MainServer.getTable());
      System.out.println("HASH TABLE PRINT");
      MainServer.printServerDirectory(MainServer.getTable());
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
