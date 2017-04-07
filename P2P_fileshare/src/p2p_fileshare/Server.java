package p2p_fileshare;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeff Blankenship && Adrian Ward-Manthey
 *  
 */
/*
   Java HashTables uses separate chaining to deal with collisions
   NEED TO IMPLEMENT MULTITHREADING????
*/
import java.io.*;
import java.util.*;
public class Server {
  //Main method just for testing purposes 
  //Server will need to get IP address of local machine so that it can connect with peers
    Hashtable<String, Song> dTab;
    public Server(){
       dTab = new Hashtable<>();
      
    }
    
    public Hashtable<String, Song> getTable(){
        return dTab;
    }

  //This function works properly
  public void processSongArray(ArrayList<Song> songs,Hashtable<String, Song> dTab){
      //This class will take an array from a peer and add it into the hashtable
      for(Song s : songs)
        dTab.put(s.getName(),s);
      
  }
  
  //This Function works properly
  public void printServerDirectory(Hashtable<String, Song> sTable){
      Enumeration songNames = sTable.keys();
      String key;
      while(songNames.hasMoreElements()){
          key = (String) songNames.nextElement();
          System.out.println("Key: " + key);
      }
  }
  
  //This function will take user input as key, hash it, then will return list of songs along with IP addresses 
  //might need 2 functions....one to process query, another to use requested IP address by user
  public static String processQuery(String key, Hashtable<String, Song> sTable){
    String IP = "";
    
    return IP;
      
  }
  
  
  
}