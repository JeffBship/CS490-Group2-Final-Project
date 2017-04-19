/**
 * 
 * HTTP request message format

RequestCode	Sp	PeerID	Sp 	IP address	Sp	Version	nl
Payload

Request Codes:
L:  Log in attempt.   I:  Inform and update
Q: query for content  D: directory query
E: Exit
 
HTTP response message format:
Status code	sp	Status Phrase	nl
Payload
 
Status Code:Phrase  (One character phrases are used to conserve message space.)
200: O.  Okay. The request was completed.  
400: B.  Bad Request error.  Parsing failed to create a usable message.
404: F.  File not found.  Peer does not hold the requested file.
505: H. HTTP Version not supported. 
 * 
 */
package p2p_fileshare;

import java.util.Scanner;


/**
 *
 * @author Jeff and Adrian
 */
public class HTTPrequest {
     String requestCode;
     String IPAddress;
     String filename;
     String Version;
     String data;
     
     
   public HTTPrequest(){
      requestCode = "";
      IPAddress = "";
      filename = "";
      Version = "";
      data ="";
   }
   
   public HTTPrequest(String Code, String IP, String fName, String V, String d){
      requestCode = Code;
      IPAddress = IP;
      filename = fName;
      Version = V;
      data = d;
   }
   
   public String makeHTTPRequest(){
     return requestCode + " " + IPAddress + " " + Version + "\n" + filename + "\n";
   }
   
   
  //can use method from Server.java to handle string parsing on Server side
  //use RDT.transmit for actual transmission
  public void informAndUpdate(){
     
  
  }
  
  
}
