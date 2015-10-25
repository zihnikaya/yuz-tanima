package com.zk.util;

/**
 Enumeration of kinds of edit operations.
    An edit operation is defined here as either an Add, Change, or Delete. */
public enum Edit {
	  
    
  ADD("Ekle"), CHANGE("G�ncelle"), DELETE("Sil");
  
  private String fName;
	
  @Override public String toString(){
    return fName;
  }
  
  private Edit(String aName){
    fName = aName;
  }

}
