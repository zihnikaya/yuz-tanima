package com.zk.denemeler;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageObserverDemo extends Box{

    Image bi;
    Image bi2;
    Dimension d = new Dimension(300,200);

    public ImageObserverDemo() {
        super(BoxLayout.Y_AXIS);        
        try {
            bi = ImageIO.read(new File("src/main/resources/yuz_goruntu.jpg"));
            bi = bi.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
            bi2 = new BufferedImage(bi.getWidth(this), bi.getHeight(this), BufferedImage.TYPE_INT_ARGB);
            bi2.getGraphics().drawImage(bi, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean imageUpdate(final Image img, int infoflags, int x, int y,
            int width, int height) {

        boolean result = true;
        if((infoflags | ImageObserver.FRAMEBITS) == ImageObserver.FRAMEBITS){
            result = false;
        } else if((infoflags | ImageObserver.ALLBITS) == ImageObserver.ALLBITS){
            result = false;
        }

        if(result){
            System.out.println("Image incomplete");
        } else{
            System.out.println("Complete");
        }

        return result;
    }

    @Override
    public Dimension getPreferredSize(){
        return d;
    }

    public static void main(String[] args){
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ImageObserverDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}