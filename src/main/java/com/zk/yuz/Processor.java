package com.zk.yuz;

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

class Processor {  
    private CascadeClassifier face_cascade;  
    // Create a constructor method  
    public Processor(){  
         face_cascade=new CascadeClassifier("C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");  
         if(face_cascade.empty())  
         {  
              System.out.println("--(!)Error loading A\n");  
               return;  
         }  
         else  
         {  
                  System.out.println("Face classifier loooaaaaaded up");  
         }  
    }  
    public Mat detect(Mat inputframe){  
         Mat mRgba=new Mat();  
         Mat mGrey=new Mat();  
         MatOfRect faces = new MatOfRect();  
         inputframe.copyTo(mRgba);  
         inputframe.copyTo(mGrey);  
         Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);  
         Imgproc.equalizeHist( mGrey, mGrey );  
         face_cascade.detectMultiScale(mGrey, faces);  
         System.out.println(String.format("Detected %s faces", faces.toArray().length));  
         for(Rect rect:faces.toArray())  
         {  
              Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );  
              Imgproc.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
         }  
         return mRgba;  
    }  
}
