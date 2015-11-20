package com.zk.giris;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.zk.util.Processor;

public class Giris extends Applet {
	
	private BufferedImage image; 	
	
    public void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);	                	
    }
    
    public void start() {
 	   File img = new File("lena.jpg");
 	   try {
 		   this.image = ImageIO.read(img);
 	   } catch (IOException e) {
 		   e.printStackTrace();
 	   }    	
		//repaint();
		
    	Processor processor=new Processor(); 
	    Mat webcam_image=new Mat();  
	    VideoCapture capture =new VideoCapture(0); 
	    if( capture.isOpened() )  {  
	    	while( true ){  
	    		capture.read(webcam_image);  
	    		if( !webcam_image.empty() ) {   
	    			webcam_image=processor.detect(webcam_image);  
	    			MatToBufferedImage(webcam_image);  	    		
	    		}else{   
	    			System.out.println(" --(!) No captured frame -- Break!");   
	    			break;   
	    		}  
	    	}  
        }                                	
    }   
    
    @Override
    public void update(Graphics g) {
    	paint(g);
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
        //repaint(); 
        System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
        return true;  
   }
      
   
   public void paint(Graphics g) {
	   g.drawString("zihni", 10, 20);

	   g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null); 
       if (this.image==null) return;  
       //g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);  
   }   

}
