package p2p_fileshare;

import java.io.IOException;

public class Peer {
  
  public static void main(String[] args) throws IOException {
    
    String destinationIP = "192.168.1.46";  //Jeff desktop localIP
    //String destinationIP = "192.168.1.118";  //Jeff laptop localIP
    int destinationPort = 55000;
    
    Message outgoing = new Message();
    
    outgoing.setData("The objective of this project is to develop a simple peer to "
        + "peer file sharing system that uses a centralized directory server. This "
        + "project consists of and is delivered in three stages: a survey paper "
        + "discussing different existing P2P architectures for file sharing; a "
        + "detailed design of the system to be implemented including its protocols;"
        + " and the final demonstration of the working system. The overall architecture "
        + "of the system will be similar to that of the original Napster, as shown below..");
    System.out.println(" hello there");
    outgoing.transmit(destinationIP, destinationPort);
    
        
  }
  
}
