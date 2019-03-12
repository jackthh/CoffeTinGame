package coffeetingame;

import java.util.*;

/**
 * @overview A program that performs the coffee tin game.
 * 
 * @author dmle
 */
public class CoffeeTinGame {
  /** constant value for the green bean*/
  private static final char GREEN = 'G';
  /** constant value for the blue bean*/
  private static final char BLUE = 'B';
  /** constant for removed beans */
  private static final char REMOVED = '-';

  /**
   * the main procedure
   * @effects 
   *    initialise a coffee tin
   *    {@link TextIO#putf(String, Object...)}: print the tin content
   *    {@link @tinGame(char[])}: perform the coffee tin game on tin
   *    {@link TextIO#putf(String, Object...)}: print the tin content again
   *    if last bean is correct
   *      {@link TextIO#putf(String, Object...)}: print its colour 
   *    else
   *      {@link TextIO#putf(String, Object...)}: print an error message
   */
  public static void main(String[] args) {
    // initialise some beans
    char[] beans = { GREEN, BLUE, BLUE, GREEN, GREEN };

    // count number of greens
    int greens = 0;
    for (char b : beans) { 
      if (b == GREEN)
        greens++;
    }
    final char last = (greens % 2 == 1) ? GREEN : BLUE;
    // beans.length > 0 /\ (greens % 2 = 1 -> last=GREEN) /\ 
    //    (greens % 2 = 0 -> last=BLUE)
    
    // print the content of tin before the game
    TextIO.putf("tin before: %s %n", Arrays.toString(beans));

    // perform the game
    char lastBean = tinGame(beans);
    // lastBean in beans /\ lastBean = last
    
    // print the content of tin and last bean
    TextIO.putf("tin after: %s %n", Arrays.toString(beans));
    
    if (lastBean == last) { 
      TextIO.putln("last bean: " + lastBean);      
    } else {
      TextIO.putf("Oops, wrong last bean: %c (expected: %c)%n",lastBean,last);
    }
  }

  /**
   * Performs the coffee tin game to determine the colour of the last bean
   * 
   * @requires tin is not null /\ tin.length > 0
   * @modifies tin
   * @effects <pre>
   *          repeatedly remove two beans from tin and update tin as follows
   *          if are same colour
   *             throw both away, put one blue bean back
   *          else
   *             put green bean back
   *          if the number of green beans is even
   *            return blue
   *          else
   *            return green
   *            
   *      i.e.  (p0 = 0 /\ one blue left) \/ 
   *            (p0 = 1 /\ one green left), where p0 = initial green parity
   *            </pre>
   */
  private static char tinGame(char[] tin) {    
    int bi1, bi2;
    int b1, b2;
    int count = tin.length;
    bi1 = 0;
    //
    // Loop invariant: 
    //  p = p0 /\ count >= 1
    // Loop variant:   
    //  P(count) = false  if count >= 2
    //             true   if count = 1      
    //
    // n=no of greens /\ m=no of blues /\ n+m=count /\ 
    // (M1.2a: for all j=bi1 to tin.length-1. tin[j]!=REMOVED) /\ 
    // (M1.2b: 1<=count<=tin.length) /\ 
    // (M1.2c: bi1+count=tin.length /\ p=p0) 
    while (count >= 2) {
      // (M2.2: M1.2a /\ (M2.2a: 2<=count<=tin.length) /\ M1.2c /\ 
      //  n=n /\ m=m /\ count=n+m)      
      // remove b1, b2 from tin
      b1 = tin[bi1];          // M2.2
      bi2 = bi1+1;            // M2.2 /\ bi2=bi1+1
      b2 = tin[bi2];          // M2.2 /\ bi2=bi1+1
      tin[bi1] = REMOVED; 
      tin[bi2] = REMOVED;     
      if (b1 == BLUE && b2 == BLUE) { 
         // put B in bin
        tin[bi2] = BLUE;      // n=n /\ m=m-1 /\ count=n+m+1 /\ p=p0 /\ 
        // bi2=bi1+1 /\ for all j=bi2 to tin.length-1. tin[j]!=REMOVED
      } else if (b1 == GREEN && b2 == GREEN) { 
        // put B in bin
        tin[bi2] = BLUE;      // n=n-2 /\ m=m+1 /\ count=n+m+1 /\ p=p0 /\
        // bi2=bi1+1 /\ for all j=bi2 to tin.length-1. tin[j]!=REMOVED
      } else { // BG, GB    
        // put G in bin
        tin[bi2] = GREEN;     // n=n /\ m=m-1 /\ count=n+m+1 /\ p=p0 /\
        // bi2=bi1+1 /\ for all j=bi2 to tin.length-1. tin[j]!=REMOVED
      }
      // count=n+m+1 /\ p=p0 /\ bi2=bi1+1 /\ 
      // for all j=bi2 to tin.length-1. tin[j]!=REMOVED
      count = count-1;
      bi1 = bi1+1;          
      // M1.2a /\ M1.2b /\ M1.2c
    }
    
    // count=1 /\ M1.2c /\ tin[bi1]!=REMOVED
    return tin[bi1];
  }
}