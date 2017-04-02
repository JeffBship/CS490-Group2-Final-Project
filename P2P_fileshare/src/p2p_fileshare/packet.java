/**
 * Packet format is:  
 * peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
 */
package p2p_fileshare;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class packet {
  byte[] body = new byte[128];
  
  public packet () throws UnsupportedEncodingException{
    this.body = "This is an empty packet".getBytes("UTF-8");
  }
  
  public packet (String peerID, String IPAddress, String sequence, String packetsRemaining, String data) throws UnsupportedEncodingException{
    
      if (peerID.length()>3) System.out.println("Error in packet, PeerID to long");
      if (IPAddress.length()>15) System.out.println("Error in packet, IPAddress to long");
      if (sequence.length()>5) System.out.println("Error in packet, Sequence to long");
      if (packetsRemaining.length()>5) System.out.println("Error in packet, PacketsRemaining to long");
      if (data.length()>95) System.out.println("Error in packet, data to long");
      String contents = peerID + " " + IPAddress + "\n" + sequence + " " + packetsRemaining + "\n" + data;
      this.body = contents.getBytes("UTF-8");
  }
  
  @Override
  public String toString(){
    return Arrays.toString(this.body);
  }
  
}    
  