/**
 * Packet format is:  
 * peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
 * 
 * This is not a DataGram Packet.  Packets are packed into DataGram Packets when sending.
 * 
 * 
 */
package p2p_fileshare;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;

public class Packet {
  byte[] body = new byte[128];
  
public Packet () throws UnsupportedEncodingException{
  this.body = "This is an empty packet".getBytes("UTF-8");
}
  
public Packet (String peerID, String IPAddress, String sequence, String packetsRemaining, String data) 
throws UnsupportedEncodingException, FileNotFoundException, IOException{
  if (peerID.length()>3) System.out.println("Error in packet, PeerID to long");
  if (IPAddress.length()>15) System.out.println("Error in packet, IPAddress to long");
  if (sequence.length()>5) System.out.println("Error in packet, Sequence to long");
  if (packetsRemaining.length()>5) System.out.println("Error in packet, PacketsRemaining to long");
  if (data.length()>95) System.out.println("Error in packet, data to long");
  String contents = peerID + " " + IPAddress + "\n" + sequence + " " + packetsRemaining + "\n" + data + "\n";
  this.body = contents.getBytes("UTF-8");
  if (this.body.length > 128) {
    System.out.println("%%%%%%%%%%  PACKET OVER 128 BYTES  %%%%%%%%%%%%");
    }
    
  }

public Packet (String contents) throws UnsupportedEncodingException{
  this.body = contents.getBytes("UTF-8");
  if (this.body.length > 128) {
    System.out.println("%%%%%%%%%%  PACKET OVER 128 BYTES  %%%%%%%%%%%%");
    }
  }

public boolean isACK(String seqPar) throws UnsupportedEncodingException{
  boolean result = (this.getSequence().equals(seqPar)) && (this.getData().equals("ACK"));
  return result;
}

public boolean isSYN() throws UnsupportedEncodingException{
  boolean result = this.getData().equals("SYN");
  return result;
}

public boolean isFIN() throws UnsupportedEncodingException{
  boolean result = this.getData().equals("FIN");
  return result;
}

   
public String asString()throws UnsupportedEncodingException{
   String result = new String(this.body, "UTF-8"  );
   return result;
}
   
 //peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
public String getpeerID () throws UnsupportedEncodingException{
  String result = "";
  String packetString = this.asString();
  int i = 0;
  do{
    result = result + packetString.charAt(i);
    i++;
  }
  while ( packetString.charAt(i) != ' ' );
  return result;     
}
   
//peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
public String getIPAddress () throws UnsupportedEncodingException{
  String result = "";
  String packetString = this.asString();
  int i = 0;
  while ( packetString.charAt(i) != ' ' ) {i++;} i++;  //move the index past the PeerID and the space
  do{
    result = result + packetString.charAt(i);
    i++;
  }
  while ( packetString.charAt(i) != '\n' );
  return result;     
}
   
 //peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
public String getSequence () throws UnsupportedEncodingException{
  String result = "";
  String packetString = this.asString();
  int i = 0;
  while ( packetString.charAt(i) != ' ' ) {i++;} i++;  //move the index past the PeerID and the space
  while ( packetString.charAt(i) != '\n') {i++;} i++;  //move the index past the IPaddress and the \n
  do{
    result = result + packetString.charAt(i);
    i++;
  }
  while ( packetString.charAt(i) != ' ' );
  return result;     
}

public void setSequence (String newSeq) throws UnsupportedEncodingException{
  String  contents = this.asString();
  int i = 0;
  while ( contents.charAt(i) != ' ' ) {i++;} i++;  //move the index past the PeerID and the space
  while ( contents.charAt(i) != '\n') {i++;} i++;  //move the index past the IPaddress and the \n
  char[] contentChars = contents.toCharArray();
  contentChars[i] = newSeq.charAt(0);
  this.body = new String(contentChars).getBytes("UTF-8");   
}
   
//peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
public String getPacketsRemaining () throws UnsupportedEncodingException{
  String result = "";
  String packetString = this.asString();
  int i = 0;
  while ( packetString.charAt(i) != ' ' ) {i++;} i++;  //move the index past the PeerID and the space
  while ( packetString.charAt(i) != '\n') {i++;} i++;  //move the index past the IPaddress and the \n
  while ( packetString.charAt(i) != ' ' ) {i++;} i++;  //move the index past the sequence and the space
  do{
    result = result + packetString.charAt(i);
    i++;
  }
  while ( packetString.charAt(i) != '\n' );
  return result;     
  }
   
//peerID [0-2] \sp[3] IPaddress[4-19] \n[20] sequence[21-26] \sp[27] packetsRemaining[28-33] \n[34] data[35-126] \n[127]
public String getData () throws UnsupportedEncodingException{
  String result = "";
  String packetString = this.asString();
  int i = 0;
  while ( packetString.charAt(i) != ' ' ) {i++;} i++;  //move the index past the PeerID and the space
  while ( packetString.charAt(i) != '\n') {i++;} i++;  //move the index past the IPaddress and the \n
  while ( packetString.charAt(i) != ' ' ) {i++;} i++;  //move the index past the sequence and the space
  while ( packetString.charAt(i) != '\n' ) {i++;} i++;  //move the index past the packetsRemaining and the \n
  result = packetString.substring(i);                   //takes the remaining characters.
  result = result.substring(0, result.length()-1 );     //remove the \n on the end
  return result;     
  }

public static Packet extractFromDatagram (DatagramPacket DGpacket) throws UnsupportedEncodingException {
      
      byte[] msgBuffer = DGpacket.getData();
      int length = DGpacket.getLength();
      int offset = DGpacket.getOffset();
      int remotePort = DGpacket.getPort();
      String DGpayload = new String(msgBuffer, offset, length);
      //System.out.println("DGpayload: " + DGpayload);
      return new Packet(DGpayload);
    }

  
   
   
   
 }  