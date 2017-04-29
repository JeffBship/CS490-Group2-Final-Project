package p2p_fileshare;
import java.io.*;
import java.net.*;

/**
 * Simple client connects sends a sentence periodically and outputs the
 * response. This is an adaption of the code provided by the Computer
 * Networking: A Top Down Approach book by Kurose and Ross
 * This class will RECEIVE bytes from a stream and place them into a file
 * @author Adrian and Jeff
 */
public class TCPClient extends Thread {

  private int serverPort;
  private int filesize;
  private String filename;
  private String serverIP;
  int bytesRead;
  int current = 0;
  FileOutputStream outFile = null;
  BufferedOutputStream buffOut = null;
  
  
  public TCPClient(String name, String serverIP, int serverPort, int filesize, String filename) {
    super(name);
    this.serverPort = serverPort;
    this.serverIP = serverIP;
    this.filesize = filesize;
    this.filename = filename;
  }

  /**
   * Start the thread to connect and begin sending
   */
  @Override
  public void run() {
    Socket clientSocket = null;
    try {
      System.out.println("CLIENT opening socket");
      //INSERT IP HERE
      clientSocket = new Socket(serverIP, serverPort);
      System.out.println("CLIENT connected to server");
      //get file
      DataInputStream is = new DataInputStream(clientSocket.getInputStream());
      byte[] bRecv = new byte[filesize];
     // BufferedInputStream is = new BufferedInputStream(in);
      File f = new File(Peer.folder,filename);
      //File f = new File("C:\\Users\\Dad\\Desktop\\490 Music to share\\test3.mp3");
      if(!f.exists())
          f.createNewFile();
      outFile = new FileOutputStream(f);
      buffOut = new BufferedOutputStream(outFile);
      
      is.readFully(bRecv);
     
      buffOut.write(bRecv);
      
      is.close();
      buffOut.flush();
      buffOut.close();
      
      System.out.println("Closing Connection");
      clientSocket.close();
      System.out.println(this.getName() + " closed connection to server");
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (Exception cse) {
        // ignore exception here
      }
    }
  }
}
