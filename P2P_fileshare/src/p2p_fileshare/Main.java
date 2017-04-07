/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;
import java.util.*;
import java.io.*;

/**
 *
 * @author Surface Book
 */
public class Main {
    public static void main(String[] args){
      ArrayList<Song> song = new ArrayList<>();
      Peer p1 = new Peer();
      song = p1.getDirectory();
      p1.printDirectory(song);
     
    
    }
}
