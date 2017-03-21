/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

class Message {
  String data = "";
  
  public Message(){
    this.data = "";
  }
  
  public Message(String datapar){
    this.data = datapar;
  }
  
  public void set (String datapar){
    this.data = datapar;
  }
  
  public void transmit(){
    System.out.println("testing 1 2 3");
    System.out.println("This is the message: " + this.data);
    
  }
  
  public static void main(String[] args) {
    System.out.println("message class compiled and ran successfully.");
  }
  
}
