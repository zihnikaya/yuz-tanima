package com.zk.yuz;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import com.zk.kullanici.Kullanici;

public final class YuzTableModel extends AbstractTableModel {
  
  private static final long serialVersionUID = 1L;
  private YuzDAO DAO;
  private List<Yuz> yuzler;
  private Kullanici kullanici;

  public YuzTableModel(Kullanici kullanici){
    this.kullanici= kullanici;
	DAO = new YuzDAO(kullanici);
    yuzler = DAO.list();
  }

  public void yenile() {
	yuzler = DAO.list();
  }
  
  @Override public int getColumnCount() {
    return 2;
  }
  
  @Override public int getRowCount() {
    return yuzler.size();
  }
  
  @Override public Object getValueAt(int aRow, int aCol) {
    Object result = null;
    Yuz yuz = yuzler.get(aRow);
    if(aCol == 0) {
      result = yuz.idAl();
    }
    else if(aCol == 1) {
    	result = new ImageIcon(yuz.goruntuAl());
    }
    return result;
  }
  
  @Override public String getColumnName(int aIdx){
    String result = "";
    if( aIdx == 0) {
      result = "ID";
    }
    else if( aIdx == 1) {
      result = this.kullanici.adAl()+" "+this.kullanici.soyadAl();
    }
    return result;
  }
  
  @Override
  public Class<?> getColumnClass(int column) {
      switch(column) {
          case 0:return Integer.class;
          case 1: return ImageIcon.class;
          default: return Object.class;
      }
  }  

}

