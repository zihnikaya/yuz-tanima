package com.zk.denemeler;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.zk.exception.InvalidInputException;
import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciDAO;
import com.zk.yuz_bul.YuzDAO;

public class BufImg {
	
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 		
	}	
	
	public static byte[] toBufferedImage(Mat matrix) {		
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
		byte [] buffer = new byte[bufferSize];
		matrix.get(0,0,buffer);		
		BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
		
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);  
		
		return targetPixels;
	}	

	public static void main(String[] args) throws InvalidInputException {
        YuzDAO yuzDAO = new YuzDAO();
        byte[] imgBlob  = yuzDAO.bul(36);
        System.out.println("Blob:" + Arrays.toString(imgBlob));
        Mat img = Imgproc.imdecode(data);
		Mat imgRgb  = new Mat();
		Mat imgRgb1 = new Mat();
        Size boyutlar = new Size(10,10);
		Imgproc.resize( imgRgb, imgRgb1, boyutlar);  
        System.out.println(imgRgb1.dump());
		Mat imgGray = new Mat();
    	Imgproc.cvtColor(imgRgb1, imgGray, Imgproc.COLOR_RGB2GRAY);  
        System.out.println(imgGray.dump());
        //BufferedImage bufImg = toBufferedImage(imgGray);
		//System.out.println("gör data:"+Arrays.toString(targetPixels));
        System.out.println(Arrays.toString(toBufferedImage(imgGray)));
        System.out.println("blob"+Arrays.toString(imgBlob));
        */
	}

}
