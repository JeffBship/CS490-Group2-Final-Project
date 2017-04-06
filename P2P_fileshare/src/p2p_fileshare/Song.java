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
  Integer SNum;
  
  public Song(){
    this.filename = "";
    this.filesize = "";
    this.peer = "";
    this.SNum = 0;
  }
  
  public Song(Integer SongNumber, String filenameParam, String filesizeParam, String peerParam){
    this.filename = filenameParam; 
    this.filesize = filesizeParam;
    this.peer = peerParam;
    this.SNum = SongNumber;
  }
  
  public String getName(){
     return this.filename;
  }
  public String getAll(){
   String result = " " + this.SNum + " " + this.filename + " " + this.filesize + " " + this.peer;
   return result;
  }
  
  public Integer getSNum(){
    return SNum;
  }
  
  public String getKey(){
    String key = this.filename + " " + this.peer;
    return key;
  }
 
  public static void main(String[] args) {
    System.out.println("song class compiled and ran successfully.");
  }
  
  
     
  
}
