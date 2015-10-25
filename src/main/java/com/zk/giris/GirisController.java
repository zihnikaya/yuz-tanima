package com.zk.giris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.zk.main.MainWindow;
import com.zk.util.Util;

public final class GirisController implements ActionListener {

	/** Action command string. */
	static final String GIRIS = "Giri�";
	  
	/** Action command string. */
	static final String VAZGEC = "Vazge�";	
	
	private GirisView fView;
	private int fNumAttempts = 0;
	private static final int MAX_NUM_ATTEMPTS = 3;	
	
	@Override 
	public void actionPerformed(ActionEvent aEvent) {
	    String command = aEvent.getActionCommand();
	    if ( GIRIS.equals(command) ) {
	    	kulKimligiDogrula();
	    }
	    else if( VAZGEC.equals(command) ){
	    	uygulamaKapat();
	    }
	    else {
	    	throw new AssertionError("Unexpected command: " + command);
	    }
	}
		
	public void kimlikSor(){
		fView = new GirisView(this);
		fView.girisEkraniGoster();
	}
	
	private void uygulamaKapat() {
		fView.kapat();
		System.exit(0);
	} 	
	
	  /**
	   Validate the user name and password entered by the user.
	   
	   <P>If the validation succeeds, close the login screen and display the {@link MainWindow}.
	   If the validation fails, allow the user to make a few more attempts at login.
	   If the validation fails repeatedly, close the application.
	  */
	  void kulKimligiDogrula(){
	    fNumAttempts++;
	    String kulAdi = fView.kulAdiAl();
	    String sifre = fView.sifreAl();
	    if (girisFormDogrula(kulAdi, sifre)){
	      fView.kapat();
	      showMainWindow(kulAdi);
	    }
	    else {
	      if(fNumAttempts < MAX_NUM_ATTEMPTS) {
	        fView.tekrarDene();
	      }
	      else {
	    	  uygulamaKapat();
	      }
	    }
	  }
	  	
	  
	  private boolean girisFormDogrula(String aKulAdi, String aSifre){
		  return Util.textAra(aKulAdi) &&  Util.textAra(aSifre);
	  }
	  
	  private void showMainWindow(String aUserName){
		  MainWindow.getInstance().buildAndShow(aUserName);
	  }	  
	  
}
