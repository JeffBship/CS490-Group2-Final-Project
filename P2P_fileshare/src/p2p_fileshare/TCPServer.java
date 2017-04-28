package p2p_fileshare;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

/**
 * Simple TCP server thread that starts up and waits for TCP connections and
 * echos what is sent capitalized. This is an adaption of the code provided by
 * the Computer Networking: A Top Down Approach book by Kurose and Ross
 *
 * @author Chad Williams
 */
public class TCPServer extends Thread {

  private int port;
  public FileInputStream inStream=null;
  public BufferedInputStream buff = null;
  public OutputStream out = null;
  
  public TCPServer(String name, int port) {
    super(name);
    this.port = port;
  }

  /**
   * Start the thread to begin listening
   */
  @Override
  public void run()   {
    ServerSocket serverSocket = null;
    try {
      //String clientSentence;
      //String capitalizedSentence;
      serverSocket = new ServerSocket(this.port);
      while (true) {
        System.out.println("SERVER accepting connections");
        Socket clientConnectionSocket = serverSocket.accept();
        System.out.println("SERVER accepted connection (single threaded so others wait)");
        
        //NEED TO SOMEHOW GET FILE NAME FROM THE QUERY
        
        
        // This is regarding the server state of the connection
        //while (clientConnectionSocket.isConnected() && !clientConnectionSocket.isClosed()) {
            //byte[] array = Files.readAllBytes(new File("C:\\Users\\Surface Book\\Desktop\\Music\\Silver.mp3").toPath());
        File fSend = (new File("C:\\Users\\Surface Book\\Desktop\\Music\\Break.mp4"));
        byte[] array = new byte[(int)fSend.length()];
        inStream = new FileInputStream(fSend);
        buff = new BufferedInputStream (inStream);
        buff.read(array, 0, array.length);
        out = clientConnectionSocket.getOutputStream();
        System.out.println("Send file... ");
        out.write(array, 0, array.length);
        out.flush();
        
     //   }
        System.out.println("FINISHED");
        
        /*
        // This is regarding the server state of the connection
        while (clientConnectionSocket.isConnected() && !clientConnectionSocket.isClosed()) {
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
          DataOutputStream outToClient = new DataOutputStream(clientConnectionSocket.getOutputStream());
          //clientSentence = inFromClient.readLine();
          // Note if this returns null it means the client closed the connection
          if (array != null) {
            //System.out.println("SERVER Received: " + clientSentence);
            //capitalizedSentence = clientSentence.toUpperCase() + '\n';
           // System.out.println("SERVER responding: " + capitalizedSentence);
           System.out.println("SERVER responding: \n");
           int len;
           int n = 0;
           while (-1 != (n = bis.read(array)))  {
              outToClient.write(array, 0, n);
           }
            //outToClient.write(array);      
            System.out.println("Just Sent the Byte Array");
            
          } else {
            clientConnectionSocket.close();
            System.out.println("SERVER client connection closed");
          }
        }
        */
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
 
    //FINALLY????
  }
}
