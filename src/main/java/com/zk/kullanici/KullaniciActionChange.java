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
  private static final Logger fLogger = Util.getLogger(KullaniciActionEkle.class);	
  
  /** Constructor. */
  public KullaniciActionChange(JFrame aFrame, JTable aTable, KullaniciTableModel aKullaniciTableModel){
    super("D�zenle...", null );
    putValue(SHORT_DESCRIPTION, "Kullan�c�y� d�zenle"); 
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
