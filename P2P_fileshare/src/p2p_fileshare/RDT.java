/**
 * Jeff Blankenship, Adrian Ward-Manthey
 * CS 490 Final Project
 * Prof Williams
 * 
 * Notes:
 * The last packet in the list has packetsRemaining = 1  (not 0)
 * 
 * 
 */
package p2p_fileshare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.abs;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

class RDT  {
  
  //Timeout calculation variables
  static double estimatedRTT;  
  static double timout;
  static double devRTT;
    
  
  public static DatagramSocket MSGSocket;
  
  
  public static void transmit(String transmitIP, int transmitPort, int ackPort, String transmitMessage)
  throws IOException, InterruptedException{
    if (Globals.SHOWALL) { System.out.println("In Transmit,  to IP " + transmitIP + " to Port " + transmitPort); }
    ArrayList<Packet> packetList = new ArrayList<>();
    //This block creates header data to use in the packets
    String LocalPeerID = Integer.toString(ackPort);  
    //Using PeerID to send the ackPort info..
    InetAddress LocalIP = InetAddress.getLocalHost();
    String LocalIPString = LocalIP.getHostAddress();
    String sequence = "0";
    String packetsRemaining = "";
    int packetsRemainingInt = 0;
    String packetData = "";
    
    //Timeout calculation data re-initializes for each packetList
    boolean finished = false;
    estimatedRTT = Globals.INIT_EST_RTT;  
    timout = Globals.INIT_TIMEOUT;
    devRTT = Globals.INIT_DEV_RTT;
    
    packetsRemainingInt = (int) Math.ceil( transmitMessage.length() / 90.0 ) +2 ; //+2 for the SYN and FIN
    // SYN packet isn't a handshake, it tells the receiver to start sequence count over
    // so if a previous message was dropped it can't jinx the sequence.
    sequence = "0";
    Packet newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining , "SYN");
    packetList.add(newPacket); 
    packetsRemainingInt--;
    sequence = "1";
    //Build the packetList by taking 95 byte chunks of the message
    while (packetsRemainingInt>1){
      if (Globals.SHOWALL) System.out.println("in packetsRemaining loop, : " + packetsRemainingInt);
      packetsRemaining = Integer.toString(packetsRemainingInt);
      int endIndex = 89;
      String newPacketData;
      if (transmitMessage.length()>95){
        newPacketData = transmitMessage.substring(0, 89); //first 94 bytes of message
        transmitMessage = transmitMessage.substring(89); //all after the first 95
        }else{
          newPacketData = transmitMessage;
        }
      newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining, newPacketData);
      packetList.add(newPacket); //adds newPacket to the end of the list.
      if (sequence.equals("0")) 
        { sequence = "1";} 
        else {sequence = "0";}
      packetsRemainingInt--;
    }
    //add FIN packet on the end
    newPacket = new Packet(LocalPeerID, LocalIPString, sequence, packetsRemaining, "FIN");
    packetList.add(newPacket); //adds newPacket to the end of the list.
    
    
    int totalPackets = packetList.size()-1;
    for (int i=0;i<packetList.size();i++){
      rdt_send(transmitIP, transmitPort, packetList.get(i));
      String currentSeq = packetList.get(i).getSequence();
      if (Globals.SHOWALL) {
        System.out.println("------------------------ NEXT PACKET----------------------------");
        System.out.println("Sending Packet " + i + " of " +  totalPackets + " with sequence number " + currentSeq); 
        System.out.println("Packet data is: " + packetList.get(i).getData());
      }
    }
  }
  
  private static void rdt_send(String rdt_sendIP, int rdt_sendPort, 
                               Packet rdt_sendPacket) throws IOException, InterruptedException{
    // - extract sequence from packet
    String sequence = rdt_sendPacket.getSequence();
    int rdt_listenPort = Integer.parseInt(rdt_sendPacket.getpeerID());  //ackPort is in PeerID
    /*
    if (rdt_sendPort==Globals.ACK_PORT){
      rdt_listenPort = Globals.MSG_PORT;
    }else{
      rdt_listenPort = Globals.ACK_PORT;
    }
    */
    DatagramSocket ACKSocket = new DatagramSocket(rdt_listenPort);
    
    boolean finished = false;
    while (!finished){
      try {
        // Delay when in SLOWMODE
          if (Globals.SLOWMODE){
            System.out.println(" <<< SLOW MODE IN EFFECT: 4 SEC DELAY before sending next packet>>> ");
            Thread.sleep(4000);
          }
          
        //  SEND THE CURRENT PACKET
          DatagramSocket MSGSocket = new DatagramSocket();
          if (Globals.SHOWALL) System.out.print("Sent to udt, ");
          long startTime = System.currentTimeMillis();  // - Start Timer
          //System.out.println("startTime " + startTime % 10000);
          udt_send(MSGSocket, rdt_sendIP, rdt_sendPort, rdt_sendPacket); 
          MSGSocket.close();
        
        //  LISTEN FOR ACK UNTIL TIMEOUT
          if (Globals.SHOWALL) System.out.println(" timeout = "+ (long)timout);
          DatagramPacket DGACKpacket = new DatagramPacket(new byte[128], 128);
          ACKSocket.setSoTimeout( (int)timout );
          
          ACKSocket.receive(DGACKpacket);
          
          //  UPDATE EstimatedRTT, and Timeout   
          long stopTime = System.currentTimeMillis();  // - Stop Timer
          long sampleRTT = stopTime - startTime;    
          
          //System.out.println("stopTime  " + stopTime % 10000);
          estimatedRTT = (1-Globals.ALPHA)*estimatedRTT + (Globals.ALPHA*sampleRTT);
          devRTT = (long) ((1-Globals.BETA)*devRTT + Globals.BETA * abs(sampleRTT- estimatedRTT ));
          timout = estimatedRTT + 4*devRTT;
          //System.out.println("Packet received on ACKSocket.");
          if (Globals.SHOWALL)  System.out.println("SampleRTT: " + sampleRTT + " New timeout: " + (long)timout);
        // CHECK SEQUENCE NUMBER OF ACK
        // - if ACK of correct sequence # , finished
          Packet response = Packet.extractFromDatagram(DGACKpacket);
          if ( response.isACK(sequence)  ){
            
            finished=true;
            if (Globals.SHOWALL){System.out.println("ACK received with sequence number " + response.getSequence() );}
          } else {
            if (Globals.SHOWALL) System.out.println("Received bad Ack, sequence " + response.getSequence() + ", resending.");
          }
        } catch (SocketTimeoutException e) {
          timout *= 2;  // After a timeout, double the timer  USING A SMALLER NUMBER DURING TESTING
          if (Globals.SHOWALL) System.out.println("Timeout occurred, resending.");
        } //try {
    }
    ACKSocket.close();
  }
  
  //May not use this.  It was an initial attempt to do things like the FSM in the book.
  private static Packet rdt_rcv() throws UnsupportedEncodingException{
    Packet result = new Packet();
    return result;
  }

  //May not use this.  It was an initial attempt to do things like the FSM in the book.
  private static Packet make_pkt(char state, String data) throws UnsupportedEncodingException{
    Packet result = new Packet();
    return result;
  }

  private static void udt_send(DatagramSocket datagramSocket,
                               String udtIP, int destinationPort, 
                               Packet packetPar) 
                               throws IOException{
        DatagramPacket outgoingPacket = makePacket(packetPar.asString(), udtIP, destinationPort);
        datagramSocket.send(outgoingPacket);
    }
  
  public static DatagramPacket makePacket(String msg, String destination, int port) 
  throws UnknownHostException{
    InetAddress IPaddress =  InetAddress.getByName(destination);
    byte[] data = msg.getBytes();
    int length = msg.length();
    if (length>128) length = 128;
    DatagramPacket madePacket = new DatagramPacket(data,length);
    madePacket.setAddress(IPaddress);
    madePacket.setPort(port);
    return madePacket;
    }
  
  
  /*
  Response Code:Phrase  (One character phrases are used to conserve message space.)
  200: O.  Okay. The request was completed.  
  400: B.  Bad Request error.  Parsing failed to create a usable message.
  404: F.  File not found.  Peer does not hold the requested file.
  505: H. HTTP Version not supported. 
  */
  
  
  public static HTTP listen(int listenPort)
  throws UnknownHostException, SocketException, IOException, InterruptedException {
    int responsePort = 999999999;
    /*
    if (listenPort==Globals.ACK_PORT){
      responsePort = Globals.MSG_PORT;
    } else {
      responsePort = Globals.ACK_PORT;
    }
    */
    HTTP result = new HTTP();
    if (Globals.SHOWALL) System.out.println("####Inside RDT.listen#######");
    if (Globals.SHOWALL) System.out.println("listen port is: " + listenPort);
    if (Globals.SHOWALL) System.out.println("response port will be PeerID ");
    ArrayList<Packet> packetList = new ArrayList<>();
    //INITIAL SEQ'S ARE IMPOSSIBLE, TO ENSURE FIRST PACKET WON'T BE A FALSE REPEAT.
    //ALSO, THIS LETS US CHECK FOR REPEATED FIN PACKETS.
    String ACKseq = "9";                                          
    String lastSeq = "9";
    String ACKip = "";
    boolean goodPacket = false;
    boolean drop = false;
    boolean finished = false;
    long delay = 0;  //This is used for testing, calculated from the Globals to determine ACK responsiveness.
    while (!finished) {
        //System.out.println("==================================================");
        //RECEIVE NEXT PACKET
        DatagramSocket MSGSocket = new DatagramSocket(listenPort);
        DatagramPacket MSGpacket = new DatagramPacket(new byte[128], 128);
        MSGSocket.receive(MSGpacket);
        MSGSocket.close();
        Packet newPacket = Packet.extractFromDatagram(MSGpacket);
        if (Globals.SHOWALL) {
          System.out.println("------------------------------------------");
          System.out.println("Seq " + newPacket.getSequence() + " received...");
        }
        //CHECK FOR GOOD PACKET, ADD FAULTS
        goodPacket = true;
        if (newPacket.isSYN()) {
            if (Globals.SHOWALL) System.out.println("SYN packet received, resetting sequence.");
        } else if (newPacket.getSequence().equals(lastSeq)) {
            goodPacket = false;
            if (Globals.SHOWALL) System.out.println("Duplicate sequence received.");
        } else {
            if (Globals.SHOWALL) System.out.println("New sequence received.");
        }
        if (newPacket.isFIN()) {
            if (Globals.SHOWALL) System.out.println("FIN received.");
            if (packetList.isEmpty()) goodPacket = false;
        }

        if (Math.random() > Globals.ACKpercent / 100.0) {
            goodPacket = false;
            drop = true;
            if (Globals.SHOWALL) System.out.println("ACKpercent --> Simulating dropped packet ");
        }

        //IF GOODPACKET, ADD TO PACKETLIST
        if (goodPacket) {
          if (Globals.SHOWALL) System.out.println("Adding packet to packetList.");
          responsePort = Integer.parseInt(newPacket.getpeerID() );
          packetList.add(newPacket);
          //if (Globals.SHOWALL) System.out.println("getData: " + newPacket.getData() );
          lastSeq = newPacket.getSequence();
        } 
        //DELAY BY THE Ack Time +/- ACKdev
        Thread.sleep(delay);
        // Delay when in SLOWMODE
          if (Globals.SLOWMODE){
            System.out.println(" <<< SLOW MODE IN EFFECT: 4 SEC DELAY before sending ACK>>> ");
            Thread.sleep(4000);
          }
        
        ACKseq = newPacket.getSequence();
        //SEND THE ACK 
        ACKseq = newPacket.getSequence();
        ACKip = newPacket.getIPAddress();
        DatagramSocket ACKSocket = new DatagramSocket();
        Packet ackPacket = new Packet("XX", ACKip, ACKseq, "XX", "ACK");
        //InetAddress ackIPinet = InetAddress.getByName(newPacket.getIPAddress());
        //Need the IP address from the incoming packet
        //InetAddress ackIPinet = InetAddress.getByName("192.168.1.46");
        DatagramPacket ACKpacket = makePacket(ackPacket.asString(), ACKip, responsePort);

        if (drop) {
            if (Globals.SHOWALL) System.out.println("drop, no ACK.");
            drop = false;
        } else {
            ACKSocket.send(ACKpacket);
            if (Globals.SHOWALL) System.out.println(" and  ACK'd, delay: " + delay + "  ");
            lastSeq = newPacket.getSequence();
        }
        ACKSocket.close();

        //process THE MESSAGE IF THE PACKET IS THE LAST
        if (newPacket.isFIN() && !packetList.isEmpty()) {
            String message = "";
            //Toss the SYN off the front
            packetList.remove(0);
            while (packetList.size() > 1) { // 1 because the last packet is the FIN
                String data = packetList.remove(0).getData();
                //if (Globals.SHOWALL) System.out.println("Adding:  " + data);
                message += data;
            }
            //There's a FIN on the end, so toss that too.
            lastSeq = "9"; //set an impossible sequence so the receiver will know it's done
            packetList.remove(0);
            //now message is the complete HTTP message as a string.
            result = new HTTP(message);
            finished = true;
                      /*
                      HTTP http = new HTTP(message);
                      System.out.println("Complete Message received.");
                      if ( http.getCode().equals("I") ) {System.out.println("It's an inform/update, call that method here.");}
                      System.out.println("------------------------------------------------");
                      if (http.isRequest() ) { System.out.println("Received an HTTP request.");}
                      if (http.isResponse()) { System.out.println("Received an HTTP response.");}
                      System.out.println( "Code:      " + http.getCode()       );
                      System.out.println( "Phrase:    " + http.getPhrase()     );
                      System.out.println( "IPaddress: " + http.getIPaddress()  );
                      System.out.println( "Version:   " + http.getVersion()    );
                      System.out.println( "Payload:   " + http.getPayload()    );
                      //System.out.println(message);
                      System.out.println("------------------------------------------------");
                      */
        }
    }
    return result;
  }
  
  
  

  public static void main(String[] args) {
    System.out.println("RDT_Sender class compiled and ran successfully.");
  }

}