/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare.files;

import java.util.Scanner;
import p2p_fileshare.Globals;

/**
 *
 * @author Dad
 */
public class Peer_Menu {
  
  
public static void main(String[] args) {
  
  Scanner scan = new Scanner(System.in);
  String menuOption = "";
  System.out.println("Peer Menu:");
  System.out.println("1:  Set local folder for sharing.");
  System.out.println("2:  Inform/update, display available files.");
  System.out.println("3:  Request file from peer.");
  System.out.println("4:  Exit.");
  System.out.print  ("Enter your selection: ");
  menuOption = scan.nextLine();
  switch (menuOption) {
  
    case "1":{
      setFolder();
      }
      break;
  
    case "2":{
      informAndUpdate();
      }
      break;
  
    case "3":{
      requestFile();
      }
      break;
    case "4":{
      // exit
      }
      break;
    default:{
      System.out.println("That was not a valid option.");
      }      
      break;
  }
  
} 
  
public static void setFolder(){
  
  //Set the local folder
  //Adrian:   Get the local folder and put it in Globals.FOLDER
  System.out.println("Adrian knows how to set folders.");
}

public static void informAndUpdate() {
  System.out.println("Sending list of files in " + Globals.FOLDER + " to server");
  System.out.println("The following files are available for sharing:");
  
// get list of files in directory
}

public static void requestFile(){
  //get the file index number they want
  //send the request
  //process the response
}
  
public static void exit(){
// tell server
// exit the program
}
      
      
      
}
  
