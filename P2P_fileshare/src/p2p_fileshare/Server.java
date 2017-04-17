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
  //Main method just for testing purposes 
  //Server will need to get IP address of local machine so that it can connect with peers
    Hashtable<String, Song> dTab;
    int sNum = 0;
    public Server(){
       dTab = new Hashtable<>();
      
    }
    
    public Hashtable<String, Song> getTable(){
        return dTab;
    }

  //This function works properly
  //Will need to modify it so that when server receives a string it can parse it 
  //then place data into hashtable 
  /*
  public void processSongArray(ArrayList<Song> songs,Hashtable<String, Song> dTab){
      //This class will take an array from a peer and add it into the hashtable
      for(Song s : songs)
        dTab.put(s.getName() + " " + s.getIP(),s);
      Song test = new Song(100, "01-hellsbells.mp3" , 0L, "255.111.1.50");
      dTab.put(test.getName() + " " + test.getIP(),test);
      
      
  }
  */
 /*Modified version of processSongArray...once the string songList from peer 
   is transmitted, this method will parse through the string, build the song object
   and place it in the hashtable.
 */
  public void processSongArray(String songList, Hashtable<String, Song> dTab){
    //parse into char works!!!!
    char[] list = songList.toCharArray();
    System.out.println("CHAR ARRAY");
    for(char f: list)
        System.out.print(f);
    
    
    //use parse to read up to newline character
    ArrayList<Character> parse = new ArrayList<>();
    //use temp to read up to delimeter
    //ArrayList<Character> temp = new ArrayList<>();
    String temp;
    String name;
    String size;
    String IP;
    char c;
    for(int i = 0; i<list.length; i++){
      c = list[i];
      if(!(c=='\n')){ 
        parse.add(c);
      System.out.print(c + " ");
      }
      else {
        System.out.println("HERE IS PARSE");
        System.out.println(parse.toString());
        //This builds a string out of parse
        StringBuilder build = new StringBuilder(parse.size());
        for(Character ch: parse)
            build.append(ch);
        temp = build.toString();
        String[] splits = temp.split(" ");
        System.out.println("THIS IS THE SPLITS");
        System.out.print(Arrays.toString(splits));
        name = splits[0];
        System.out.println(name);
        size = splits[1];
        System.out.println(size);
        IP = splits[2];
        System.out.println(IP);
        dTab.put(name + " " + IP,new Song(++sNum, name, size, IP ));
        parse.clear();
      }
      
      // LET ME PUSH
      
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
  //Argument will now just be a string- RETURN ARRAYLIST<SONG>
  public void processQuery(Hashtable<String, Song> sTable) {
    String IP = "";
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
          if(key.getName().contains(q))
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
  
  /*This method will work in conjunction with the TCP.java class.
    User will type in IP address and TCP.java will handle the connection
  */
  public void processRequest(){
    
  }
  
  
}