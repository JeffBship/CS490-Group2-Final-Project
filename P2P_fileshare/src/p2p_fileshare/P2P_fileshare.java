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
    System.out.println("P2P_fileshare main program compiled. \n"
        + "From here the user will choose Server or Peer and \n"
        + "the appropriate app will start.   ");
    
    InetAddress LocalIP = InetAddress.getLocalHost(); 
    
    System.out.println("Local IP is: " + LocalIP.getHostAddress());
    
    RDT.transmit( Globals.ADRIAN_LAPTOP_IP, Globals.MSG_PORT, Globals.ALICE);
  }
  
}
