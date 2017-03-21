  /**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * 
 */
package p2p_fileshare;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

class Message {
  String data = "";
  
  public Message(){
    this.data = "";
  }
  
  public Message(String datapar){
    this.data = datapar;
  }
  
  public void setData (String datapar){
    this.data = datapar;
  }
  
  public String getData(){
    return this.data;
  }
  
  public void transmitToTerminal(){
    System.out.print  ("Transmitted to Terminal:  ");
    System.out.println(this.data);
  }

  public void transmit(String destinationIP, int destinationPort) throws IOException{
        Socket socket = null;
        //String destinationIP = "192.168.1.46";
        //int destinationPort = 55555;
        socket = new Socket(destinationIP, destinationPort);
        OutputStream out = socket.getOutputStream();
        byte[] bytes = this.getData().getBytes("UTF-8");
        int length = bytes.length;
        if (length>128) System.out.println("Length is more than 128, xmit truncated.");
        length = 128;
        out.write(bytes,0,length);
        
        out.close();
        socket.close();
    }
  
  public static void main(String[] args) {
    System.out.println("message class compiled and ran successfully.");
  }
  
}
