package com.zk.kullanici;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import com.zk.util.Util;

/** Show dialog for adding a new {@link Kullanici}. */
public final class KullaniciActionEkle extends AbstractAction  {
  
  // PRIVATE
  private JFrame fFrame;
  private static final Logger fLogger = Util.getLogger(KullaniciActionEkle.class);	
	
  /** Constructor. */
  public KullaniciActionEkle(JFrame aFrame){
    super("Ekle...", null );
    putValue(SHORT_DESCRIPTION, "Yeni Kullanýcý Ekle"); 
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A) );
    fFrame = aFrame;
  }
  
  @Override public void actionPerformed(ActionEvent aActionEvent) {
    KullaniciView view = new KullaniciView(fFrame);
  }
  
}
