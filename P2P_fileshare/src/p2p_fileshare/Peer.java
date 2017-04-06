/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.*;
import java.util.*;
/**
 *
 * @author Jeff Blankenship and Adrian Ward-Manthey
 * This class will allow contain functionality to allow the user to inform and update
 * It will also allow the user to 
 */
public class Peer {
    
    public void printDirectory(ArrayList<Song> SongL){
        for(Song s : SongL )
           System.out.println(s.getAll());
    }
    
    //modify to allow user to designate directory
    public static void getDirectory(String directory){
      //File folder = new File("C:\\Users\\Surface Book\\Desktop\\CCSU\\Spring 2017\\CS 490 Networking\\CS490-Group2-Final-Project\\P2P_fileshare\\src\\p2p_fileshare\\files");
      File folder = new File(directory);
      File[] listOfFiles = folder.listFiles();
      ArrayList<Song> SongL = new ArrayList<>();
      int i = 0;
      for(File file : listOfFiles)
       if(file.isFile() && file.getName().endsWith(".mp3"))
            SongL.add(new Song(++i, file.getName(), "Size", "IP" ));
    }
    
}