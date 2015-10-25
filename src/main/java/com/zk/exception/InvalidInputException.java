package com.zk.exception;

import java.util.*;

import com.zk.util.Args;

/**  Checked exception thrown when a user input validation error occurs. 
 
  <P>Most applications need to handle erroneous user input in a controlled way. 
  When user input is found to be invalid, objects of this class are created and thrown, 
  and appropriate error messages are displayed to the user. */
public final class InvalidInputException extends Exception {
  
  /**  Add a new error message to this exception. */
  public void add(String aErrorMessage){
    Args.checkForContent(aErrorMessage);
    fErrorMessages.add(aErrorMessage);
  }
  
  /** Return an unmodfiable list of error messages. */
  public List<String> getErrorMessages(){
    return Collections.unmodifiableList(fErrorMessages);
  }

  /** Return <tt>true</tt> only if {@link #add(String)} has been called at least once.*/
  public boolean hasErrors(){
    return ! fErrorMessages.isEmpty();
  }

  // PRIVATE 
  private List<String> fErrorMessages = new ArrayList<>();
}
