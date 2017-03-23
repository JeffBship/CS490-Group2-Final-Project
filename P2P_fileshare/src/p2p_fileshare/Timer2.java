package p2p_fileshare;
    

import java.net.*;
import java.io.*;

public class Timer2 extends Thread {
    int max;
    
    public Timer2(int maxPar) {
        this.max = maxPar*1000;
    }
    
    public void run() {
      int startTime = (int) System.currentTimeMillis();
      int remainingTime, currentTime;
      char letter = 'A';
      do{
        currentTime = (int) System.currentTimeMillis();
        remainingTime = Math.round(  (startTime + this.max - currentTime)/1000  );
        System.out.println("Char is: " + letter);
        letter += 1;
        try { Thread.sleep(1700);
            } catch(InterruptedException ex) {
            }
      } while ( remainingTime>0);
    }
}