package com.zk.util;

import java.util.*;
import java.util.logging.Logger;

import com.zk.exception.InvalidInputException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 Static convenience methods for common tasks, which eliminate code duplication.
*/
public final class Util {

   /**
    Return <tt>true</tt> only if <tt>aText</tt> is not null,
    and is not empty after trimming. (Trimming removes both
    leading/trailing whitespace and ASCII control characters.)
   
    <P> For checking argument validity, {@link Args#checkForContent} should 
    be used instead of this method.
    
    <P>This method is particularly useful, since it is very commonly required.
   
    @param aText possibly-null.
   */
   public static boolean textAra(String aText) {
     return (aText != null) && (aText.trim().length() > 0);
   }

  /**
   Return <tt>true</tt> only if <tt>aNumber</tt> is in the range 
   <tt>aLow..aHigh</tt> (inclusive).
  
   <P> For checking argument validity, {@link Args#checkForRange} should 
   be used instead of this method.
  
   @param aLow less than or equal to <tt>aHigh</tt>.
  */
  static public boolean isInRange( int aNumber, int aLow, int aHigh ){
    if (aLow > aHigh) {
      throw new IllegalArgumentException("Low is greater than High.");
    }
    return (aLow <= aNumber && aNumber <= aHigh);
  }

  /**
   Return <tt>true</tt> if <tt>aBoolean</tt> equals "true" (ignore case), or 
   <tt>false</tt> if <tt>aBoolean</tt> equals "false" (ignore case).
  
  <P>Note that this behavior is different from that of <tt>Boolean.getValue</tt>.
  
   @param aBoolean equals "true" or "false" (not case-sensitive).
  */
  public static Boolean parseBoolean(String aBoolean){
    if ( aBoolean.equalsIgnoreCase("true") ) {
      return Boolean.TRUE;
    }
    else if ( aBoolean.equalsIgnoreCase("false") ) {
      return Boolean.FALSE;
    }
    else {
      throw new IllegalArgumentException("Cannot parse into Boolean: " + aBoolean);
    }
  }
  
  /**
   Return a {@link Logger} whose name follows a specific naming convention.
  
   <P>The conventional logger names are taken as   
   <tt>aClass.getPackage().getName()</tt>.
   
   <P>Logger names appearing in the <tt>logging.properties</tt> config file
   must match the names returned by this method.
  */
  public static Logger getLogger(Class<?> aClass){
     return Logger.getLogger(aClass.getPackage().getName());  
   }
  
  /**
  Parse text into a {@link Date}. If the text has no content, then return <tt>null</tt>.
  */
  public static Date parseDate(String aDate, String aName) throws InvalidInputException {
    Date result = null;
    if( textAra(aDate) ) {
      DateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);
      FORMAT.setLenient(false);
      try {
        result = FORMAT.parse(aDate);
      }
      catch (ParseException ex){
        throwEx(aName + " is not a valid date: " + aDate);
      }
    }
    return result;
  }
  
  /**   Format an arbitrary Object, into a form suitable for the end user.  */
  public static String format(Object aObject){
    String result = "";
    if( aObject != null ){
      if( aObject instanceof Date ){
        Date date = (Date)aObject;
        DateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);
        FORMAT.setLenient(false);
        result = FORMAT.format(date);
      }
      else {
        result = String.valueOf(aObject);
      }
    }
    return result;
  }
  
  
  /**   Parse text into a {@link BigDecimal}. If the text has no content, then return <tt>null</tt>. */
  public static BigDecimal parseBigDecimal(String aBigDecimal, String aName) throws InvalidInputException {
    BigDecimal result = null;
    if ( textAra(aBigDecimal) ) {
      try {
        result = new BigDecimal(aBigDecimal);
      }
      catch (NumberFormatException exception){
        throwEx(aName + " is not a valid number.");
      }
    }
    return result;
  }

  // PRIVATE
  
  /** The application's date format. */
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  
  private static void throwEx(String aMessage) throws InvalidInputException {
    InvalidInputException ex = new InvalidInputException();
    ex.add(aMessage);
    throw ex;
  }
}
