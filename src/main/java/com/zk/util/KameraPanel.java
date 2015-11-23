package com.zk.util;

import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.awt.image.DataBufferByte;  
import javax.swing.*;  
import org.opencv.core.Core;  
import org.opencv.core.Mat;  
import org.opencv.core.MatOfRect;  
import org.opencv.core.Point;  
import org.opencv.core.Rect;  
import org.opencv.core.Scalar;  
import org.opencv.core.Size;  
import org.opencv.videoio.VideoCapture;  
import org.opencv.imgproc.Imgproc;  
import org.opencv.objdetect.CascadeClassifier;  
public class KameraPanel extends JPanel{  
     private static final long serialVersionUID = 1L;  
     private BufferedImage image;  
     // Create a constructor method  
     public KameraPanel(){  
          super();   
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
    	  super.paintComponent(g);   
          if (this.image==null) return;  
          	g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);  
  
     }  
} 