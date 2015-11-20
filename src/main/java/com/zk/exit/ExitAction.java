package com.zk.exit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.zk.kullanici.KullaniciDAO;
import com.zk.util.Util;

public final class ExitAction extends AbstractAction {
  
  /** Constructor. */
  public ExitAction(){
    super("Çıkış", null);
    putValue(SHORT_DESCRIPTION, "Uygulamadan ��k"); 
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
    //mnemonic activates only when alt is pressed and held down
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_X) );    
  }
  
  @Override public void actionPerformed(ActionEvent aActionEvent) {
    KullaniciDAO dao = new KullaniciDAO();
    System.exit(0);
  }
  
}
