/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 *
 * @author Jeff Blankenship and Adrian Ward-Manthey
 * This class will allow contain functionality to allow the user to inform and update
 * It will also allow the user to 
 * 
 * USE IP.isReachable() to check and see if we can reach the IP before establishing a TCP connection
 */
public class Peer {
    
    File folder = null;
  
    //modify to allow user to designate directory
    //NEEDS TO BE TESTED
    /*
    public ArrayList<Song> getDirectory() throws UnknownHostException{
      //perhaps this functionality should be handled in another method????
      int i = 0;
      File f = null;
      /*
      String directory = "";
      while(i==0){
        Scanner kbd = new Scanner(System.in);
        System.out.print("Please enter desired file path: ");
        directory = kbd.nextLine();
        f = new File(directory);
        if (f.isDirectory())
            i=1;     
        else{
            System.out.println("Please enter a valid directory");          
        }
      }
     
      File folder = new File("C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files");
      //folder = new File(directory);
      //BUILD A STRING FOR TRANSMIT
      File[] listOfFiles = folder.listFiles();
      ArrayList<Song> SongL = new ArrayList<>();
      int j=0;
      for(File file : listOfFiles)
       if(file.isFile() && file.getName().endsWith(".mp3"))
           //we will use the lowercase name of the song with no white space as the key
            SongL.add(new Song(++j, file.getName().toLowerCase().replace(" ", ""), file.length(), InetAddress.getLocalHost().getHostAddress()));
      return SongL;
    }
    */
    
    //This method will return a string for transmit...server will handle string parsing
    //NOTE: change so that user can choose directory
    public String getDirectory() throws UnknownHostException{
      String songList= "";
      File folder = new File("C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files");
      File[] listOfFiles = folder.listFiles();
      int j=0;
      for(File file : listOfFiles)
       if(file.isFile() && file.getName().endsWith(".mp3"))
           songList+= file.getName() + "\t" + file.length() + "\t" + InetAddress.getLocalHost().getHostAddress() + "\n"; 
      return songList;
    
    }
    //NEEDS TO BE TESTED
    //Will return string for transmission
    //Move this to String processing to getDirectory Method
    /*
    public void printDirectory(ArrayList<Song> SongL){
        String list = "";
        if(SongL.isEmpty())
          System.out.println("No files ending with .mp3 were found in designated directory");
        else {
         for(Song s : SongL ){
           System.out.println(s.getAll());
           list+=s.getAll() + "\n";
         }
        }
        System.out.println("HERES ARRAYLIST FOR TRANSMIT METHOD");
        System.out.print(list);
        
    }
*/
    
    
}