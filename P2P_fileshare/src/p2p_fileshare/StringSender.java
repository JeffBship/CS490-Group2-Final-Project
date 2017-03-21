/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringsender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class StringSender {

  public static void main(String[] args) throws IOException {
        Socket socket = null;
        //String destinationIP = "149.152.23.48";
        //String destinationIP = "73.69.81.72"; //jeff pc public ip
        //String destinationIP = "192.168.1.46"; // jeff pc local ip
        String destinationIP = "192.168.1.118"; // jeff laptop local ip
        int destinationPort = 50000;
        socket = new Socket(destinationIP, destinationPort);
        OutputStream out = socket.getOutputStream();
        
        String message = "This is a big long String that represents a message we want to send. \naaaaaa \n bbbbbbbb \n ccccccccc \n dddddddd \neeeeeeeeee 12345fffffff gggggggg hhhhhhh";
        byte[] bytes = message.getBytes("UTF-8");
        int length = bytes.length;
        if (length>128) System.out.println("Length is more than 128, message will be truncated.");
        
        out.write(bytes,0,length);
        
        out.close();
        socket.close();
    }
  
}
