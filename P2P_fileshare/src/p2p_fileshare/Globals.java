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


public class Globals {
  
  public static final int MSG_PORT = 55000;
  public static final int ACK_PORT = 55001;
  
  public static final double INIT_EST_RTT = 100.0;
  public static final double INIT_DEV_RTT = INIT_EST_RTT / 4.0;
  public static       double INIT_TIMEOUT = 4.0 * INIT_EST_RTT;
  
  public static final double ALPHA = 0.125;
  public static final double BETA = 0.25;
  

  public static String LocalPeerID = "1";
  
  public static final String JEFF_PC_IP = "192.168.1.46";
  public static final String JEFF_LAPTOP_IP = "192.168.1.118";
  public static final String ADRIAN_LAPTOP_IP = "192.168.1.96";
  
  
  
  // This is a big long string for test purposes.
  // From http://gaia.cs.umass.edu/wiresharklabs/alice.txt
  // Which does not give any copyright data, but probably need to give credit
  // to Charles Dodgson (aka Lewis Carrol) 
  public static final String ALICE = 
      "Alice\n was beginning to get very tired of sitting by her sister " +
      "on the bank, and of having nothing to do:  once or twice she had " +
      "peeped into the book her sister was reading, but it had no " +
      "pictures or conversations in it, `and what is the use of a book,' " +
      "thought Alice `without pictures or conversation?' " +
      "\n" +
      "     So she was considering in her own mind (as well as she could, " +
      "for the hot day made her feel very sleepy and stupid), whether " +
      "the pleasure of making a daisy-chain would be worth the trouble " +
      "of getting up and picking the daisies, when suddenly a White " +
      "Rabbit with pink eyes ran close by her." +
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
      "somebody, so managed to put it into one of the cupboards as she\n" +
      "fell past it. ENDS WITH DIGITS TO ZERO 1234567890";
      
  
}
