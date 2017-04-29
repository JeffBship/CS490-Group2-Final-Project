package p2p_fileshare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Simple TCP server and client, connects sends a sentence periodically, the
 * server responds with the sentence capitalized. This is an adaption of the
 * code provided by the Computer Networking: A Top Down Approach book by Kurose
 * and Ross
 *http://www.rgagnon.com/javadetails/java-0542.html
 * @author Chad Williams
 */
public class Main {

  public static void main(String[] args) throws FileNotFoundException, IOException {
    //TEST FILE READER CAPABILITY
/*    
String name = "newfile";
File inFile = new File("C:\\Users\\Dad\\Desktop\\Music\\" + name + ".mp3");

File outFile = new File("C:\\Users\\Dad\\Desktop\\Music\\morenew.mp3");
FileInputStream inStream = new FileInputStream(inFile);
FileOutputStream outStream = new FileOutputStream(outFile);
byte[] buffer = new byte[(int)inFile.length()];
int len;
while ((len = inStream.read(buffer)) != -1) {
    outStream.write(buffer, 0, len);
}
inStream.close();
outStream.close();
    */
    
    //TCPServer serverThread = null;
    TCPClient clientThread = null;
    
    try {
      // Start server
      //serverThread = new TCPServer("Server", 49000);
      //serverThread.start();

      // Create client
      TCPClient client1 = new TCPClient("CLIENT1", 49000);
      client1.start();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
