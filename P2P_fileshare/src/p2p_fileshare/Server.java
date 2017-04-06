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
  public static void main(String[] args){
      
   Enumeration songNames;
   String key;
  /*Declare a HashMap*/
  Hashtable<String, Song> dTab = new Hashtable<>();
  
  /*Song Objects*/
  Song s1 = new Song(1,"AnalRupture","128KB","Adrian");
  Song s2 = new Song(2,"AnalRedemption", "128KB","Jeff");
  Song s3 = new Song(3, "Anime Was a Mistake", "OKB", "Ewan McGregor");
  /*Add elements to Hashtable*/
  /*Is using the entire string as the key ok*/
  dTab.put(s1.getAll(), s1);
  dTab.put(s2.getAll(), s2);
  dTab.put(s3.getAll(), s3);
  /*Use Enumerator to print dTab*/
  songNames = dTab.keys();
  /*Work in Progress..Need to deal with Song Objects in Packet.java*/
  String outputStream="";
  while(songNames.hasMoreElements()){
    key = (String) songNames.nextElement();
    System.out.println("Key: " + key);
    outputStream = outputStream + key + "\n";
  }
  
  //System.out.print("OUTPUTSTREAM" + outputStream);
  //!!!!!!!!!!!!!!
  /*Test code to read files from a directory*/
  //!!!!!!!!!!!!!!!
  List<String> names;
  names = new ArrayList<>();
  /*
             Things to Figure Out
    1) How to get associated IP addresses with the songs
    2) How to get user to input directory themself (perhaps with scanner...)
  */
  
  // Adrian:  This doesn't work from my maching.  I think it needs a relative file tree.
  
  System.out.println();
  System.out.println();
  //Need to make it so that user can designate the directory
  File folder = new File("C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files");
  File[] listOfFiles = folder.listFiles();
  for(File file : listOfFiles){
   if(file.isFile() && file.getName().endsWith(".mp3"))
    names.add(file.getName());
  }
  
  for(int i = 0; i<names.size(); i++){
      System.out.println(names.get(i));
  }
  
 }
  
  public static void processSongArray(){
      //This class will take an array from a peer and add it into the hashtable
  }
  
  public void printServerDirectory(){
      //This class will print all entries in the hashtable along with the associated IP addresses of the peers who own them
  }
  
  
  
}