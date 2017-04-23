/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Scanner;
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
    
    public JFileChooser chooser = new JFileChooser();
    public File folder = null;
    public String centralServerIP = "";
    Hashtable<String, Song> Ltab = new Hashtable<>();
    
    /*CODE FOR ALLOWING USER TO INPUT FILE DIRECTORY
      int i = 0;
      File f = null;
      /*
      String directory = "";
      while(i==0){
        Scanner kbd = new Scanner(System.in);
        System.out.print("Please enter desired file path: ");
        directory = kbd.nextLine();
        f = new File(directory);
        if (f.isDirectory())
            i=1;     
        else{
            System.out.println("Please enter a valid directory");          
        }
    }
    */
    
  
    // There's not real logging in, it mostly just checks for a response from the entered IP, using
    // the HTTP req/res system for a proof of concept.
    public void setIP() throws IOException, InterruptedException{
      System.out.print("Enter the IP to look for the Server: ");
      Scanner scan = new Scanner(System.in);
      String tryIP = scan.nextLine();
      
      //Try login attempt...basically a fancy ping
      String code = "L";    // I for inform and update
      String phrase = "L";  // I for inform and update (yes it's the same.  The response are the ones that are different than the code.
      InetAddress LocalIP = InetAddress.getLocalHost(); 
      String IPaddress = LocalIP.getHostAddress();
      String version = "1"; // because we only have one version!
      String payload = "Payload not used";  //this is just a filler
      HTTP login = new HTTP(code, phrase, IPaddress, version, payload);
      
      RDT.transmit( tryIP, Globals.MSG_PORT, login.asString());
      
      HTTP response = new HTTP("200","O","someIP","1","some payload");
      //RDT.listen (needs to be coded)...  get the HTTP response and if check to 200:Okay
      if ( response.getCode().equals("200") ) {
        System.out.println("Login succes, IP is working.");
        centralServerIP = tryIP;
        } else {
          centralServerIP = "";
          System.out.print("Login failed, that IP is not working.");
      }
      
      
    }
    
    public void chooseFolder(){
          // JFileChooser chooser = new JFileChooser();
              //FileNameExtensionFilter filter = new FileNameExtensionFilter(
              //    "mp3 files", "mp3");
              //chooser.setFileFilter(filter);
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
    
    public String getDirectory() throws UnknownHostException{
      String songList= "";
      //folder = new File("C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files");
      File[] listOfFiles = folder.listFiles();
      int j=0;
      for(File file : listOfFiles)
       if(file.isFile() && file.getName().endsWith(".mp3"))
           songList+= file.getName() + "\t" + file.length() + "\t" + InetAddress.getLocalHost().getHostAddress() + "\n"; 
      return songList;
    }
    
    //Add Hashtable to peer...modify to search table in peer instance later
    //Returns User Query as a string to be returned for transmit to Server
    
    public void informAndUpdate(Hash serv) throws UnknownHostException, IOException, InterruptedException{
      System.out.println("Informing Server...");
      String temp = getDirectory();
      //Build the HTTP request   public HTTP(String code, String phrase, String IPaddress, String version, String payload){
      String code = "I";    // I for inform and update
      String phrase = "I";  // I for inform and update (yes it's the same.  The response are the ones that are different than the code.
      String IPaddress = "the CentralServer IP";
      String version = "1"; // because we only have one version!
      String payload = getDirectory();  //this is 
      HTTP inform = new HTTP(code, phrase, IPaddress, version, payload);
      
      
      
      //HTTP Request  transmit the "temp" variable!!!
      // RDT.transmit( Globals.JEFF_PC_IP, Globals.MSG_PORT, temp);
      //rdt.transmit request
      //wait for a response
      //serv.processSongString(temp, serv.getTable());
      System.out.println(temp);
      Song.processSongString(temp,Ltab );
      Song.processSongString(temp, serv.getTable());
      //System.out.println(temp);
      System.out.println("This is HASHTABLE in Server");
      Song.printDirectory(serv.getTable());
      System.out.println("This is HASHTABLE in Peer");
      Song.printDirectory(Ltab);
      
    }
    
    
    
    public String makeQuery(){
         System.out.print("Please enter file name that you would like to query: ");
         String q;
         Scanner query = new Scanner(System.in);
         q = query.nextLine();
         q = q.toLowerCase().replace(" ", "");
         return q;
    }
    
    
    //This Method will handle the bulk of userInteraction with the Server
    public void userInteraction(Hash serv) throws UnknownHostException, IOException, InterruptedException{
    Scanner in = new Scanner(System.in);
    String input = "I";
    String temp;
    System.out.println("Welcome to the network!!!!");
    System.out.println("S: Set Central Server IP");
    System.out.println("F: Select folder");
    System.out.println("I: Inform and Update");
    System.out.println("Q: Query for content");
    System.out.println("R: Request Content");
    System.out.println("E: Exit Network");
    while(!input.equals("E")){
      System.out.print("Enter operation: ");
      input = in.nextLine().toUpperCase();
      
        if(input.equals("I")){
          informAndUpdate(serv);
        }
        else if(input.equals("Q")){
            String query = makeQuery();
            //RDT transmit query 
            serv.processQuery(serv.getTable(), query);
        }
        else if(input.equals("R")){
           System.out.println("Handling Request-Not Yet But Eventually");
        }
        else if(input.equals("F")){
          chooseFolder();
        } 
        else if(input.equals("S")){
          setIP();
        } else
            System.out.println("Please Enter a valid input:");
      }
    System.out.println("Exiting Network and Deleting Corresponding Entries in Server");
    in.close();
  } 
    
}