package com.zk.denemeler;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

public class OrtGoruntu {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mat yuz1 = Imgcodecs.imread("src/main/resources/1.png",0);
		Mat yuz2 = Imgcodecs.imread("src/main/resources/2.png",0);
		yuz1.convertTo(yuz1, CvType.CV_16UC1);
		yuz2.convertTo(yuz2, CvType.CV_16UC1);
		Mat topYuz = new Mat(new Size(92,112), CvType.CV_16UC1);
		Core.add(yuz1,yuz2,topYuz);
		System.out.println(topYuz.dump());
		Mat ortYuz = new Mat(new Size(92,112), CvType.CV_16UC1);
		Core.divide(topYuz,Scalar.all(2),ortYuz);
		ortYuz.convertTo(ortYuz,CvType.CV_8UC1);
		BufferedImage bImg = matToBufferedImage(ortYuz);
		displayImage(bImg);
	}
	
    public static void displayImage(Image img2){   
	    ImageIcon icon=new ImageIcon(img2);
	    JFrame frame=new JFrame();
	    frame.setLayout(new FlowLayout());        
	    frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
	    JLabel lbl=new JLabel();
	    lbl.setIcon(icon);
	    frame.add(lbl);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	
	public static BufferedImage matToBufferedImage(Mat matrix) {
	    int cols = matrix.cols();
	    int rows = matrix.rows();
	    int elemSize = (int)matrix.elemSize();
	    byte[] data = new byte[cols * rows * elemSize];
	    int type;

	    matrix.get(0, 0, data);

	    switch (matrix.channels()) {
	        case 1:
	            type = BufferedImage.TYPE_BYTE_GRAY;
	            break;

	        case 3: 
	            type = BufferedImage.TYPE_3BYTE_BGR;

	            // bgr to rgb
	            byte b;
	            for(int i=0; i<data.length; i=i+3) {
	                b = data[i];
	                data[i] = data[i+2];
	                data[i+2] = b;
	            }
	            break;

	        default:
	            return null;
	    }

	    BufferedImage image = new BufferedImage(cols, rows, type);
	    image.getRaster().setDataElements(0, 0, cols, rows, data);

	    return image;
	}	

}
