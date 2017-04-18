/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;



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
 
  public static void main(String[] args) {
    System.out.println("song class compiled and ran successfully.");
  }
 
  
}
