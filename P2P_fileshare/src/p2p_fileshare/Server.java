package p2p_fileshare;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeff Blankenship && Adrian Ward-Manthey
 *  
 */
/*
   Java HashTables uses separate chaining to deal with collisions
   NEED TO IMPLEMENT MULTITHREADING????
   Use InetAddress.getByName(String host) to convert 
*/
import java.util.*;
public class Server {
  //Server will need to get IP address of local machine so that it can connect with peers
    Hashtable<String, Song> dTab;
    int sNum = 0;
    
    public Server(){
       dTab = new Hashtable<>();
      
    }
    
    public Hashtable<String, Song> getTable(){
        return dTab;
    }
 /*Modified version of processSongArray...once the string songList from peer 
   is transmitted, this method will parse through the string, build the song object
   and place it in the hashtable.
    NEEDS TO HANDLE DELETING ENTRIES WHEN PEER LEAVES!!!!
 */
  public void processSongString(String songList, Hashtable<String, Song> dTab){
    //parse into char works!!!!
    char[] list = songList.toCharArray();
    /*
    System.out.println("CHAR ARRAY");
    for(char f: list)
        System.out.print(f);
    */
    //use parse to read up to newline character
    ArrayList<Character> parse = new ArrayList<>();
   
    String temp;
    String name;
    String size;
    String IP;
    char c;
    for(int i = 0; i<list.length; i++){
      c = list[i];
      if(!(c=='\n')) 
        parse.add(c);
      else {
        //This builds a string out of parse
        StringBuilder build = new StringBuilder(parse.size());
        for(Character ch: parse)
            build.append(ch);
        temp = build.toString();
        //uses \t as delimeter for string parsing
        String[] splits = temp.split("\t");
        name = splits[0];
        size = splits[1];
        IP = splits[2];
        dTab.put(name + " " + IP,new Song(++sNum, name, size, IP ));
        parse.clear();
      }
    } 
  }
  
  
  //This Function works properly
  public void printServerDirectory(Hashtable<String, Song> sTable){
      Enumeration songNames = sTable.keys();
      String key;
      while(songNames.hasMoreElements()){
          key = (String) songNames.nextElement();
          System.out.println(sTable.get(key).getAll());
      }
  }
  
  //This function will take user input as key, hash it, then will return list of songs along with IP addresses 
  //might need 2 functions....one to process query, another to use requested IP address by user
  //Perhaps this should be in the Peer class?????
  //THIS FUNCTION WILL TEMPORARILY JUST PRINT OUT THE RESULTING QUERY FOR TESTING PURPOSE
  public void processQuery(Hashtable<String, Song> sTable) {
    //Scanner not closed to allow for Peer to continue using scanner
    // query.close();
    String IP;
    System.out.print("Please enter file name that you would like to query: ");
    String q;
    Scanner query = new Scanner(System.in);
    q = query.nextLine();
    q = q.toLowerCase().replace(" ", "");
    System.out.println("Q is " + q);
   
    //DONT CHANGE THIS PART
    Enumeration songNames = sTable.elements();
    Song key;
    while(songNames.hasMoreElements()){
          key = (Song) songNames.nextElement();
          if(key.getName().toLowerCase().replace(" ","").contains(q))
            System.out.println(key.getAll());
      }
    
    /*
      //if((sTable.get(q).getName()).equals(""))
      if(!sTable.containsKey(q)) 
           System.out.println("Query empty");        
      else
        System.out.println(sTable.get(q).getAll());
    */
  }
  
  //These can be written AFTER testing RDT code between client and server
  
  /*This method will work in conjunction with the TCP.java class.
    User will type in IP address and TCP.java will handle the connection
  */
  public void processRequest(){
    
  }
  
  
  //This method will delete all entries associated with a User from the hashtable
  public void processPeerExit(){
  
  }
  
  
}