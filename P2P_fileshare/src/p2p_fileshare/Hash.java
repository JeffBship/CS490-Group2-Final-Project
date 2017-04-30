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
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

public class Hash {
  
    Hashtable<String, Song> dTab;
    int sNum = 0;
    
    public Hash(){
       dTab = new Hashtable<>();
    }
    
//CAN WE DO THIS???    
    public Hash(Hashtable<String, Song> newTab){
       dTab = newTab;
    }
    
    public Hash copy (Hash original){
      Hash result = new Hash();
      result.dTab = original.dTab;
      return result;
    }
    
    public Hashtable<String, Song> getTable(){
        return dTab;
    }
 
  public static String processQuery(Hashtable<String, Song> sTable, String query) {
    String q = "Num \tIPaddress\tsize\tName\n";
    String IP;
    //DONT CHANGE THIS PART
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          if(( key.getName().toLowerCase().replace(" ","").contains(query))
              || ( key.getIP().contains(query)) )
           q += key.getSNum() + "\t" + key.getIP() + "\t" + key.getFilesize() + "\t" + key.getName() + "\n";
      }
   if (q.equals(""))
       q= " ";
   return q;
  }
  
  public static Song getSongFromSNum(Hashtable<String, Song> sTable, int num) {
    Song result = null;
    //DONT CHANGE THIS PART
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          if ( key.getSNum()== num) {
            result = key;
          }
      }
   return result;
  }
  
  public static void print(Hashtable<String, Song> sTable) {
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          System.out.printf("%2s%15s%15s%45s", key.getSNum(),key.getIP(),key.getFilesize(),key.getName() );
      }
  }
  
  public static ArrayList<Song> getListFromIP(Hashtable<String, Song> sTable, String ip){
    ArrayList<Song> result = new ArrayList<>();
    //DONT CHANGE THIS PART
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          if ( key.getIP().equals(ip) ) {
            result.add(key);
          }
      }
    return result;
  }
  
  public static ArrayList<String> getListFromIP(Hashtable<String, Song> sTable){
    ArrayList<String> result = new ArrayList<>();
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          if ( !result.contains( key.getIP() ) ) {
            result.add(key.getIP());
          }
      }
    return result;
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
      songs+= temp.getName() + "\t" + temp.getFilesize() + "\t" + temp.getIP() + "\n";
    }
    return songs;
  }
  
  
}