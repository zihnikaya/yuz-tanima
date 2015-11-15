package com.zk.yuz_tanima;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.zk.App;
import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciTableModel;

public class YuzTanimaAcAction extends AbstractAction {
	// PRIVATE 
	private JFrame frame;
	private JTable table;
	private KullaniciTableModel kullaniciTableModel;
	
	public YuzTanimaAcAction(JFrame frame, JTable table, KullaniciTableModel kullaniciTableModel ){
		super("Yüz Tanıma", null );
	    putValue(SHORT_DESCRIPTION, "Yüz Tanıma"); 
	    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_T) );
	    this.frame = frame;
	    this.table = table;
	    this.kullaniciTableModel = kullaniciTableModel;
	    setEnabled(false);
	}
	  
	@Override 
	public void actionPerformed(ActionEvent actionEvent) {
		setEnabled(false);
	    int row = table.getSelectedRow();
	    Kullanici secilenKullanici = kullaniciTableModel.kullaniciAl(row);
	    App.kullanici = secilenKullanici;
	    App.dialog.setVisible(false);
	    App.GUIYuzTanima();
	    App.taniButtonAyarla();
	    App.dialog.setVisible(true);
	}
	  
}
