/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
/**
 *
 * @author Jeff Blankenship and Adrian Ward-Manthey
 * This class will allow contain functionality to allow the user to inform and update
 * It will also allow the user to 
 * 
 * USE IP.isReachable() to check and see if we can reach the IP before establishing a TCP connection
 */
public class Peer {
    
    public static JFileChooser chooser = new JFileChooser();
    public static File folder = null;
    public static String centralServerIP = "";
    public static int portOffset;
    public static int ackPort;
    public static boolean keepListening;
    
    //
    //  Adrian:  I'll set up the HTTP methods to call this in order to transmit a file.
    //
    public static void TCPtransmit (String filename, String IPaddress){
      
    }
    
    
    // In here we need to find out if the file is available on this peer,
    // so we can return a 404 error if not.
    public static boolean haveFile (String filename){
      boolean result = false;
      
      return result;
    }
    
    
    // There's no real logging in, it mostly just checks for a response from the entered IP, using
    // the HTTP req/res system for a proof of server existence.
    public static void setIP() throws IOException, InterruptedException{
      System.out.print("Enter the IP to look for the Server: ");
      Scanner scan = new Scanner(System.in);
      String tryIP = scan.nextLine();
      
      //Try login attempt...basically a fancy ping
      String code = "L";    // I for inform and update
      String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
      InetAddress LocalIP = InetAddress.getLocalHost(); 
      String IPaddress = LocalIP.getHostAddress();
      String version = "1"; // because we only have one version!
      String payload = "Payload not used";  //this is just a filler
      HTTP login = new HTTP(code, phrase, IPaddress, version, payload);
      
      RDT.transmit( tryIP, Globals.S_PORT, ackPort,  login.asString());
      HTTP loginResponse = RDT.listen(ackPort);
      System.out.println("in login.  received HTTP response as follows: \n" +  loginResponse.display() );
      
      //HTTP response = new HTTP("200","O","someIP","1","some payload");
      //RDT.listen (needs to be coded)...  get the HTTP response and if check to 200:Okay
      if ( loginResponse.getCode().equals("200") ) {
        System.out.println("Login succes, IP is working.");
        centralServerIP = tryIP;
        } else {
          centralServerIP = "";
          System.out.print("Login failed, that IP is not working.");
      }
      
    }
    
    public static void chooseFolder(){
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = chooser.showOpenDialog(null);
      //chooser.setCurrentDirectory(new java.io.File("."));
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        //folder = chooser.getSelectedFile().getName());
        folder =  chooser.getSelectedFile();
        System.out.println("Selected folder: " + folder.getName() );
        // folder = chooser.getSelectedFile().getName());
      }
    }
    
    public static String getDirectory() throws UnknownHostException{
      String songList= "";
      File[] listOfFiles = folder.listFiles();
      int j=0;
      for(File file : listOfFiles)
       if(file.isFile() && file.getName().endsWith(".mp3"))
           songList+= file.getName() + "\t" + file.length() + "\t" + InetAddress.getLocalHost().getHostAddress() + "\n"; 
      return songList;
    }
    
    //Add Hashtable to peer...modify to search table in peer instance later
    //Returns User Query as a string to be returned for transmit to Server
    
    public static void informAndUpdate() throws UnknownHostException, IOException, InterruptedException{
      if (centralServerIP.equals("") || (folder==null) ) {
        System.out.println("Please set Central Server IP and sharing folder first.");
      }else{
        System.out.println("Informing Server...\n\n");
        //Build the HTTP request   
        String code = "I";    // I for inform and update
        String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
        InetAddress LocalIP = InetAddress.getLocalHost(); 
        String IPaddress = LocalIP.getHostAddress();
        String version = "1"; // because we only have one version!
        String payload = getDirectory();  //
        HTTP inform = new HTTP(code, phrase, IPaddress, version, payload);
        RDT.transmit( centralServerIP, Globals.S_PORT, ackPort, inform.asString() );
        if (Globals.SHOWALL) System.out.println("Transmitted \n " + inform.getPayload());
        HTTP informResponse = RDT.listen(ackPort);
        System.out.println("The current files available are:\n" +  informResponse.getPayload() );
      } //end else
    }
    
    public static void requestContent() 
    throws UnknownHostException, IOException, InterruptedException{
      Scanner scan = new Scanner(System.in);
      System.out.println("\nRequest Content.");
      System.out.println("Enter the name of the requested file: ");
      String fileName = scan.nextLine();
      System.out.println("Enter the IP to request it from: ");
      String fileIP = scan.nextLine();
      //Build the HTTP request   public HTTP(String code, String phrase, String IPaddress, String version, String payload){
      String code = "R";    // R for request for file
      String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
      InetAddress LocalIP = InetAddress.getLocalHost(); 
      String IPaddress = LocalIP.getHostAddress();
      String version = "1"; // because we only have one version!
      String payload = fileName;  //
      HTTP request = new HTTP(code, phrase, IPaddress, version, payload);
      RDT.transmit( fileIP, Globals.P_PORT, ackPort, request.asString() );
      HTTP exitResponse = RDT.listen(ackPort);
    }
    
    public static void query() 
    throws UnknownHostException, IOException, InterruptedException{
      String payload = makeQuery();
          String code = "Q";    // E for exit
          String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
          InetAddress LocalIP = InetAddress.getLocalHost();
          String IPaddress = LocalIP.getHostAddress();
          String version = "1"; // because we only have one version!
          HTTP query = new HTTP(code, phrase, IPaddress, version, payload);
          RDT.transmit( centralServerIP, Globals.S_PORT, ackPort, query.asString() );
          HTTP queryResponse = RDT.listen(ackPort);
          System.out.println("query results:\n" +  queryResponse.display() );
      }
    
    public static String makeQuery(){
         System.out.print("Please enter file name that you would like to query: ");
         String q;
         Scanner query = new Scanner(System.in);
         q = query.nextLine();
         q = q.toLowerCase().replace(" ", "");
         return q;
    }
    
    public static void exit() 
    throws UnknownHostException, IOException, InterruptedException{
      if (centralServerIP.equals("") ) {
        System.out.println("You can't exit if you haven't logged in and set a folder.");
      }else{
        System.out.println("Informing Server...");
        String code = "E";    // E for exit
        String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
        InetAddress LocalIP = InetAddress.getLocalHost(); 
        String IPaddress = LocalIP.getHostAddress();
        String version = "1"; // because we only have one version!
        String payload = "";  // sending an empty payload says I have no files to share anymore
        HTTP request = new HTTP(code, phrase, IPaddress, version, payload);
        RDT.transmit( centralServerIP, Globals.S_PORT, ackPort, request.asString() );
        HTTP exitResponse = RDT.listen(ackPort);
        if ( exitResponse.getCode().equals("200") ) {
          System.out.println("Central Server has removed your files from the directory.  Have a peachy day.");
          } else {
          System.out.print("Exit failed.");
          } 
        keepListening = false; //turn off the listening thread
      }
      
    }
    
    
    
    
    //This Method will handle the bulk of userInteraction with the Server
  public static void main(String[] args) 
  throws IOException, UnknownHostException, InterruptedException{
    keepListening = true;
    peerListener ears = new peerListener();
    ears.start();
    Scanner in = new Scanner(System.in);
    portOffset = 0;
    String input = "";
    while(!input.equals("E")){
      ackPort = Globals.BASE_PORT + 200 + portOffset;  //portOffset: separate threads, 200: separarate menu from threads
      portOffset = (portOffset + 1) % 100;
      String temp;
      System.out.println("\nWelcome to the network!!!!");
      System.out.println("S: Set Central Server IP and Log in");
      System.out.println("F: Select sharing folder");
      System.out.println("I: Inform and Update");
      System.out.println("Q: Query for content");
      System.out.println("R: Request Content");
      System.out.println("E: Exit Network");
      System.out.print("Enter desired operation: ");
      input = in.nextLine().toUpperCase();
      
      switch (input) {
        case "I": informAndUpdate();
                  break;
        case "Q": query();
                  break;
        case "R": requestContent();
                  break;
        case "F": chooseFolder();
                  break;
        case "S": setIP();
                  break;
        case "E": exit();
                  break;
        default:  System.out.println("Please Enter a valid input:");
                  break;
        }
      }
    System.out.println("Exiting Network and Deleting Corresponding Entries in Server");
    in.close();
  } 
    
}

class peerListener extends Thread {
  
  public peerListener(){
    //empty constructor right now.
  }
  
  @Override
  public void run() {
    int portOffset = 0;
    int ackPort;
    
    System.out.println("peer listener running, using port " + Globals.P_PORT);
    while (Peer.keepListening){
      HTTP received = new HTTP();
      try {
        received = RDT.listen( Globals.P_PORT  );
      } catch (SocketException ex) {
      } catch (IOException | InterruptedException ex) {
      }
      ackPort = Globals.BASE_PORT + 100 + portOffset;  //portOffset: separate threads, 100: separarate threads
      portOffset = (portOffset + 1) % 100;
      peerRequestHandler newThread = new peerRequestHandler(received, ackPort);
      newThread.start();
    }
  }
}

class peerRequestHandler extends Thread {
  
  HTTP received;
  int ackPort; 
  
  public peerRequestHandler(HTTP received, int ackPort){
    this.received = received;
    this.ackPort = ackPort;
  }
  
  @Override
  public void run() {
    System.out.println("peerRequestHandler running, using port " + ackPort);
    InetAddress LocalIP = null;  
    try {
      LocalIP = InetAddress.getLocalHost();
    } catch (UnknownHostException ex) {
    }
    HTTP response = new HTTP();
    System.out.println("HTTP received by peer: \n");
    System.out.println(received.display());
    int responsePort = Integer.parseInt(received.getPhrase() );
    switch (received.getCode()) {
      case "R": // REQUEST FOR CONTENT  should have gone to a peer
                System.out.println("processing R on peer");
                boolean hasFile = true;  //need some check to see if the file is on the peer
                if (hasFile) {
                  //###############  Insert TCP send stuff here ###################
                  System.out.println("In file handling thread.  TCP transfer should happen now....");
                  response = new HTTP("200","F",LocalIP.getHostAddress(),"1","Enjoy your new file.");
                } else {
                  response = new HTTP("404","F",LocalIP.getHostAddress(),"1","File not found");
                }
                break; 
      default:  // request did not match any of the expected cases.
                System.out.println("processing Default");
                response = new HTTP("400","B",LocalIP.getHostAddress(),"1","Peer received bad request.");
                break;
      }
    try {
      RDT.transmit( received.getIPaddress(), responsePort, ackPort, response.asString() );
    } catch (IOException | InterruptedException ex) {
    }
  }
}
