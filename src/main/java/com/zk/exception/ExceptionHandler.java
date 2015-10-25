package com.zk.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.zk.util.Util;
import com.zk.util.ui.UiUtil;

/** Custom handler for any uncaught exceptions.
 
 <P>By default, a Swing app will handle uncaught exceptions simply by 
  printing a stack trace to {@link System#err}. However, the end user will 
  not usually see that, and if they do, they will not likely understand it. 
  This class addresses that problem, by showing the end user a 
  simple error message in a modal dialog. (The dialog's owner is the 
  currently active frame.)
*/
public final class ExceptionHandler implements Thread.UncaughtExceptionHandler {

  /** 
  Custom handler for uncaught exceptions.
  
  <P>Displays a simple model dialog to the user, showing that an error has
   occured. The text of the error includes {@link Throwable#toString()}. 
   The stack trace is logged at a SEVERE level.
 */
  @Override  public void uncaughtException(Thread aThread, Throwable aThrowable) {
    fLogger.severe(getStackTrace(aThrowable));
    JOptionPane.showMessageDialog(
      UiUtil.getActiveFrame(), "Error: " + aThrowable.toString(), 
      "Error", JOptionPane.ERROR_MESSAGE
    );
  }
  
  // PRIVATE 
  private static final Logger fLogger = Util.getLogger(ExceptionHandler.class);
  
  private String getStackTrace(Throwable aThrowable) {
    final Writer result = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(result);
    aThrowable.printStackTrace(printWriter);
    return result.toString();
  }
}
