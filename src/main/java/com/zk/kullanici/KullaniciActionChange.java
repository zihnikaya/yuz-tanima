package com.zk.kullanici;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.zk.util.Util;

public final class KullaniciActionChange extends AbstractAction  {
	
  // PRIVATE 
  private JFrame fFrame;
  private JTable fTable;
  private KullaniciTableModel fKullaniciTableModel;
  
  /** Constructor. */
  public KullaniciActionChange(JFrame aFrame, JTable aTable, KullaniciTableModel aKullaniciTableModel){
    super("Düzenle...", null );
    putValue(SHORT_DESCRIPTION, "Kullanıcı düzenle"); 
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_E) );
    fFrame = aFrame;
    fTable = aTable;
    fKullaniciTableModel = aKullaniciTableModel;
    setEnabled(false);
  }
  
  @Override public void actionPerformed(ActionEvent aActionEvent) {
    setEnabled(false);
    int row = fTable.getSelectedRow();
    Kullanici selectedKullanici = fKullaniciTableModel.kullaniciAl(row);    
    KullaniciView view = new KullaniciView(fFrame, selectedKullanici);
  }
  
}
