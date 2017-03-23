/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * This is some test code to send and receive 128 byte strings via sockets.
 */


package p2p_fileshare;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class StringListener1 {

  public static void main(String[] args) throws IOException {
    
    new Timer(20).start();
    new Timer2(30).start();
         
    }  
}