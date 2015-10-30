package com.zk.denemeler;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageShow {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	
	public static void  main(String[] args) {
		Mat img = Imgcodecs.imread("src/main/resources/lena.jpg");
		BufferedImage bImg = matToBufferedImage(img);
		displayImage(bImg);
	}
	
    public static void displayImage(Image img2){   
	    //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
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
