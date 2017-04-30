package p2p_fileshare;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

/**
 * Simple TCP server thread that starts up and waits for TCP connections and
 * echos what is sent capitalized. This is an adaption of the code provided by
 * the Computer Networking: A Top Down Approach book by Kurose and Ross
 * 
 * This class wile be used to SEND files as a stream of bytes in a dataoutputstream
 * 
 * @author Adrian and Jeff
 */
public class TCPServer extends Thread {

  private int port;
  private File targetFile;

  public TCPServer(String name, int port, File targetFile) {
    super(name);
    this.port = port;
    this.targetFile = targetFile;
  }

  /**
   * Start the thread to begin listening
   */
  public void run() {
    ServerSocket serverSocket = null;
    File outFile = null;
    FileInputStream fis = null;
    BufferedInputStream buffRead = null;
    DataOutputStream out = null;
    //BufferedOutputStream out = null;
    
   
    byte[] bSend;    
    try {
      serverSocket = new ServerSocket(this.port);
     
      //while (true) {
        {
        System.out.println("SERVER accepting connections");
        Socket clientConnectionSocket = serverSocket.accept();
        System.out.println("SERVER accepted connection (single threaded so others wait)");
        
        //outFile = new File("C:\\Users\\Surface Book\\Desktop\\Music\\07 - You Shook Me All Night Long.mp3");
        outFile = targetFile;
        
        bSend = new byte[(int) outFile.length()];
        if (Globals.SHOWALL) System.out.println("Array is "+ bSend.length);
        
        //Should read all bytes from file into bSend
        //bSend = Files.readAllBytes(outFile.toPath());
        
        
        fis = new FileInputStream(outFile);
        buffRead = new BufferedInputStream(fis);
        buffRead.read(bSend, 0, bSend.length);
        buffRead.close();
        fis.close();
        
        
        // This is regarding the server state of the connection
        while (clientConnectionSocket.isConnected() && !clientConnectionSocket.isClosed()) {
           out =new DataOutputStream( clientConnectionSocket.getOutputStream());
            // Note if this returns null it means the client closed the connection
          if (out!= null) {
            if (Globals.SHOWALL) System.out.println("SERVER sending file...");
            if (Globals.SHOWALL) System.out.println("Size is" + bSend.length);
            out.write(bSend, 0, bSend.length);
            out.flush();
            out.close();
            clientConnectionSocket.close();
            System.out.println("Done");
            
          } else {
            out.close();
            clientConnectionSocket.close();
            System.out.println("SERVER client connection closed");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (serverSocket != null) {
        try {
          serverSocket.close();
        } catch (IOException ioe) {
          // ignore
        }
      }
    }
  }
}
