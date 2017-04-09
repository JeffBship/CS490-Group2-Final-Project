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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Dad
 */
public class P2P_fileshare {

  /**
   * @param args the command line arguments
   * @throws java.net.UnknownHostException
   */
  
  public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
    System.out.println("P2P_fileshare main program ");  
    String input = "";
    InetAddress TargetIP = InetAddress.getLocalHost();
    TargetIP = InetAddress.getByName(input);
    
    System.out.println("For testing, this will transmit a large message to the target IP.");
    System.out.println("The file RDTack should be running (as a file, not a project) ");
    System.out.println("on the target machine.");
    System.out.println("Local IP is: " + TargetIP.getHostAddress());
    System.out.println("\n Press Enter to transmit to local IP,");
    System.out.print(" or enter IP for other target: ");
    Scanner scan = new Scanner(System.in);
    input = scan.nextLine();
    if (input.length()>0) TargetIP = InetAddress.getByName(input);
    
    RDT.transmit( TargetIP.getHostAddress(), Globals.MSG_PORT, Globals.ALICE);
  }
  
}
