package com.zk.kullanici;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.zk.exception.InvalidInputException;
import com.zk.main.MainWindow;
import com.zk.util.Edit;
import com.zk.util.Util;

/** Add a new {@link Kullanici} to the database, or change an existing one. 
 
 <P>It's important to note that this class uses most of the other classes in 
 this feature to get its job done (it doesn't use the <tt>Action</tt> classes):
 <ul>   <li>it gets user input from the view - {@link KullaniciView}   <li>it validates user input using the model - {@link Kullanici}   <li>it persists the data using the Data Access Object  - {@link KullaniciDAO}
 </ul>
*/
final class KullaniciController implements ActionListener {
  
  // PRIVATE 
  private final KullaniciView fView;
  private Kullanici fKullanici;
  private Edit fEdit;
  private KullaniciDAO fDAO = new KullaniciDAO();
  private static final Logger fLogger = Util.getLogger(KullaniciController.class);	
	
  /**
   Constructor.
   @param aView user interface
   @param aEdit identifies what type of edit - add or change 
  */
  KullaniciController(KullaniciView aView, Edit aEdit){
    fView = aView;
    fEdit = aEdit;
  }
  
  /**
   Attempt to add a new {@link Kullanici}, or edit an existing one.
   
   <P>If the input is invalid, then inform the user of the problem(s).
   If the input is valid, then add or change the <tt>Kullanici</tt>, close the dialog, 
   and update the main window's display.  
  */
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
	  fKullanici = new Kullanici(fView.idAl(), fView.ogrNoAl(), fView.adAl(), fView.soyadAl());
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