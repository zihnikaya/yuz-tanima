package com.zk.giris;


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
		      NO_OWNER, "Giriş", 
		      true, OnClose.DISPOSE, getBody(), getButtons()
		    );
		    fStandardDialog.setDefaultButton(fGiris);
		    fStandardDialog.display();
	  }	  
	  
	  private JPanel getBody(){
		    JPanel result = new JPanel();
		    result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		    	
		    
		    UiUtil.alignAllX(result, UiUtil.AlignX.LEFT);
		    return result;
		  }

		  private List<JButton> getButtons(){
		    List<JButton> result = new ArrayList<>();
		    fGiris = new JButton("Giriş");
		    fGiris.setActionCommand(GirisController.GIRIS);
		    fGiris.addActionListener(fController);
		    result.add(fGiris);		    
		    
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
	    fMesaj.setText("Giriş başarısız! Tekrar deneyiniz.");
	  }	  	 

}
