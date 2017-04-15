/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.net.InetAddress;

public class Song {
  String filename;
  Long filesize;
  InetAddress peer;
  Integer SNum;
  
  public Song(){
    this.filename = "";
    this.filesize = 0L;
    this.peer = null;
    this.SNum = 0;
  }
  
  public Song(Integer SongNumber, String filenameParam, Long filesizeParam, InetAddress peerParam){
    this.filename = filenameParam; 
    this.filesize = filesizeParam;
    this.peer = peerParam;
    this.SNum = SongNumber;
  }
  
  public String getName(){
     return this.filename;
  }
  
  public Long getFilesize(){
      return this.filesize;
  }
  
  public Integer getSNum(){
    return this.SNum;
  }
  
  public InetAddress getIP(){
     return this.peer;
  }
  
  public String getAll(){
   String result = " " + this.SNum + " " + this.filename + " " + this.filesize + " bytes " + " " + this.peer;
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
