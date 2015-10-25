package com.zk.util.ui;

import javax.swing.*;

import com.zk.util.ui.OnClose;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public final class StandardDialog {
	// PRIVATE
	private JDialog fDialog;

  /**
   Construct a standard dialog.
   
   @param aOwner the frame which is the owner/caller/parent of this dialog. This dialog
   gets its icon and its position from the owner. Possibly null. It's strongly recommened to use 
   a non-null owner.
   @param aTitle the text to appear on the title bar of this dialog
   @param aIsModal controls whether this dialog is modal: if <tt>true</tt>, then this
   dialog must be dismissed before you are allowed to return to the main window.
   @param aOnClose specifies desired behavior when this dialog closes 
   @param aBody the body of the dialog, where the user enters information
   @param aButtons a row of buttons appearing at the bottom of this dialog
   */
  public StandardDialog (JFrame aOwner, String aTitle, boolean aIsModal, OnClose aOnClose, JPanel aBody, java.util.List<JButton> aButtons) {
    String title = UiUtil.getDialogTitle(aTitle);
    fDialog = new JDialog(aOwner, title, aIsModal);
    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
    content.setBorder(UiUtil.getStandardBorder());
    aBody.setAlignmentX(Component.CENTER_ALIGNMENT);
    content.add(aBody);
    content.add(Box.createVerticalStrut(10));
    content.add(buildButtonPanel(aButtons));
    fDialog.add(content);
    fDialog.setResizable(false);
    fDialog.setDefaultCloseOperation(aOnClose.getIntValue());
    addCancelByEscapeKey(aOnClose);
  }

  /**
   Display the dialog.
    
   <P> The dialog is not automatically displayed in the constructor. This is because some
   callers may want to build a dialog upon startup, but only display it later. (Such a
   style might be chosen in order to slightly improve the apparent responsiveness of the
   application.)
   */
  public void display() {
    UiUtil.centerAndShow(fDialog);
  }

  /** Assign a default button for this dialog. */
  public void setDefaultButton(JButton aButton) {
    fDialog.getRootPane().setDefaultButton(aButton);
  }

  /** Call <tt>dispose</tt> on the underlying dialog object. */
  public void dispose() {
    fDialog.dispose();
  }

  /** Return the underlying dialog object.   */
  public JDialog getDialog() {
    return fDialog;
  }


  private JPanel buildButtonPanel(java.util.List<JButton> aButtons) {
    JPanel result = new JPanel();
    result.setLayout(new BoxLayout(result, BoxLayout.LINE_AXIS));
    result.add(Box.createHorizontalGlue());
    int count = 0;
    for (JButton button : aButtons) {
      count++;
      result.add(button);
      if (count < aButtons.size()) {
        result.add(Box.createHorizontalStrut(6));
      }
    }
    result.add(Box.createHorizontalGlue());
    return result;
  }
  
  /**
  Force the escape key to call the same action as the default {@link OnClose} operation
  passed to the constructor. In some special cases, this does not always work.
 */
private void addCancelByEscapeKey(final OnClose aOnClose) {
  String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
  int noModifiers = 0;
  KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, noModifiers, false);
  InputMap inputMap = fDialog.getRootPane().getInputMap(
  JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  inputMap.put(escapeKey, CANCEL_ACTION_KEY);
  AbstractAction cancelAction = new AbstractAction() {
    @Override public void actionPerformed(ActionEvent e) {
      if (OnClose.DO_NOTHING == aOnClose) {
        // do nothing
      }
      else if (OnClose.DISPOSE == aOnClose) {
        fDialog.dispose();
      }
      else if (OnClose.HIDE == aOnClose) {
        fDialog.setVisible(false);
      }
      else if (OnClose.EXIT == aOnClose) {
        fDialog.dispose();
        System.exit(0);
      }
      else {
        throw new AssertionError("Unexpected branch for this value of OnClose: " + aOnClose);
      }
    }
  };
  fDialog.getRootPane().getActionMap().put(CANCEL_ACTION_KEY, cancelAction);
}
  
}
