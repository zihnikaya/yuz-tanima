package com.zk.kullanici;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.zk.util.Util;

public final class KullaniciTableModel extends AbstractTableModel {
  
  // PRIVATE //
  private KullaniciDAO fDAO;
  private List<Kullanici> fKullanicilar;
  private int fNumClicks = 0;	

  public KullaniciTableModel(){
    fDAO = new KullaniciDAO();
    fKullanicilar = fDAO.list();
  }

  public void refreshView() {
    fKullanicilar = fDAO.list();
    fireTableDataChanged();
  }
  
  public Kullanici kullaniciAl(int aRow){
    return fKullanicilar.get(aRow);
  }
  
  public void sortByColumn(int aIdx){
    fNumClicks++;
    if( aIdx == 1 ) {
      //natural sorting of the Kullanici class
      Collections.sort(fKullanicilar);
    }
    else {
      Comparator<Kullanici> comparator = null;
      if ( aIdx == 0 ){
        comparator = Kullanici.TITLE_SORT;
      }
      else if ( aIdx == 2 ){
        comparator = Kullanici.RATING_SORT;
      }
      else if ( aIdx == 3 ){
        comparator = Kullanici.COMMENT_SORT;
      }
      Collections.sort(fKullanicilar, comparator);
    }
    if( (fNumClicks % 2) == 0){
      Collections.reverse(fKullanicilar);
    }
    fireTableDataChanged();
  }
  
  @Override public int getColumnCount() {
    return 4;
  }
  
  @Override public int getRowCount() {
    return fKullanicilar.size();
  }
  
  @Override public Object getValueAt(int aRow, int aCol) {
    Object result = null;
    Kullanici kullanici = fKullanicilar.get(aRow);
    if(aCol == 0) {
      result = kullanici.ogrNoAl();
    }
    else if(aCol == 1) {
      result = kullanici.adAl();
    }
    else if(aCol == 2) {
    	result = kullanici.soyadAl();
    }
    else if(aCol == 3) {
      result = kullanici.bolumAl();
    }
    return result;
  }
  
  @Override public String getColumnName(int aIdx){
    String result = "";
    if( aIdx == 0) {
      result = "Öðrenci No";
    }
    else if( aIdx == 1) {
      result = "Adý";
    }
    else if( aIdx == 2) {
      result = "Soyadý";
    }
    else if( aIdx == 3) {
      result =  "Bölümü";
    }
    return result;
  }
  

}
