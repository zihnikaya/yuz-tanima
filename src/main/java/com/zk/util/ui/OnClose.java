package com.zk.util.ui;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/** Enumeration of various close operations.
 
 <P>Use of such an enumeration provides a clearer and safer alternative to using 
 the members of {@link javax.swing.WindowConstants}.
 
  <P>It is important to note that 
  {@link javax.swing.JDialog#setDefaultCloseOperation(int)} allows for 
  only 3 of these operations. It does not allow for {@link #EXIT}. */
public enum OnClose {
  DISPOSE(DISPOSE_ON_CLOSE), 
  DO_NOTHING(DO_NOTHING_ON_CLOSE), 
  HIDE(HIDE_ON_CLOSE),
  EXIT(EXIT_ON_CLOSE);
  
  /**   Return the integer value of the corresponding constant in {@link javax.swing.WindowConstants}.  */
  public int getIntValue(){ return fOption; }
  
  /** Only private constructors should be used with enumerations. */ 
  private OnClose(int aUnderlyingOption){
    fOption = aUnderlyingOption;
  }
  private int fOption;
}
