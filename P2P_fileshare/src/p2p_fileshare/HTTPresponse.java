/**
 * 
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

public class HTTPresponse {
  String statusCode = "";
  String statusPhrase = "";
  String payload = "";
  
  String[] allowedCodes = { "200" , "400" , "404" , "505" };
  
  
  
  
}
