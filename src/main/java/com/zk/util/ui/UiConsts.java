package com.zk.util.ui;


/**
 Collected constants related to the user interface.

 <P>All constants must be immutable.
 No instances of this class can be constructed.

 <P>The Java Look and Feel Design Guidelines recommend that components be 
 spaced using this scheme :
<ul>
 <li>place <tt>6*N</tt> pixels between items 
 <li>but, place <tt>6*N-1</tt> pixels between items if there is one 
 white border present
</ul>

 The <tt>XXX_SPACE(S)</tt> and {@link #STANDARD_BORDER} items follow the second scheme, 
 and use <tt>6*N-1</tt> pixel spacings (since this is the more common case). 
*/
public final class UiConsts  {

  public static final int ONE_SPACE = 5;
  public static final int TWO_SPACES = 11;
  public static final int THREE_SPACES = 17;
  public static final int STANDARD_BORDER = TWO_SPACES; 

  /** Symbolic name for absence of keystroke mask.  */
  public static final int NO_KEYSTROKE_MASK = 0;
  
  /** Suggested width for a <tt>JTextField</tt>  */
  public static final int SIMPLE_FIELD_WIDTH = 20;
 
  // PRIVATE
  
  /** Prevent object construction.  */
  private UiConsts(){
    throw new AssertionError();
  }
}