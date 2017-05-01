/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
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
    public static int TCPport;
    public static volatile boolean keepListening;
    public static String localDirectory = "";
    static Hash LocalHash = new Hash();
    
    public static int pingTimeout = 1000;

    
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
      String payload = "THIS IS THE LOGIN ATTEMPT HTTP";  //this is just a filler
      HTTP login = new HTTP(code, phrase, IPaddress, version, payload);
      
      RDT.transmit( tryIP, Globals.S_PORT, ackPort,  login.asString());
      HTTP loginResponse = RDT.listen(ackPort);
      //System.out.println("in login.  received HTTP response as follows: \n" +  loginResponse.display() );
      
      //HTTP response = new HTTP("200","O","someIP","1","some payload");
      //RDT.listen (needs to be coded)...  get the HTTP response and if check to 200:Okay
      if ( loginResponse.getCode().equals("200") ) {
        System.out.println("\nLogin succes, There is a server running on that IP.");
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
    
    //if(file.isFile() && file.getName().endsWith(".mp3"))
    
    public static String getDirectory() throws UnknownHostException{
      String songList= "";
      File[] listOfFiles = folder.listFiles();
      int j=0;
      for(File file : listOfFiles)
        
       if(file.isFile() )
           songList+= file.getName() + "\t" + file.length() + "\t" + InetAddress.getLocalHost().getHostAddress() + "\n"; 
      return songList;
    }
    
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
        LocalHash = new Hash();  //start over with an empty hash table, fill it with contents from server
        //LocalHash.getTable().clear();
        Song.processSongString(informResponse.getPayload(), LocalHash.getTable());
        System.out.println("These files are available:");
        
        Song.printDirectory(LocalHash.getTable());
        System.out.println();
      } 
    }
    
    
    
    public static void requestContent() 
    throws UnknownHostException, IOException, InterruptedException{
      
      
      //public Song getSongFromSNum(Hashtable<String, Song> sTable, int num) 
      Scanner scan = new Scanner(System.in);
      System.out.println("\nRequest Content.");
      System.out.println("Enter the number of the song you want: ");
      int num = scan.nextInt(); scan.nextLine();
      Song songReq = new Song();
      songReq = Hash.getSongFromSNum(LocalHash.getTable(), num);
        
      getFile(songReq);
      
      
    }
    
    
  public static void getFile (Song songReq) 
  throws UnknownHostException, IOException, InterruptedException {
    //ping the target.  if ping= true request the file
    if ( pingClient( songReq.getIP() ) ){
        System.out.println("That peer is online." );
        int filesize = Integer.parseInt(songReq.getFilesize());
        String fileName = songReq.getName();
        String fileIP = songReq.getIP();
        //Build the HTTP request   public HTTP(String code, String phrase, String IPaddress, String version, String payload){
        String code = "R";    // R for request for file
        String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
        InetAddress LocalIP = InetAddress.getLocalHost(); 
        String IPaddress = LocalIP.getHostAddress();
        String version = "1"; // because we only have one version!
        String payload = fileName;
        HTTP request = new HTTP(code, phrase, IPaddress, version, payload);
        RDT.transmit( fileIP, Globals.P_PORT, ackPort, request.asString() );
        HTTP exitResponse = RDT.listen(ackPort);
        if (exitResponse.getCode().equals("404")) {
          System.out.println("The file was not found at the requested IP.");
          System.out.println("Be sure of spelling, and include the filetype extension.");
        } else if (exitResponse.getCode().equals("200")) {//If the file is available, start the TCP
          TCPClient clientThread = null;
          int rcvTCPport = Integer.parseInt(exitResponse.getPayload());
          try {  
            // Create client
            TCPClient client1 = new TCPClient("CLIENT1", fileIP ,rcvTCPport, filesize, fileName);
            client1.start();
          } catch (Exception e) {
          }
          System.out.println("Look in " + folder + ".  Enjoy your new file.");
        }
      } else {
        System.out.println("That peer is not responding." );
      }
    }
    
    public static void allfiles() 
    throws IOException, UnknownHostException, InterruptedException{
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter the number of any song from the peer you want all the songs from: ");
      int num = scan.nextInt(); scan.nextLine();
      Song songReq = new Song();
      songReq = Hash.getSongFromSNum(LocalHash.getTable(), num);
      
      System.out.println("IP wanted is: " + songReq.getIP() );
      
      ArrayList<Song> getThese = new ArrayList<>();
      getThese = Hash.getListFromIP(LocalHash.getTable(), songReq.getIP());
      System.out.println("Getting " + getThese.size() + " files from " + songReq.getIP() );
      
      //This is going to be innefficient.  Might want to clean it up later if we have extra time.
      ackPort = Globals.BASE_PORT + 200 + portOffset;  //portOffset: separate threads, 200: separarate menu from threads
      portOffset = (portOffset + 1) % 100;
      
      while (!getThese.isEmpty()){
        getFile(getThese.remove(0));
      }
    }
    
    public static void query() 
    throws UnknownHostException, IOException, InterruptedException{
      String payload = makeQuery();
          /*
          String code = "Q";    // E for exit
          String phrase = Integer.toString(ackPort);  // Using phrase for ackport to make threads have differnt ports
          InetAddress LocalIP = InetAddress.getLocalHost();
          String IPaddress = LocalIP.getHostAddress();
          String version = "1"; // because we only have one version!
          HTTP query = new HTTP(code, phrase, IPaddress, version, payload);
          RDT.transmit( centralServerIP, Globals.S_PORT, ackPort, query.asString() );
          HTTP queryResponse = RDT.listen(ackPort);
          System.out.println("query results:\n" +  queryResponse.display() );
          */
      }
    
    public static String makeQuery(){
      //public Song getSongFromSNum(Hashtable<String, Song> sTable, int num) 
      String q; 
      Scanner scan = new Scanner(System.in);
      System.out.println("\nRequest Content.");
      System.out.print("Enter the number of the song you want: ");
      String numString = scan.nextLine(); //scan.nextLine();
      int num = Integer.parseInt(numString);
      Song songReq = new Song();
      //System.out.println("num is " + num);
      songReq = Hash.getSongFromSNum(LocalHash.getTable(), num);
        
      q= songReq.getName();
      //System.out.println("q is " + q);
      System.out.println( Hash.processQuery(  LocalHash.getTable(), q ) );
      
      return q;
      
      
      /*
         System.out.print("Please enter file name that you would like to query: ");
         String q;
         Scanner query = new Scanner(System.in);
         q = query.nextLine();
         q = q.toLowerCase().replace(" ", "");
         return q;
      */
    }
    
    public static void ping(){
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter the number of any song from the peer you want to ping: ");
      int num = scan.nextInt(); scan.nextLine();
      Song songReq = new Song();
      songReq = Hash.getSongFromSNum(LocalHash.getTable(), num);
      
      System.out.println("IP wanted is: " + songReq.getIP() );
      
      //pingClient poke = new pingClient( songReq.getIP() );
      //poke.start();
      //boolean pingtest = pingClient( songReq.getIP() );
      if (pingClient(songReq.getIP())){
        System.out.println("That peer is online." );
      } else {
        System.out.println("That peer is not responding." );
      }
    }
    
    
    public static void exit() 
    throws UnknownHostException, IOException, InterruptedException{
      if (!centralServerIP.equals("") ) {
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
      }
    }
    
  //This Method will handle the bulk of userInteraction with the Server
  public static void main(String[] args) 
  throws IOException, UnknownHostException, InterruptedException{
    keepListening = true;
    
    //This listens for HTTP requests from other peers
    peerListener peerEars = new peerListener();
    peerEars.start();
    
    //This listens for TCP requests so simulate a ping.
    pingServer pingEars = new pingServer();
    pingEars.start();
    
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
      System.out.println("A: Request ALL files form a peer");
      System.out.println("P: Ping a peer");
      System.out.println("E: Exit Network");
      System.out.print("Enter desired operation: ");
      input = in.nextLine().toUpperCase();
      switch (input) {
        case "S": setIP();
                  break;
        case "F": chooseFolder();
                  break;
        case "I": informAndUpdate();
                  break;
        case "Q": query();
                  break;
        case "R": requestContent();
                  break;
        case "A": allfiles();
                  break;
        case "P": ping();
                  break;
        case "E": exit();
                  break;
        default:  System.out.println("Please Enter a valid input:");
                  break;
        }
      }
    keepListening = false;
    //Can't figure out how to stop the ears threads.
  } 


private static class peerListener extends Thread {
  
  private peerListener(){
    //empty constructor right now.
  }
  @Override
  public void run() {
    int portOffset = 0;
    int ackPort;
    System.out.println("peer listener running, using port " + Globals.P_PORT);
    while (keepListening){
      HTTP received = new HTTP();
      try {
        //System.out.println("just inside try");
        received = RDT.listen(Globals.P_PORT);
        System.out.println("there was a http received by peer");
        System.out.println(received.asString());
      } catch (SocketException ex) {
      } catch (IOException | InterruptedException ex) {
        break;
      }
      TCPport = Globals.BASE_PORT + 100 + portOffset;  //portOffset: separate threads, 100: separarate threads
      portOffset = (portOffset + 1) % 100;
      peerRequestHandler newThread = new peerRequestHandler(received, TCPport);
      newThread.start();
      
    }
  }
}

private static class peerRequestHandler extends Thread {
    HTTP received;
    int ackPort; 
    
  private peerRequestHandler(HTTP received, int ackPort){
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
                File targetFile = new File(Peer.folder,  received.getPayload());
                boolean hasFile = true;  //need some check to see if the file is on the peer
                if (targetFile.isFile()) {
                  //###############  Insert TCP send stuff here ###################
                  //System.out.println("In file handling thread.  File is available.  TCP transfer should happen now....");
                  TCPServer serverThread = null;
                  try {
                    // Start server  TCPServer(String name, int port, String filename) {
                    serverThread = new TCPServer("Server", TCPport, targetFile );
                    serverThread.start();
                  } catch (Exception e) {
                  }
                  response = new HTTP("200","O",LocalIP.getHostAddress(),"1", Integer.toString(TCPport) );
                } else {
                  response = new HTTP("404","F",LocalIP.getHostAddress(),"1","File not found.");
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

//This thread is just to accept pings as TCP socket reuests
  private static class pingServer extends Thread {
    
    public pingServer( ){
    }
    
    @Override
    public void run() {
      ServerSocket pingSocket = null;
      System.out.println("Ping listener accepting pings on port " + Globals.PING_PORT); 
      while (keepListening){
        try {
          pingSocket = new ServerSocket(Globals.PING_PORT);
          while (true) {
            Socket clientConnectionSocket = pingSocket.accept();
            //System.out.println("Listener accepted a socket");
            // This is regarding the server state of the connection
          }
        } catch (IOException e) {
        } 
      }
    }
  }

  
  
  //This tries to open a TCP socket with the serverIP, then closes it.
  //If the socket opens, the ping is true.  if not, ping is false.
  public static boolean pingClient(String serverIP) {
      boolean result = false;
      Socket clientSocket = null;
      try {
        //clientSocket = new Socket(serverIP, Globals.PING_PORT);
        
        
        InetSocketAddress ipPort = new InetSocketAddress(serverIP, Globals.PING_PORT);
        //creating an unbound socket, then connecting allows a timeout to be used in the connect method.
        Socket newSocket = new Socket();
        newSocket.connect(ipPort, pingTimeout);
        
        
        result = true;
      } catch (IOException e) {
        result = false;  //it's false if the socket won't open, creating an exception
      }
      if (clientSocket != null){
          if (clientSocket.isConnected() && !clientSocket.isClosed()) {
            try {
              clientSocket.close();
            } catch (IOException ex) {
            }
          }
      }
      return result;
    }
  


}// end class Peer
