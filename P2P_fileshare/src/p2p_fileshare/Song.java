/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

public class Song {
  String filename;
  String filesize;
  String peer;
  
  public Song(){
    this.filename = "";
    this.filesize = "";
    this.peer = "";
  }
  
  public Song(String filenameParam, String filesizeParam, String peerParam){
    this.filename = filenameParam; 
    this.filesize = filesizeParam;
    this.peer = peerParam;
  }
  
  public String getName(){
     return this.filename;
  }
  public String getAll(){
   String result = this.filename + " " + this.filesize + " " + this.peer;
   return result;
  }
  
  public String getKey(){
    String key = this.filename + " " + this.peer;
    return key;
  }
 
  public static void main(String[] args) {
    System.out.println("song class compiled and ran successfully.");
  }
  
  
     
  
}
