package zk.tez.yuz_tanima;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciTableModel;

import zk.tez.UygulamaCalistir;

public class YuzTanimaAcAction extends AbstractAction{
	// PRIVATE 
	private JFrame frame;
	private JTable table;
	private KullaniciTableModel kullaniciTableModel;
	
	/** Constructor. */
	public YuzTanimaAcAction(JFrame frame, JTable table, KullaniciTableModel kullaniciTableModel ){
		super("Yüz Tanıma", null );
	    putValue(SHORT_DESCRIPTION, "Yüz tanıma"); 
	    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_T) );
	    this.frame = frame;
	    this.table = table;
	    this.kullaniciTableModel = kullaniciTableModel;
	    setEnabled(false);
	}
	  
	@Override public void actionPerformed(ActionEvent actionEvent) {
		setEnabled(false);
	    int row = table.getSelectedRow();
	    Kullanici secilenKullanici = kullaniciTableModel.kullaniciAl(row);
	    UygulamaCalistir.kullanici = secilenKullanici;
	    UygulamaCalistir.dialog.setVisible(false);
	    UygulamaCalistir.GUIYuzTanima();
	    UygulamaCalistir.taniButtonAyarla();
	    UygulamaCalistir.dialog.setVisible(true);
	}
	  
}
