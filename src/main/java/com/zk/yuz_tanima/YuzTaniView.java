package com.zk.yuz_tanima;

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

import com.zk.util.KameraPanel;
import com.zk.kullanici.Kullanici;
import com.zk.util.Processor;
import com.zk.util.ui.StandardDialog;

public class YuzTaniView extends JFrame {
	
	private StandardDialog standardDialog;
	private JButton girisButton;
	
	private JPanel buttonlariAl() {
	    JPanel sonuc = new JPanel();

	    girisButton = new JButton("Giriş");
	    girisButton.addActionListener(new YuzTaniController());
	    sonuc.add(girisButton);

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
