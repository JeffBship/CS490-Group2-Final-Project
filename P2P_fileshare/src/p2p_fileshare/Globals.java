/*
 * This is a test to see if pushing to github works  
 * 
 * Commit damnit!!
 * 
 * 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_fileshare;

import java.io.File;


public class Globals {
    
  //These are for testing and determine the amount of delay and variability
  //in response time, as well as chance of a dropped packet
  public static final double ACKpercent = 100.0;   //probability of correct ACK (incorrect is simulated dropped packet)./
  public static final long   ACKtime = 10;         //base time wait before ack (msec)
  public static final long   ACKdev = 5;          //amount of variability in time before ack (msec)

  //public static       File FOLDER = new File("c:\\");
  
  public static final boolean SLOWMODE = false;   //this adds a 4000ms delay send and ack
  public static final boolean SHOWALL = false;    //set this to true/false to show packet data as sent/received
  
  public static final int S_PORT = 2000;      //server listens on this port
  public static final int P_PORT = 2001;      //peers listen on this port
  public static final int PING_PORT = 2003;
  public static final int BASE_PORT = 2004;   //threads and menus reference off this  000 server threads 100 peer threads 200 peer menu
  
  //These are the variables to timeout calculations
  public static final double INIT_EST_RTT = 100.0;
  public static final double INIT_DEV_RTT = INIT_EST_RTT / 4.0;
  public static       double INIT_TIMEOUT = 4.0 * INIT_EST_RTT;
  
  public static final double ALPHA = 0.125;
  public static final double BETA = 0.25;
  
  public static final String JEFF_PC_IP = "192.168.1.46";
  //public static final String JEFF_LAPTOP_IP = "192.168.1.118";
  public static final String JEFF_LAPTOP_IP = "192.168.2.49";
  public static final String ADRIAN_LAPTOP_IP = "192.168.1.96";
  public static final String ADRIAN_LAB = "192.168.2.48";
  public static final String JEFF_LAB = "192.168.2.49";
  
  
  //public static volatile boolean pingResult = false;
  
  
  
  // This is a big long string for test purposes.
  // From http://gaia.cs.umass.edu/wiresharklabs/alice.txt
  // Which does not give any copyright data, but probably need to give credit
  // to Charles Dodgson (aka Lewis Carrol) 
  public static final String ALICE = 
      "START Alice was beginning to get very tired of sitting by her sister \n" +
      "on the bank, and of having nothing to do:  once or twice she had \n" +
      "peeped into the book her sister was reading, but it had no \n" +
      "pictures or conversations in it, `and what is the use of a book,' \n" +
      "thought Alice `without pictures or conversation?' \n" +
      "\n" +
      "     So she was considering in her own mind (as well as she could, \n" +
      "for the hot day made her feel very sleepy and stupid), whether \n" +
      "the pleasure of making a daisy-chain would be worth the trouble \n" +
      "of getting up and picking the daisies, when suddenly a White \n" +
      "Rabbit with pink eyes ran close by her.END\n";
      /*+
      "\n" +
      "  There was nothing so VERY remarkable in that; nor did Alice\n" +
      "think it so VERY much out of the way to hear the Rabbit say to\n" +
      "itself, `Oh dear!  Oh dear!  I shall be late!'  (when she thought\n" +
      "it over afterwards, it occurred to her that she ought to have\n" +
      "wondered at this, but at the time it all seemed quite natural);\n" +
      "but when the Rabbit actually TOOK A WATCH OUT OF ITS WAISTCOAT-\n" +
      "POCKET, and looked at it, and then hurried on, Alice started to\n" +
      "her feet, for it flashed across her mind that she had never\n" +
      "before seen a rabbit with either a waistcoat-pocket, or a watch to\n" +
      "take out of it, and burning with curiosity, she ran across the\n" +
      "field after it, and fortunately was just in time to see it pop\n" +
      "down a large rabbit-hole under the hedge.\n" +
      "\n" +
      "  In another moment down went Alice after it, never once\n" +
      "considering how in the world she was to get out again.\n" +
      "\n" +
      "  The rabbit-hole went straight on like a tunnel for some way,\n" +
      "and then dipped suddenly down, so suddenly that Alice had not a\n" +
      "moment to think about stopping herself before she found herself\n" +
      "falling down a very deep well.\n" +
      "\n" +
      "  Either the well was very deep, or she fell very slowly, for she\n" +
      "had plenty of time as she went down to look about her and to\n" +
      "wonder what was going to happen next.  First, she tried to look\n" +
      "down and make out what she was coming to, but it was too dark to\n" +
      "see anything; then she looked at the sides of the well, and\n" +
      "noticed that they were filled with cupboards and book-shelves;\n" +
      "here and there she saw maps and pictures hung upon pegs.  She\n" +
      "took down a jar from one of the shelves as she passed; it was\n" +
      "labelled `ORANGE MARMALADE', but to her great disappointment it\n" +
      "was empty:  she did not like to drop the jar for fear of killing\n" +
      "somebody, so managed to put it into one of the ENDS WITH A ZERO o\n"
      
      +
      
      "X                                                               \n"+
      " X                                                              \n"+
      "  X                                                             \n"+
      "   X                                                            \n"+
      "    X                                                           \n"+
      "     X                                                          \n"+
      "      X                                                         \n"+
      "       X                                                        \n"+
      "        X                                                       \n"+
      "         X                                                      \n"+
      "          X                                                     \n"+
      "           X                                                    \n"+
      "            X                                                   \n"+
      "             X                                                  \n"+
      "            X                                                   \n"+
      "           X                                                    \n"+
      "          X                                                     \n"+
      "         X                                                      \n"+
      "        X                                                       \n"+
      "       X                                                        \n"+
      "      X                                                         \n"+
      "     X                                                          \n"+
      "    X                                                           \n"+
      "   X                                                            \n"+
      "  X                                                             \n"+
      " X                                                              \n"+
      "X                                                               \n"
      ;
*/

      
  
}
