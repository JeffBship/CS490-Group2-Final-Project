package p2p_fileshare;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple client connects sends a sentence periodically and outputs the
 * response. This is an adaption of the code provided by the Computer
 * Networking: A Top Down Approach book by Kurose and Ross
 *
 * @author Chad Williams
 */
public class TCPClient extends Thread {

  private int serverPort;
  

  public TCPClient(String name, int serverPort) {
    super(name);
    this.serverPort = serverPort;
  }

  /**
   * Start the thread to connect and begin sending
   */
  @Override
  public void run() {
    Socket clientSocket = null;
    FileOutputStream oStream = null;
    BufferedOutputStream buff = null;
    int bytesRead;
    int current = 0;
    try {
      //LATER ON GET THIS DESTINATION FROM PEER Folder itself
      File fDestination = new File("C:\\Users\\JeffBship\\Test");
      oStream = new FileOutputStream(fDestination);
      //Receive File
      byte[] array = new byte[3000000];
      System.out.println("CLIENT opening socket");
      clientSocket = new Socket("192.168.2.32", serverPort);
      System.out.println("CLIENT connected to server");
      InputStream in = clientSocket.getInputStream();
     //wrapping oStream inside of buff is faster
      oStream = new FileOutputStream(fDestination);
      buff = new BufferedOutputStream(oStream);
      bytesRead = in.read(array, 0, array.length);
      current = bytesRead;
      
      do{
        bytesRead = in.read(array, 0, array.length-current);
        if(bytesRead >=0) 
          current +=bytesRead;
      } while(bytesRead>-1);
      
      buff.write(array, 0, current);
      buff.flush();
      System.out.println("File Received");
      
       /*
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("CLIENT opening socket");
      clientSocket = new Socket("192.168.2.32", serverPort);
      System.out.println("CLIENT connected to server");
      
      //DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      int songBytes = inFromServer.read();
      oStream.write(songBytes);
      System.out.println("Received file!");
              
      
      */
      //Thread.sleep(180000);
      clientSocket.close();
      oStream.close();
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
