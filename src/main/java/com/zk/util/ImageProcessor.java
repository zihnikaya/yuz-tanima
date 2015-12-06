package com.zk.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;

public class ImageProcessor  {
	
	public BufferedImage toBufferedImage(Mat matrix){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
		byte [] buffer = new byte[bufferSize];
		matrix.get(0,0,buffer); // get all the pixels
		BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);  
		//System.out.println("gör data:"+Arrays.toString(targetPixels));
		return image;
	}
	
	public static byte[] toByte(Mat matrix) {		
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
		byte [] buffer = new byte[bufferSize];
		matrix.get(0,0,buffer);		
		BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
		
		final byte[] targetPixels1 = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels1, 0, buffer.length);  
		
		return targetPixels1;
	}	
	
	public static BufferedImage toBufferedImg(byte[] yuz) {		
		InputStream in = new ByteArrayInputStream(yuz);
		BufferedImage bufImg=new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
		try {
			bufImg = ImageIO.read(in);
			//ImageIO.write(bufImg, "jpg", new File("yuz_goruntu_1.jpg"));					

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufImg;		
	}	

}