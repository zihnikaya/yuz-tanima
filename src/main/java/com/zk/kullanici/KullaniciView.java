package com.zk.kullanici;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.zk.util.Edit;
import com.zk.util.Util;
import com.zk.util.ui.OnClose;
import com.zk.util.ui.StandardDialog;
import com.zk.util.ui.UiUtil;


final class KullaniciView {

  // PRIVATE 
  private StandardDialog fStandardDialog;
  private Edit fEdit;
  private String fId;
  private JTextField fOgrNo = new JTextField();
  private JTextField fAd = new JTextField();
  private JTextField fSoyad = new JTextField();
  private JTextField fBolum = new JTextField();
  private JButton fEditButton;
  
  KullaniciView(JFrame aParent) {
    fEdit = Edit.ADD;
    buildGui(aParent, "Kullanıcı Ekle");
    fStandardDialog.display();
  }

  KullaniciView(JFrame aParent, Kullanici aSecilenKullanici) {
    fEdit = Edit.CHANGE;
    fId = aSecilenKullanici.idAl();
    buildGui(aParent, "Kullanici Düzenle");
    populateFields(aSecilenKullanici);
    fStandardDialog.display();
  }

  String idAl() {
    return fId;
  }

  String ogrNoAl() {
    return fOgrNo.getText();
  }

  String adAl() {
    return fAd.getText();
  }

  String soyadAl() {
    return fSoyad.getText();
  }

  String bolumAl() {
    return fBolum.getText();
  }

  void closeDialog() {
    fStandardDialog.dispose();
  }

  JDialog getDialog() {
    return fStandardDialog.getDialog();
  }


  private void populateFields(Kullanici aSecilenKullanici) {
    fOgrNo.setText(aSecilenKullanici.ogrNoAl());
    fAd.setText(aSecilenKullanici.adAl());
    fSoyad.setText(aSecilenKullanici.soyadAl());
    fBolum.setText(aSecilenKullanici.bolumAl());
  }

  private void buildGui(JFrame aParent, String aDialogTitle) {
    fStandardDialog = new StandardDialog(
      aParent, aDialogTitle, true, OnClose.DISPOSE, getUserInputArea(), getButtons()
    );
    fStandardDialog.setDefaultButton(fEditButton);
  }

  private JPanel getUserInputArea() {
    JPanel result = new JPanel();
    result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));

    addTextField(fOgrNo, "Öğrenci No", result);
    addTextField(fAd, "Adı", result);
    addTextField(fSoyad, "Soyadı", result);
    addTextField(fBolum, "Bölüm", result);
    UiUtil.alignAllX(result, UiUtil.AlignX.LEFT);
    return result;
  }

  private void addTextField(JTextField aTextField, String aLabel, JPanel aPanel) {
    JLabel label = new JLabel(aLabel);
    aPanel.add(label);
    aPanel.add(aTextField);
    aTextField.setColumns(15);
  }

  private java.util.List<JButton> getButtons() {
    java.util.List<JButton> result = new ArrayList<>();

    fEditButton = new JButton(fEdit.toString());
    fEditButton.addActionListener(new KullaniciController(this, fEdit));
    result.add(fEditButton);

    JButton cancel = new JButton("Vazgeç");
    cancel.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent arg0) {
        closeDialog();
      }
    });
    result.add(cancel);
    return result;
  }
}
