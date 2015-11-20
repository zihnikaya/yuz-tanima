package com.zk.yuz_bul;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.zk.kullanici.Kullanici;
import com.zk.util.Processor;
import com.zk.util.ui.StandardDialog;

public class YuzBulView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private StandardDialog standardDialog;
	private JButton kaydetButton;
	private Kullanici secilenKullanici;
	
	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		YuzBulView yuzBulView = new YuzBulView(); 
		
		yuzBulView.setSize(800,600);
		yuzBulView.setLayout(new BoxLayout(yuzBulView.getContentPane(),BoxLayout.Y_AXIS));
		
		KameraPanel kameraPanel = new KameraPanel();
		kameraPanel.setPreferredSize(new Dimension(600, 400));
		yuzBulView.add(kameraPanel); 
		
		yuzBulView.add(yuzBulView.buttonlariAl());
		Processor processor=new Processor(); 
		
		yuzBulView.setDefaultCloseOperation(EXIT_ON_CLOSE);
		yuzBulView.setVisible(true);
	    
	    Mat webcam_image=new Mat();  
	    VideoCapture capture =new VideoCapture(0); 
	    if( capture.isOpened())  {  
	    	while( true ){  
	    		capture.read(webcam_image);  
	    		if( !webcam_image.empty() ) {   
	    			webcam_image=processor.detect(webcam_image);  
	    			kameraPanel.MatToBufferedImage(webcam_image);  
	    			kameraPanel.repaint(); 
	    		}else{   
	    			System.out.println(" --(!) No captured frame -- Break!");   
	    			break;   
	    		}  
	    	}  
        }  	            
	}	
	
	private JPanel buttonlariAl() {
	    JPanel sonuc = new JPanel();

	    kaydetButton = new JButton("Kaydet");
	    //kaydetButton.addActionListener(new KameraController(this));
	    sonuc.add(kaydetButton);

	    JButton vazgec = new JButton("Vazgeç");
	    vazgec.addActionListener(new ActionListener() {
	    	@Override public void actionPerformed(ActionEvent arg0) {
	    		kapat();
	    	}
	    });
	    sonuc.add(vazgec);
	    return sonuc;	  
	}	

	void kapat() {
		dispose();
	}	
	
	
}

