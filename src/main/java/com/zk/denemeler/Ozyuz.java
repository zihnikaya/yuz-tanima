package com.zk.denemeler;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;


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
		Mat ortYuz = new Mat(112, 92, CvType.CV_8U);
		for (File yuz : yuzler) {
			Mat img = Imgcodecs.imread(yuz.getAbsolutePath(),0);
			System.out.println(img.elemSize());
			int toplamByte = (int )(img.total()*img.elemSize());
			byte[] pikDeg = new byte[1];
			img.get(1, 1, pikDeg);	
			img.s
			System.out.println(pikDeg.length);
			System.out.println("piksel sayısı:"+92*112*1);
			System.out.println("piksel değeri:"+pikDeg[0]);
			System.out.println("--------------------------------");
		}		
	}
}
