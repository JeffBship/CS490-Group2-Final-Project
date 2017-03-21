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

public class StringListener {

  public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try { serverSocket = new ServerSocket(55000);
        } catch (IOException ex) {System.out.println("Port not available. ");
        }

        Socket socket = null;
        InputStream inputStream = null;

        try { socket = serverSocket.accept();
        } catch (IOException ex) {System.out.println("Error: accept socket failed. ");
        }

        try { inputStream = socket.getInputStream();
        } catch (IOException ex) {System.out.println("Error: get input stream failed. ");
        }

        byte[] bytes = new byte[128];
        inputStream.read(bytes);
        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("\n The message is: " + message);

        inputStream.close();
        socket.close();
        serverSocket.close();
    }  
}
