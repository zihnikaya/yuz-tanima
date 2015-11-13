package com.zk.kullanici;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.zk.exception.InvalidInputException;
import com.zk.main.MainWindow;
import com.zk.util.Edit;
import com.zk.util.Util;

final class KullaniciController implements ActionListener {
  
  // PRIVATE 
  private final KullaniciView fView;
  private Kullanici fKullanici;
  private Edit fEdit;
  private KullaniciDAO fDAO = new KullaniciDAO();
	
  KullaniciController(KullaniciView aView, Edit aEdit){
    fView = aView;
    fEdit = aEdit;
  }
  
  @Override public void actionPerformed(ActionEvent aEvent){
    try {
      createValidMovieFromUserInput();
    }
    catch(InvalidInputException ex){
      informUserOfProblems(ex);
    }
    if ( isUserInputValid() ){
      if( Edit.ADD == fEdit ) {
        fDAO.ekle(fKullanici);
      }
      else if (Edit.CHANGE == fEdit) {
        fDAO.degistir(fKullanici);
      }
      else {
        throw new AssertionError();
      }
      fView.closeDialog();
      MainWindow.getInstance().refreshView();
    }
  }
  
  private void createValidMovieFromUserInput() throws InvalidInputException {
	  fKullanici = new Kullanici(fView.idAl(), fView.ogrNoAl(), fView.adAl(), fView.soyadAl(), fView.bolumAl());
  }

  private boolean isUserInputValid(){
    return fKullanici != null;
  }
  
  private void informUserOfProblems(InvalidInputException aException) {
    Object[] messages = aException.getErrorMessages().toArray();
    JOptionPane.showMessageDialog(
      fView.getDialog(), messages, 
      "Kullanici kaydedilemedi!", JOptionPane.ERROR_MESSAGE
    );
  }
}