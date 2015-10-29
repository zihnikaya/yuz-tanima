package com.zk.main;

import zk.tez.UygulamaCalistir;
import zk.tez.yuz_tanima.YuzTanimaAcAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.zk.about.AboutAction;
import com.zk.exit.ExitAction;
import com.zk.kullanici.KullaniciActionChange;
import com.zk.kullanici.KullaniciActionDelete;
import com.zk.kullanici.KullaniciActionEkle;
import com.zk.kullanici.KullaniciTableModel;
import com.zk.util.ui.UiUtil;
import com.zk.yuz.KameraAcAction;

public final class MainWindow {
	
	  private KullaniciTableModel fKullaniciTableModel;
	  private JTable fKullaniciTable;
	  private Action fChangeKullaniciAction;
	  private Action fDeleteKullaniciAction;
	  private Action kameraActionAc;
	  private Action yuzTanimaAcAction;
	  private String fUserName;
	  private static MainWindow INSTANCE = new MainWindow();	  
  
  public static MainWindow getInstance() {
    return INSTANCE;
  }
  
  public void buildAndShow(String aUserName){
    fUserName = aUserName;
    buildGui();
  }
  
  public void refreshView(){
	  fKullaniciTableModel.refreshView();
  }
  
  public String getUserName(){
    return fUserName;
  }
  
  private MainWindow() {  }
  
  private void buildGui(){
    JFrame frame = new JFrame(
      App.APP_NAME + 
      " - " + ("Kullan�c�lar")
    ); 
    
    fKullaniciTableModel = new KullaniciTableModel();
    fKullaniciTable = new JTable(fKullaniciTableModel);
    buildActionsAndMenu(frame);
    buildContent(frame);
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    placeInMiddlePartOfTheScreen(frame);
    addApplicationIcon(frame);
    UiUtil.centerAndShow(frame);
  }

  private final class SortMovieTable extends MouseAdapter {
    @Override public void mouseClicked(MouseEvent aEvent) {
      int columnIdx = fKullaniciTable.getColumnModel().getColumnIndexAtX(aEvent.getX());
      fKullaniciTableModel.sortByColumn(columnIdx);
    }
  }
  
  private final class LaunchEditMovieDialog extends MouseAdapter {
    @Override public void mouseClicked(MouseEvent aEvent) {
      if( aEvent.getClickCount() == 2) {
        ActionEvent event = new ActionEvent(this, 0, "");
        fChangeKullaniciAction.actionPerformed(event);
      }
    }
  }
  
  private final class EnableEditActions implements ListSelectionListener {
    @Override public void valueChanged(ListSelectionEvent aEvent) {
      if( aEvent.getFirstIndex() != -1) {
        fDeleteKullaniciAction.setEnabled(true);
        fChangeKullaniciAction.setEnabled(true);
        kameraActionAc.setEnabled(true);
        yuzTanimaAcAction.setEnabled(true);
        
      }
      else {
        fDeleteKullaniciAction.setEnabled(false);
        fChangeKullaniciAction.setEnabled(false);
        kameraActionAc.setEnabled(false);
        yuzTanimaAcAction.setEnabled(false);
      }
    }
  }
  
  private void buildActionsAndMenu(JFrame aFrame) {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("Dosya");
    fileMenu.setMnemonic('D');     
    Action addMovieAction = new KullaniciActionEkle(aFrame);
    fileMenu.add(new JMenuItem(addMovieAction));    
    fChangeKullaniciAction = new KullaniciActionChange(aFrame, fKullaniciTable, fKullaniciTableModel);
    fileMenu.add(new JMenuItem(fChangeKullaniciAction));    
    
    kameraActionAc = new KameraAcAction(aFrame, fKullaniciTable, fKullaniciTableModel);
    fileMenu.add(new JMenuItem(kameraActionAc));
    
    yuzTanimaAcAction = new YuzTanimaAcAction(aFrame, fKullaniciTable, fKullaniciTableModel);
    fileMenu.add(new JMenuItem(yuzTanimaAcAction));

    fDeleteKullaniciAction = new KullaniciActionDelete(fKullaniciTable, fKullaniciTableModel);
    fileMenu.add(new JMenuItem(fDeleteKullaniciAction));    
    Action exitAction = new ExitAction();
    fileMenu.add(new JMenuItem(exitAction));
    menuBar.add(fileMenu);
    
    JMenu helpMenu = new JMenu("Yard�m");
    helpMenu.setMnemonic('Y');
    helpMenu.add(new JMenuItem(new AboutAction(aFrame)));
    menuBar.add(helpMenu);
    
    aFrame.setJMenuBar(menuBar);
    
  }
  
  private void placeInMiddlePartOfTheScreen(JFrame aFrame) {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension halfScreen = new Dimension(2*screen.width/3, screen.height/2);
    aFrame.setPreferredSize(halfScreen);
  }

  private void addApplicationIcon(JFrame aFrame) {
    ImageIcon icon =  UiUtil.createImageIcon("app_icon.png", "Application icon", this.getClass());
    aFrame.setIconImage(icon.getImage());
  }

  /** Build the main content of the frame. */
  private void buildContent(JFrame aFrame) {
	  fKullaniciTable.setBackground(Color.LIGHT_GRAY);
    
	  fKullaniciTable.getColumnModel().getColumn(0).setPreferredWidth(100);
	  fKullaniciTable.getColumnModel().getColumn(1).setPreferredWidth(20);
	  fKullaniciTable.getColumnModel().getColumn(2).setPreferredWidth(20);
	  fKullaniciTable.getColumnModel().getColumn(3).setPreferredWidth(200);
    
    clickOnHeaderSortsTable();
    doubleClickShowsEditDialog();
    rowSelectionEnablesActions();
    
    JScrollPane panel = new JScrollPane(fKullaniciTable);
    aFrame.getContentPane().add(panel);  
  }

  private void clickOnHeaderSortsTable() {
	  fKullaniciTable.getTableHeader().addMouseListener(new SortMovieTable());
  }
  
  private void doubleClickShowsEditDialog() {
	  fKullaniciTable.addMouseListener( new LaunchEditMovieDialog() );
  }

  private void rowSelectionEnablesActions() {
	  fKullaniciTable.getSelectionModel().addListSelectionListener(new EnableEditActions());
  }
}
      