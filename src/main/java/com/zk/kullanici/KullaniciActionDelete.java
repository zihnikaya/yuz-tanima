package com.zk.kullanici;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import com.zk.main.MainWindow;
import com.zk.util.Util;

public class KullaniciActionDelete  extends AbstractAction {
	  // PRIVATE
	  private JTable fTable;
	  private KullaniciTableModel fKullaniciTableModel;
	  private static final Logger fLogger = Util.getLogger(KullaniciActionEkle.class);

  /** Constructor. */
  public KullaniciActionDelete(JTable aTable, KullaniciTableModel aKullaniciTableModel){
    super("Sil", null );
    putValue(SHORT_DESCRIPTION, "Seçilen kullanýcýyý sil!"); 
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_D) );
    fTable = aTable;
    fKullaniciTableModel = aKullaniciTableModel;
    setEnabled(false);
  }
  
  @Override public void actionPerformed(ActionEvent aActionEvent) {
    int row = fTable.getSelectedRow();    
    Kullanici selectedKullanici = fKullaniciTableModel.kullaniciAl(row);
    KullaniciDAO dao = new KullaniciDAO();
    dao.sil(selectedKullanici);
    setEnabled(false);
    MainWindow.getInstance().refreshView();
  }
  
}
