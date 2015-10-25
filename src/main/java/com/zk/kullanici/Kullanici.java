package com.zk.kullanici;

import java.util.*;

import com.zk.exception.InvalidInputException;
import com.zk.util.Util;

public final class Kullanici implements Comparable<Kullanici>{

  // PRIVATE
  private String fId;
  private final String fOgrNo;
  private final String fAd;
  private final String fSoyad;
  private static final int EQUAL = 0;
  private static final int DESCENDING = -1;
	
  public Kullanici(String aId, String aOgrNo, String aAd, String aSoyad ) throws InvalidInputException {
    fId = aId;
    fOgrNo = aOgrNo;
    fAd = aAd;
    fSoyad= aSoyad;
    validateState();
  }
  
  public String idAl(){ 
	  return fId; 
  }  
  
  void idVer(String aId){ 
	  fId = aId; 
  }
  
  public String ogrNoAl(){ 
	  return fOgrNo; 
  }
  
  public String adAl(){ 
	  return fAd; 
  }
  
  public String soyadAl(){ 
	  return fSoyad; 
  }
  
 
  
  @Override public boolean equals(Object aThat){
    if ( this == aThat ) return true;
    if ( !(aThat instanceof Kullanici) ) return false;
    Kullanici that = (Kullanici)aThat;
    return 
      areEqual(this.fOgrNo, that.fOgrNo) && 
      areEqual(this.fAd, that.fAd) && 
      areEqual(this.fSoyad, that.fSoyad) 
    ; 
  }
  
  @Override public int hashCode(){
    int result = 17;
    result = addHash(result, fOgrNo);
    result = addHash(result, fAd);
    result = addHash(result, fSoyad);
    return result;
  }
  
  @Override public String toString(){
    return 
      "Kullanici  Id:" + fId + " ��renci No:" + fOgrNo + " Ad�" + fAd + 
      " Soyad�:" + fSoyad 
    ; 
  }
  
  /** 
   Default sort by Date Viewed, then Title. 
   Dates have the most recent items listed first. 
 */
  @Override public int compareTo(Kullanici aThat) {
    if ( this == aThat ) return EQUAL;
   
    int comparison = DESCENDING*comparePossiblyNull(this.fAd, aThat.fAd);
    if ( comparison != EQUAL ) return comparison;
    
    comparison = this.fOgrNo.compareTo(aThat.fOgrNo);
    if ( comparison != EQUAL ) return comparison;
    
    comparison = comparePossiblyNull(this.fSoyad, aThat.fSoyad);
    if ( comparison != EQUAL ) return comparison;
   
    
    return EQUAL;
  }
  
  /** Sort by Title. */
  public static Comparator<Kullanici> TITLE_SORT = new Comparator<Kullanici>(){
    @Override public int compare(Kullanici aThis, Kullanici aThat) {
      if ( aThis == aThat ) return EQUAL;

      int comparison = aThis.fOgrNo.compareTo(aThat.fOgrNo);
      if ( comparison != EQUAL ) return comparison;
      
      comparison = DESCENDING*comparePossiblyNull(aThis.fAd, aThat.fAd);
      if ( comparison != EQUAL ) return comparison;
      
      comparison = comparePossiblyNull(aThis.fSoyad, aThat.fSoyad);
      if ( comparison != EQUAL ) return comparison;
     
      
      return EQUAL;
    };
  };
  
  /** Sort by Rating (descending), then Date Viewed (descending). */
  public static Comparator<Kullanici> RATING_SORT = new Comparator<Kullanici>(){
    @Override public int compare(Kullanici aThis, Kullanici aThat) {
      if ( aThis == aThat ) return EQUAL;

      int comparison = DESCENDING*comparePossiblyNull(aThis.fSoyad, aThat.fSoyad);
      if ( comparison != EQUAL ) return comparison;

      comparison = DESCENDING*comparePossiblyNull(aThis.fAd, aThat.fAd);
      if ( comparison != EQUAL ) return comparison;
      
      comparison = aThis.fOgrNo.compareTo(aThat.fOgrNo);
      if ( comparison != EQUAL ) return comparison;
      

      
      return EQUAL;
    };
  };
  
  /** Sort by Comment. */
  public static Comparator<Kullanici> COMMENT_SORT = new Comparator<Kullanici>(){
    @Override public int compare(Kullanici aThis, Kullanici aThat) {
      if ( aThis == aThat ) return EQUAL;

      int comparison = comparePossiblyNull(aThis.fOgrNo, aThat.fOgrNo);
      if ( comparison != EQUAL ) return comparison;
      
      comparison = DESCENDING*comparePossiblyNull(aThis.fAd, aThat.fAd);
      if ( comparison != EQUAL ) return comparison;
      
      comparison = comparePossiblyNull(aThis.fSoyad, aThat.fSoyad);
      if ( comparison != EQUAL ) return comparison;
      
      return EQUAL;
    };
  };
 
  
  private void validateState() throws InvalidInputException {
    InvalidInputException ex = new InvalidInputException();
    
    if( ! Util.textAra(fOgrNo) ) {
        ex.add("��renci numaras� bo� b�rak�lamaz");
      }
    if( ! Util.textAra(fAd) ) {
        ex.add("Ad bo� b�rak�lamaz");
      }
    if( ! Util.textAra(fSoyad) ) {
        ex.add("Soyad bo� b�rak�lamaz");
      }

    if ( ex.hasErrors() ) {
      throw ex;
    }
  }
  
  private boolean areEqual(Object aThis, Object aThat){
    return aThis == null ? aThat == null : aThis.equals(aThat);
  }
  
  private int addHash(int aHash, Object aField){
    int result = 37*aHash;
    if (aField != null){
      result = result + aField.hashCode();
    }
    return result;
  }
  
  /** Utility method.  */
  private static <T extends Comparable<T>> int comparePossiblyNull(T aThis, T aThat){
    int result = EQUAL;
    int BEFORE = -1;
    int AFTER = 1;
    
    if(aThis != null && aThat != null){ 
      result = aThis.compareTo(aThat);
    }
    else {
      //at least one reference is null - special handling
      if(aThis == null && aThat == null) {
        //do nothing - they are not distinct 
      }
      else if(aThis == null && aThat != null) {
        result = BEFORE;
      }
      else if( aThis != null && aThat == null) {
        result = AFTER;
      }
    }
    return result;
  }
}