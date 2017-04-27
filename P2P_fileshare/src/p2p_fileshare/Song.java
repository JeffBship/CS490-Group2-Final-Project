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

  //!!!!!add in functionality to erase entries no longer in directory!!!!!
  
  //###  Might need to change this to return a hashtable instead of using global variables.
  public static void processSongString(String songList, Hashtable<String, Song> dTab){
    //parse into char works!!!!
    char[] list = songList.toCharArray();
    Integer snum = 0;
    System.out.println("CHAR ARRAY");
    for(char f: list)
        System.out.print(f);
    //use parse to read up to newline character
    ArrayList<Character> parse = new ArrayList<>();
   
    String temp;
    String name;
    String size;
    String IP;
    char c;
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
    } 
  }
 
 public static void printDirectory(Hashtable<String, Song> sTable){
      Enumeration songNames = sTable.keys();
      String key;
      while(songNames.hasMoreElements()){
          key = (String) songNames.nextElement();
          System.out.println(sTable.get(key).getAll());
      }
  }
 

  public static void main(String[] args) {
    System.out.println("song class compiled and ran successfully.");
  }
 
  
}
