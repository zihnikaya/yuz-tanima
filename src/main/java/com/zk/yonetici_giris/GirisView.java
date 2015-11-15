package com.zk.yonetici_giris;


import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.zk.util.ui.OnClose;
import com.zk.util.ui.StandardDialog;
import com.zk.util.ui.UiUtil;

public final class GirisView {
	
	  private GirisController fController;
	  private StandardDialog fStandardDialog;
	  private JTextField fKulAdi;  
	  private JPasswordField fSifre;
	  private JLabel fMesaj;
	  private JButton fGiris;	  
	  
	  public GirisView(GirisController aController){
		  fController = aController;
	  }
	  
	  void girisEkraniGoster(){
	    JFrame NO_OWNER = null;
	    fStandardDialog = new StandardDialog(
	      NO_OWNER, "Giri�", 
	      true, OnClose.DISPOSE, getBody(), getButtons()
	    );
	    fStandardDialog.setDefaultButton(fGiris);
	    fStandardDialog.display();
	  }	  
	  
	  private JPanel getBody(){
		    JPanel result = new JPanel();
		    result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		    
		    fMesaj = new JLabel("L�tfen giri� yap�n�z.");
		    result.add(fMesaj);
		    result.add(Box.createVerticalStrut(5));
		    
		    result.add(new JLabel("Kullan�c� ad�:"));
		    fKulAdi = new JTextField();
		    fKulAdi.setColumns(15);
		    result.add(fKulAdi);
		    
		    result.add(new JLabel("�ifre:"));
		    fSifre = new JPasswordField();
		    result.add(fSifre);
		    
		    UiUtil.alignAllX(result, UiUtil.AlignX.LEFT);
		    return result;
		  }

		  private List<JButton> getButtons(){
		    List<JButton> result = new ArrayList<>();
		    fGiris = new JButton("Giri�");
		    fGiris.setActionCommand(GirisController.GIRIS);
		    fGiris.addActionListener(fController);
		    result.add(fGiris);
		    
		    JButton cancel = new JButton("Vazge�");
		    cancel.setActionCommand(GirisController.VAZGEC);
		    cancel.addActionListener(fController);
		    result.add(cancel);
		    
		    return result;
	  }	  
	  
	  String kulAdiAl(){
	    return fKulAdi.getText();
	  }
	  
	  String sifreAl(){
	    return fSifre.getPassword().toString();
	  }	  

	  void kapat(){
	    fStandardDialog.dispose();
	  }	
	  
	  void tekrarDene(){
	    fMesaj.setText("Giri� ba�ar�s�z! Tekrar deneyiniz.");
	  }	  	 

}
