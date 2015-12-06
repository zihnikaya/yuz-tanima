package com.zk.denemeler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImgToBufImg {

	public static void main(String[] args) {
		try {			
			byte[] imageInByte;
			BufferedImage originalImage = ImageIO.read(new File("yuz_goruntu.jpg"));

			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();

			// convert byte array back to BufferedImage
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			
			ImageIO.write(bImageFromConvert, "jpg", new File(
					"yuz_goruntu_1.jpg"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	    	    
	}
	
	public static BufferedImage toBufferedImg(byte[] yuz) {		
		  ColorModel cm=ColorModel.getRGBdefault();
		  MemoryImageSource imageSource=new MemoryImageSource(100,100,cm,yuz,0,100);
		  Image img=Toolkit.getDefaultToolkit().createImage(imageSource);
		  BufferedImage bufImg=new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
	      Graphics g = bufImg.createGraphics();
		  g.drawImage(img, 0, 0, null);
		  g.dispose();
	    try {
			ImageIO.write(bufImg, "png", new File("a.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		  return bufImg;
		
	}	

}
