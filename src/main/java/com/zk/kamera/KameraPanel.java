package com.zk.kamera;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.zk.kullanici.Kullanici;
import com.zk.util.ui.StandardDialog;

import com.zk.util.Processor;

public class KameraPanel extends Panel {
	
	private StandardDialog standardDialog;
    private BufferedImage image; 
	
	public void ac(JFrame parent){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		KameraPanel kameraPanel = new KameraPanel();
		kameraPanel.setPreferredSize(new Dimension(600, 400));
		
		Processor processor=new Processor(); 
		
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
	
    public boolean MatToBufferedImage(Mat matBGR){  
        long startTime = System.nanoTime();  
        int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;  
        byte[] sourcePixels = new byte[width * height * channels];  
        matBGR.get(0, 0, sourcePixels);  
        // create new image and get reference to backing data  
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);  
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();  
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);  
        long endTime = System.nanoTime();  
        System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
        return true;  
   }
   
   public void paintComponent(Graphics g){  
       System.out.println("1"); 
        if (this.image==null) return;  
         g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);  
   }	
}
