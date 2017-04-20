/**
 * 
 * HTTP request message format
 * 
 * NEW COMBINED HTTP FORMAT FOR REQUEST AND RESPONSE:

Code Sp	Phrase Sp IP address Sp Version nl Payload 

Request Codes/Phrase:
L: L Log in attempt.   
I: I  Inform and update
Q: Q query for content  
D: D directory query     <do we need this?  can't we just use inform/update?>
// <why did this turn blue? maybe a good way to have comments stand out! >
E: E Exit
Response Code:Phrase  (One character phrases are used to conserve message space.)
200: O.  Okay. The request was completed.  
400: B.  Bad Request error.  Parsing failed to create a usable message.
404: F.  File not found.  Peer does not hold the requested file.
505: H. HTTP Version not supported. 
* 
* 
 * 
 */
package p2p_fileshare;
/**
 *@author Jeff and Adrian
 */

public class HTTP {
  String body;
  
  private static String requestCodes = "L I Q D";
  private static String responseCodes = "200 400 404 505";
     
  public HTTP(){
    body = "";
    }
  
  public HTTP(String code, String phrase, String IPaddress, String version, String payload){
    //Code Sp Phrase Sp IP address Sp Version nl Payload 
    this.body = code +" "+ phrase +" "+  IPaddress +" "+  version +"\n"+  payload;  
    }

    @Override
    public String toString(){
       return this.body;
    }

  public String getPayload(){
    //Code Sp Phrase Sp IPaddress Sp Version nl Payload 
    String result = "";
    int i = 0;
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Code and the space
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Phrase and the space
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the IPaddress and the space
    while ( this.body.charAt(i) != '\n') {i++;} i++;  //move the index past the Version and the \n
    result = this.body.substring(i);                   //takes the remaining characters.
    return result; 
  }  

  public String getVersion(){
    //Code Sp Phrase Sp IPaddress Sp Version nl Payload 
    String result = "";
    int i = 0;
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Code and the space
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Phrase and the space
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the IPaddress and the space
    do{                                               //take the next characters until \n
      result = result + this.body.charAt(i);
      i++;
    }
    while ( this.body.charAt(i) != '\n' );
    return result; 
  }

  public String getIPaddress(){
    //Code Sp Phrase Sp IPaddress Sp Version nl Payload 
    String result = "";
    int i = 0;
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Code and the space
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Phrase and the space
    do{                                               //take the next characters until space
      result = result + this.body.charAt(i);
      i++;
    }
    while ( this.body.charAt(i) != ' ' );
    return result; 
  }

  public String getPhrase(){
    //Code Sp Phrase Sp IPaddress Sp Version nl Payload 
    String result = "";
    int i = 0;
    while ( this.body.charAt(i) != ' ' ) {i++;} i++;  //move the index past the Code and the space
    do{                                               //take the next characters until space
      result = result + this.body.charAt(i);
      i++;
    }
    while ( this.body.charAt(i) != ' ' );
    return result; 
  }

  public String getCode(){
    //Code Sp Phrase Sp IPaddress Sp Version nl Payload 
    String result = "";
    int i = 0;
    do{                                               //take the next characters until space
      result = result + this.body.charAt(i);
      i++;
    }
    while ( this.body.charAt(i) != ' ' );
    return result; 
  }

  public boolean isRequest(){
    return requestCodes.contains(this.getCode());
    }
  
  public boolean isResponse(){
    return responseCodes.contains(this.getCode());
    }


  public static void main(String[] args){
    System.out.println("testing HTTP methods");
    
    //Code Sp Phrase Sp IPaddress Sp Version nl Payload 
    HTTP test = new HTTP("200","O","192.168.1.118","1","This is a test payload");
    System.out.println( test.getCode()       );
    System.out.println( test.getPhrase()     );
    System.out.println( test.getIPaddress()  );
    System.out.println( test.getVersion()    );
    System.out.println( test.getPayload()    );
    System.out.println( "toString method:  " + test.toString()      );
    System.out.println( "isRequest: " + test.isRequest() );
    System.out.println( "isResponse: " + test.isResponse() );
    
    
    
    
  }
  
  
}
