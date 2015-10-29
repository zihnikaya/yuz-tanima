package com.zk.yuz;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciTableModel;

import zk.tez.UygulamaCalistir;

public class KameraAcAction extends AbstractAction{
	// PRIVATE 
	private JFrame frame;
	private JTable table;
	private KullaniciTableModel kullaniciTableModel;
	
	/** Constructor. */
	public KameraAcAction(JFrame frame, JTable table, KullaniciTableModel kullaniciTableModel ){
		super("Y�z ekle/g�ncelle", null );
	    putValue(SHORT_DESCRIPTION, "Y�z ekle/g�ncelle"); 
	    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_Z) );
	    this.frame = frame;
	    this.table = table;
	    this.kullaniciTableModel = kullaniciTableModel;
	    setEnabled(false);
	}
	  
	@Override public void actionPerformed(ActionEvent actionEvent) {
		setEnabled(false);
	    int row = table.getSelectedRow();
	    Kullanici secilenKullanici = kullaniciTableModel.kullaniciAl(row);
	    App.kullanici = secilenKullanici;
	    //UygulamaCalistir.GUIYuzBulma();
	    //UygulamaCalistir.loadCascade();
	    //UygulamaCalistir.kameraAc();

	    App.yuzGoruntuleriAyarla();
	    App.dialog.setVisible(true);
	}
	  
}
