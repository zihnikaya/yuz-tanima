package com.zk.denemeler;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;
public class Ozyuz {
	  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	public static void main(String[] args) {
        String yuzDizini = "src/main/resources/att_faces";

        FilenameFilter dosyaFiltrele = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        
        File f = new File(yuzDizini);        
        Vector<File> yuzler=new Vector<File>();
		if(f.isDirectory()){
			String[] s = f.list();
	        for(int i=0; i<s.length; i++){
	        	File f1 = new File(yuzDizini + '/' + s[i]);	        	
	        	if(f1.isDirectory()){
	        		File[] f2 = f1.listFiles(dosyaFiltrele);
	        		for(File f3 : f2){
	        			yuzler.add(f3.getAbsoluteFile());
	        		}
	        	}
	        }
		}
		System.out.println(yuzler.size());
		ortYuzBul(yuzler);
		
        //System.out.println(testGoruntu.toString());
	}
	
	public static void ortYuzBul(Vector<File> yuzler){
		Mat topYuz = new Mat(new Size(92,112), CvType.CV_16UC1);
		Mat ortYuz = new Mat(new Size(92,112), CvType.CV_16UC1);
		for (File yuz : yuzler) {
			Mat img = Imgcodecs.imread(yuz.getAbsolutePath(),0);
			img.convertTo(img,CvType.CV_16UC1);
			Core.add(img,topYuz,topYuz);
			System.out.println("--------------------------------");
		}
		Core.divide(topYuz,Scalar.all(yuzler.size()),ortYuz);
		
		System.out.println(topYuz.dump());
		System.out.println(ortYuz.dump());
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
