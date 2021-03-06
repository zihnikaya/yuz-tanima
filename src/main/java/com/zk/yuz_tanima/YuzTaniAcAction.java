package com.zk.yuz_tanima;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.zk.App;
import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciTableModel;

public class YuzTaniAcAction extends AbstractAction {
	// PRIVATE 
	private JFrame frame;
	private JTable table;
	private KullaniciTableModel kullaniciTableModel;
	
	public YuzTaniAcAction(JFrame frame ){
		super("Yüz Tanıma", null );
	    putValue(SHORT_DESCRIPTION, "Yüz Tanıma"); 
	    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_T) );
	    this.frame = frame;
	}
	  
	@Override 
	public void actionPerformed(ActionEvent actionEvent) {
		setEnabled(false);
		App.dialog.setTitle("Yüz Tanıma");
		App.taniButtonuGoster();
	    App.dialog.setVisible(true);
	}
	  
}
