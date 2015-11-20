package com.zk.yuz_bul;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciTableModel;

import com.zk.App;

public class YuzBulAcAction extends AbstractAction{
	// PRIVATE 
	private JFrame frame;
	private JTable table;
	private KullaniciTableModel kullaniciTableModel;
	
	/** Constructor. */
	public YuzBulAcAction(JFrame frame, JTable table, KullaniciTableModel kullaniciTableModel ){
		super("Yüz Bul", null );
	    putValue(SHORT_DESCRIPTION, "Yüz Bul"); 
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
	    System.out.println(secilenKullanici);
	    App.kullanici = secilenKullanici;
		App.setupButtons();
		App.adKaydetPanel.repaint();
		App.kaydetButton.repaint();

		App.yuzGoruntuleriAyarla();
		System.out.println(App.yuzTable.getSelectionModel().toString());
	    //App.yuzScrollPane.repaint();
	    App.dialog.setVisible(true);
	}
	  
}
