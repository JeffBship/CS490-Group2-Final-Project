/**
 * Timer counts down a number of seconds.
 * 
 * This is mostly just practice on multithreading. * 
 * 
 */



package p2p_fileshare;

public class Timer extends Thread {
    int max;
    
    public Timer(int maxPar) {
        this.max = maxPar*1000;
    }
    
    public void run() {
      int startTime = (int) System.currentTimeMillis();
      int remainingTime, currentTime;
      do{
        currentTime = (int) System.currentTimeMillis();
        remainingTime = Math.round(  (startTime + this.max - currentTime)/ 1000 );
        System.out.println("Time remaining: "+ remainingTime );
        try { Thread.sleep(1000);
            } catch(InterruptedException ex) {
            }
      } while ( remainingTime>0);
    }
}