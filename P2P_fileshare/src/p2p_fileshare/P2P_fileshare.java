/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * This is some test code to send and receive 128 byte strings via sockets.
 * 
 * 
 * Sound file IndustrialAlarm courtesy of http://soundbible.com/287-Industrial-Alarm.html
 * 
 */


package p2p_fileshare;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import static p2p_fileshare.Peer.chooser;
import static p2p_fileshare.Peer.folder;

/**
 *
 * @author Dad
 */
public class P2P_fileshare {
  
  public static JFileChooser chooser = new JFileChooser();
  public static File folder = null;
  
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
  
  public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
    
    InetAddress LocalIP = InetAddress.getLocalHost(); 
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
    System.out.println("Run one of the files CentralServer.java or Peer.java to use the application.");
    
    chooseFolder();
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter the name of the requested file: ");
    String fileName = scan.nextLine();
    
    String path = folder.getPath();
    File targetFile = new File(folder,fileName);
    System.out.println("target is " + targetFile);
    System.out.println("That is a file:  " + targetFile.isFile() );
    
    
  }
  
}
