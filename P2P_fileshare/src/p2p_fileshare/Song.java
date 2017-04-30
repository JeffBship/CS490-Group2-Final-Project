/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Song {
  Integer SNum;
  String filename;
  String filesize;
  String peer;
  
  
  public Song(){
    SNum = 0;
    filename = "";
    filesize = "";
    peer = "";
  }
  
  public Song(Integer SongNumber, String filenameParam, String filesizeParam, String peerParam){
    SNum = SongNumber;
    filename = filenameParam; 
    filesize = filesizeParam;
    peer = peerParam; 
  }
  
  public String getName(){
     return this.filename;
  }
  
  public String getFilesize(){
      return this.filesize;
  }
  
  public Integer getSNum(){
    return this.SNum;
  }
  
  public String getIP(){
     return this.peer;
  }
  
  public String getAll(){
   String result = " " + this.SNum + "\t" + this.filename + "\t" + this.filesize + "bytes " + "\t" + this.peer;
   return result;
  }
  
  public String getKey(){
    String key = this.filename + " " + this.peer;
    return key;
  }

  
  //###  Might need to change this to return a hashtable instead of using global variables.
  public static void processSongString(String songList, Hashtable<String, Song> dTab){
    
    char[] list = songList.toCharArray();
    Integer snum = 0;
    
    
    //use parse to read up to newline character
    ArrayList<Character> parse = new ArrayList<>();
    
    String temp;
    String name;
    String size;
    String IP;
    char c;
    if (list!=null){
        for(int i = 0; i<list.length; i++){
          c = list[i];
          if(!(c=='\n')) 
            parse.add(c);
          else {
            //This builds a string out of parse
            StringBuilder build = new StringBuilder(parse.size());
            for(Character ch: parse)
                build.append(ch);
            temp = build.toString();
            //uses \t as delimeter for string parsing
            String[] splits = temp.split("\t");
            name = splits[0];
            size = splits[1];
            IP = splits[2];
            dTab.put(name + " " + IP,new Song(++snum, name, size, IP ));
            parse.clear();
          }
          //now dTab is a hashtable of the songList
        } 
    }
    //return result;
  }
 
 public static void printDirectory(Hashtable<String, Song> sTable){
      Enumeration songNames = sTable.elements();
      Song key;
      String space = " ";
      while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          //System.out.println(sTable.get(key).getAll());
          System.out.printf("%2.2s%1.1s%15.15s%15.15s%30.30s", 
              key.getSNum(), space, key.getIP(), key.getFilesize(), key.getName());
          System.out.println();
      }
  }
 

  public static void main(String[] args) {
    System.out.println("song class compiled and ran successfully.");
  }
 
  
}
