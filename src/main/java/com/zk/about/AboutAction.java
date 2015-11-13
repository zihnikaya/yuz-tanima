package com.zk.about;

import com.zk.App;

import static com.zk.util.Consts.NEW_LINE;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.zk.util.Util;

/**
  Show a simple 'About' box, displaying the application name and version.

  <P>This implementation is very simple and plain, and uses a {@link javax.swing.JOptionPane}.
*/
public final class AboutAction extends AbstractAction {

  /** Constructor. */
  public AboutAction(JFrame aFrame) {
    super("Hakk�nda", null);
    fFrame = aFrame;
    putValue(SHORT_DESCRIPTION, "Uygulama Hakk�nda");
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
  }

  /** Show an 'zk.tez.about' box. */
  @Override public void actionPerformed(ActionEvent aActionEvent) {
    JOptionPane.showMessageDialog(fFrame, getMessageText(), "Hakk�nda",
    JOptionPane.INFORMATION_MESSAGE);
  }

  // PRIVATE 
  private final JFrame fFrame;
  private static final Logger fLogger = Util.getLogger(AboutAction.class);

  private String getMessageText() {
    StringBuilder result = new StringBuilder(App.APP_NAME + " ");
    result.append(App.APP_VERSION);
    result.append(" - Zihni Kaya'n�n y�ksek lisans tezidir.");
    result.append(NEW_LINE);
    result.append(NEW_LINE);
    result.append("Detayl� bilgi i�in zihni.net web sitesini ziyaret ediniz.");
    return result.toString();
  }
}
