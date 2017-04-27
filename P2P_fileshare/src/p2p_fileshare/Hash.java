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
   Use InetAddress.getByName(String host) to convert 
*/
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
public class Hash {
  //Server will need to get IP address of local machine so that it can connect with peers
    Hashtable<String, Song> dTab;
    int sNum = 0;
    
    public Hash(){
       dTab = new Hashtable<>();
      
    }
    
    public Hashtable<String, Song> getTable(){
        return dTab;
    }
 
  //Modify to return query as a string!!!!
  public void processQuery(Hashtable<String, Song> sTable, String query) {
    //Scanner not closed to allow for Peer to continue using scanner
    // query.close();
    String IP;
    //DONT CHANGE THIS PART
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          if(key.getName().toLowerCase().replace(" ","").contains(query))
            System.out.println(key.getAll());
      }
   
  }
  
  //These can be written AFTER testing RDT code between client and server
  
  /*This method will work in conjunction with the TCP.java class.
    User will type in IP address and TCP.java will handle the connection
  */
  
  //Check whether or not all the IPS are reachable
  public void checkIPs(){
  
  
  }
  
  //MUST BE TESTED 
  //This method deletes all entries in hashtable associated with PeerIP
  //Can be used to in Inform and Update and Exit methods in Peer.java
  public void clearAssociatedElements(String PeerIP){
    Enumeration enumK = dTab.elements();
    Song key = null;
    while(enumK.hasMoreElements()){
      key = (Song) enumK.nextElement();
      if(key.getIP().equals(PeerIP))
          dTab.remove(key.getKey());
    }

  }
  
  //MUST BE TESTED 
  //Makes String from Server Hashtable to send to peer
    public String makeDirectoryString(){
    String songs = "";
    Enumeration keys = dTab.keys();
    String key = "";
    Song temp = null;
    while(keys.hasMoreElements()){
      key =(String) keys.nextElement();
      temp = dTab.get(key);
      songs+= temp.getName() + "\t\t" + temp.getFilesize() + "\t\t" + temp.getIP() + "\n";
    }
    return songs;
  }
  
  
}