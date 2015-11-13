package com.zk.util.ui;

import com.zk.App;

import java.util.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;

import com.zk.util.Args;

import java.awt.*;

public final class UiUtil {

  public static void centerAndShow(Window aWindow){
    
    aWindow.pack();
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension window = aWindow.getSize();
    if (window.height > screen.height) {
      window.height = screen.height;
    }
    if (window.width > screen.width) {
      window.width = screen.width;
    }
    int xCoord = (screen.width/2 - window.width/2);
    int yCoord = (screen.height/2 - window.height/2);
    aWindow.setLocation( xCoord, yCoord );
   
    aWindow.setVisible(true);
  }
  
  public static void centerOnParentAndShow(Window aWindow){
    aWindow.pack();
    
    Dimension parent = aWindow.getParent().getSize();
    Dimension window = aWindow.getSize();
    int xCoord = 
      aWindow.getParent().getLocationOnScreen().x + 
     (parent.width/2 - window.width/2)
    ;
    int yCoord = 
      aWindow.getParent().getLocationOnScreen().y + 
      (parent.height/2 - window.height/2)
    ;
    
    //Ensure that no part of aWindow will be off-screen
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int xOffScreenExcess = xCoord + window.width - screen.width;
    if ( xOffScreenExcess > 0 ) {
      xCoord = xCoord - xOffScreenExcess;
    }
    if (xCoord < 0 ) {
      xCoord = 0;
    }
    int yOffScreenExcess = yCoord + window.height - screen.height;
    if ( yOffScreenExcess > 0 ) {
      yCoord = yCoord - yOffScreenExcess;
    }
    if (yCoord < 0) {
      yCoord = 0;
    }
    
    aWindow.setLocation( xCoord, yCoord );
    aWindow.setVisible(true);
  }

  public static Border getStandardBorder(){
    return BorderFactory.createEmptyBorder(
      UiConsts.STANDARD_BORDER, 
      UiConsts.STANDARD_BORDER, 
      UiConsts.STANDARD_BORDER, 
      UiConsts.STANDARD_BORDER
    );
  }

  public static String getDialogTitle(String aSpecificDialogName){
    Args.checkForContent(aSpecificDialogName);
    StringBuilder result = new StringBuilder(App.APP_NAME);
    result.append(": ");
    result.append(aSpecificDialogName);
    return result.toString(); 
  }
  
  public static JComponent getCommandRow(java.util.List<JComponent> aButtons){
    equalizeSizes( aButtons );
    JPanel panel = new JPanel();
    LayoutManager layout = new BoxLayout(panel, BoxLayout.X_AXIS);
    panel.setLayout(layout);
    panel.setBorder(BorderFactory.createEmptyBorder(UiConsts.THREE_SPACES, 0, 0, 0));
    panel.add(Box.createHorizontalGlue());
    Iterator<JComponent> buttonsIter = aButtons.iterator();
    while (buttonsIter.hasNext()) {
      panel.add( buttonsIter.next() );
      if (buttonsIter.hasNext()) {
        panel.add(Box.createHorizontalStrut(UiConsts.ONE_SPACE));
      }
    }
    return panel;
  }
  public static JComponent getCommandColumn( java.util.List<JComponent> aButtons ){
    equalizeSizes( aButtons );
    JPanel panel = new JPanel();
    LayoutManager layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
    panel.setLayout( layout );
    panel.setBorder(
      BorderFactory.createEmptyBorder(0,UiConsts.THREE_SPACES, 0,0)
    );
    //(no for-each is used here, because of the 'not-yet-last' check)
    Iterator<JComponent> buttonsIter = aButtons.iterator();
    while ( buttonsIter.hasNext() ) {
      panel.add(buttonsIter.next());
      if ( buttonsIter.hasNext() ) {
        panel.add( Box.createVerticalStrut(UiConsts.ONE_SPACE) );
      }
    }
    panel.add( Box.createVerticalGlue() );
    return panel;
  }

  /** Return the currently active frame. */
  public static Frame getActiveFrame() {
    Frame result = null;
    Frame[] frames = Frame.getFrames();
    for (int i = 0; i < frames.length; i++) {
      Frame frame = frames[i];
      if (frame.isVisible()) { //Component method
        result = frame;
        break;
      }
    }
    return result;
  }
  
  public static final Dimension getDimensionFromPercent(int aPercentWidth, int aPercentHeight){
    int low = 1;
    int high = 100;
    Args.checkForRange(aPercentWidth, low, high);
    Args.checkForRange(aPercentHeight, low, high);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    return calcDimensionFromPercent(screenSize, aPercentWidth, aPercentHeight);
  }

  public static void equalizeSizes(java.util.List<JComponent> aComponents) {
    Dimension targetSize = new Dimension(0,0);
    for(JComponent comp: aComponents ) {
      Dimension compSize = comp.getPreferredSize();
      double width = Math.max(targetSize.getWidth(), compSize.getWidth());
      double height = Math.max(targetSize.getHeight(), compSize.getHeight());
      targetSize.setSize(width, height);
    }
    setSizes(aComponents, targetSize);
  }

  public static void beep(){
    Toolkit.getDefaultToolkit().beep();
  }
  
  public static void alignAllX(Container aContainer, UiUtil.AlignX aAlignment){
    java.util.List<Component> components = Arrays.asList( aContainer.getComponents() );
    for(Component comp: components){
      JComponent jcomp = (JComponent)comp;
      jcomp.setAlignmentX( aAlignment.getValue() );
    }
  }
  
  /** Enumeration for horizontal alignment. */
  public enum AlignX {
    LEFT(Component.LEFT_ALIGNMENT),
    CENTER(Component.CENTER_ALIGNMENT),
    RIGHT(Component.RIGHT_ALIGNMENT);
    public float getValue(){
      return fValue;
    }
    private final float fValue;
    private AlignX(float aValue){
      fValue = aValue;
    }
  }
  
  public static void alignAllY(Container aContainer, UiUtil.AlignY aAlignment){
    java.util.List components = Arrays.asList( aContainer.getComponents() );
    Iterator compsIter = components.iterator();
    while ( compsIter.hasNext() ) {
      JComponent comp = (JComponent)compsIter.next();
      comp.setAlignmentY( aAlignment.getValue() );
    }
  }

  public enum AlignY {
    TOP(Component.TOP_ALIGNMENT),
    CENTER(Component.CENTER_ALIGNMENT),
    BOTTOM(Component.BOTTOM_ALIGNMENT);
    float getValue(){
      return fValue;
    }
    private final float fValue;
    private AlignY( float aValue){
      fValue = aValue;
    }
  }
  
  public static void noDefaultButton(JRootPane aRootPane){
    aRootPane.setDefaultButton(null);
  }
  
  public static ImageIcon createImageIcon(String aPath, String aDescription, Class aClass) {
    ImageIcon result = null;
    URL imageURL = aClass.getResource(aPath); 
    if (imageURL != null) {
      result = new ImageIcon(imageURL, aDescription);
    } 
    return result;
  }
  
  private static void setSizes(java.util.List aComponents, Dimension aDimension){
    Iterator compsIter = aComponents.iterator();      
    while ( compsIter.hasNext() ) {
      JComponent comp = (JComponent) compsIter.next();
      comp.setPreferredSize( (Dimension)aDimension.clone() );
      comp.setMaximumSize( (Dimension)aDimension.clone() );
    }
  }

  private static Dimension calcDimensionFromPercent(Dimension aSourceDimension, int aPercentWidth, int aPercentHeight){
    int width = aSourceDimension.width * aPercentWidth/100;
    int height = aSourceDimension.height * aPercentHeight/100;
    return new Dimension(width, height);
  }
}