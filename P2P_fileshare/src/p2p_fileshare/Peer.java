/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 *
 * @author Jeff Blankenship and Adrian Ward-Manthey
 * This class will allow contain functionality to allow the user to inform and update
 * It will also allow the user to 
 * 
 * USE IP.isReachable() to check and see if we can reach the IP before establishing a TCP connection
 */
public class Peer {
    
    File folder = null;
    
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
    
    //This method will return a string for transmit...server will handle string parsing
    //NOTE: change so that user can choose directory
    public String getDirectory() throws UnknownHostException{
      String songList= "";
      File folder = new File("C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files");
      File[] listOfFiles = folder.listFiles();
      int j=0;
      for(File file : listOfFiles)
       if(file.isFile() && file.getName().endsWith(".mp3"))
           songList+= file.getName() + "\t" + file.length() + "\t" + InetAddress.getLocalHost().getHostAddress() + "\n"; 
      return songList;
    
    }
    
    //This Method will handle the bulk of userInteraction with the Server
    public void userInteraction(Server serv) throws UnknownHostException{
    Scanner in = new Scanner(System.in);
    String input = "I";
    String temp;
    System.out.println("Welcome to the network!!!!");
    System.out.println("I: Inform and Update");
    System.out.println("Q: Query for content");
    System.out.println("R: Request Conent");
    System.out.println("E: Exit Network");
    while(!input.equals("E")){
      System.out.print("Enter operation: ");
      input = in.nextLine().toUpperCase();
        if(input.equals("I")){
           System.out.println("Informing Server...");
           temp = getDirectory();
           serv.processSongString(temp, serv.getTable());
           System.out.println(temp);
           System.out.println("This is HASHTABLE");
           serv.printServerDirectory(serv.getTable());
        }
        else if(input.equals("Q")){
            serv.processQuery(serv.getTable());
        }
        else if(input.equals("R")){
           System.out.println("Handling Request-Not Yet But Eventually");
        }
        else
            System.out.println("Please Enter a valid input:");
    
      }
    System.out.println("Exiting Network and Deleting Corresponding Entries in Server");
    in.close();
  } 
    
}