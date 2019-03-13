package coffeetingame;

import java.util.*;

import utils.TextIO;

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
     * @param args
   * @effects 
   *    initialize a coffee tin
   *    {@link TextIO#putf(String, Object...)}: print the tin content
   *    {@link @tinGame(char[])}: perform the coffee tin game on tin
   *    {@link TextIO#putf(String, Object...)}: print the tin content again
   *    if last bean is correct
   *      {@link TextIO#putf(String, Object...)}: print its color 
   *    else
   *      {@link TextIO#putf(String, Object...)}: print an error message
   */
  public static void main(String[] args) {
    // initialize some beans
    char[] beans= {BLUE,BLUE,GREEN,GREEN,GREEN,GREEN,BLUE,GREEN};
    //count the number of greens
    int greens =0;
   
    for(char b:beans) 
    {
      if (b==GREEN)
            greens++;
    }
    // the expected last bean
    final char last=(greens%2==1)?GREEN:BLUE;
    // print the content of tin before the game
    // p0= green parity/\ 
    // (p0=1 -> last = GREEN) /\ (p0=0 ->last= BLUE)
    
   
    System.out.printf("tin before :%s %n",Arrays.toString(beans));
    
    //perform the game
    char lastBean=tinGame(beans);
    //lastBean = last\/ lastBean!last
    
    
   //print tin after the game
System.out.printf("tin after :%s %n",Arrays.toString(beans));
  // check if last bean as expected and print 

    
  if (lastBean==last)
    {
        System.out.println("last bean: "+lastBean);
   }
   else {
      System.out.printf("Oops, wong last bean: %c (epectec : %c).%n",lastBean,last);
    }
  }

  /**
   * Performs the coffee tin game to determine the color of the last bean
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
  public static char tinGame(char[] tin) {  
      int count = tin.length;
      
      while (count > 1) {
          updateTin(tin, takeTwo(tin));
      }
      
      char LastBean = 0;
      
      //Loop invariant
      for (char x : tin) {
          // Check whether bean is avaiable or not
          if (x != REMOVED) {
              LastBean = x;
          }
      }
      
      return LastBean;
    
  }
  
  public static int[] takeTwo(char[] tin) {
      int[] randIndices = new int[2];
      int bean1, bean2;
      bean1 = randInt(tin.length);
      bean2 = randInt(tin.length);


      // Check whether random beans are avaiable or not
      while (tin[bean1] == REMOVED || tin[bean2] == REMOVED || tin[bean1] == tin[bean2]) {
        // Take 2 random beans from tin
        bean1 = randInt(tin.length);
        bean2 = randInt(tin.length);
      } 
        // Return indices
        randIndices[0] = bean1;
        randIndices[1] = bean2;
      
      return randIndices;
  }
  
  public static void updateTin(char[] tin, int[] randIndices) {
      char bi1, bi2;
      
      // Determine 2 random beans
      bi1 = tin[randIndices[0]];
      bi2 = tin[randIndices[1]];
      
      tin[randIndices[0]] = REMOVED;
      tin[randIndices[1]] = REMOVED;
      
      // Color check and update
      if (bi1 == bi2) {
        tin[randIndices[0]] = BLUE;
      } else {
          if (bi1 == BLUE) {
            tin[randIndices[1]] = GREEN;
          } else {
            tin[randIndices[0]] = GREEN;
          }
      }
  }
  
  
  public static int randInt(int n) {
      // Return an random integer number from 0 (inclusive) to n (exclusive)
      Random r = new Random();
      return r.ints(0, n).limit(1).findFirst().getAsInt();
  }
}