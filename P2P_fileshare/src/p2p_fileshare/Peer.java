package p2p_fileshare;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dad
 */
public class Peer {
  
  public static void main(String[] args) {
    
    message outgoing = new message();
    
    outgoing.set("this is a big long string of stuff for the outgoing message.");
    
        
    outgoing.transmit();
  }
  
}
