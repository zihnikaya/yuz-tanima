package com.zk.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedCharacterIterator;
import java.util.ResourceBundle.Control;
 


import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import zk.tez.kamera.KameraView;
 
public class Kamera {
 
    static int control = 0;
    private static CascadeClassifier faceDetector;
 
    public static void ac() {
        // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        JPanel panel = new JPanel();
        panel.setSize(500, 500);
 
        // for drawing image to jpanel
        KameraPanel KameraView = new KameraPanel();
 
        frame.setContentPane(KameraView);
        frame.setVisible(true);
 
        // we added button to frame for changing color to gray
        JButton btnGray = new JButton();
        btnGray.setText("RGB");
        frame.add(btnGray);
 
        // this work when buttton click
        btnGray.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                control++;
            }
        });
 
        // take image from webcam with small time
        VideoCapture webCam = new VideoCapture(0);
 
        Mat webCamImage = new Mat();
        Mat rgbImage = new Mat();
 
        faceDetector = new CascadeClassifier(new File("src/resources/lbpcascades/lbpcascade_frontalface.xml").getAbsolutePath());
        MatOfRect faceDetections = new MatOfRect();
 
        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));
        webCam.read(webCamImage);
        if (webCam.isOpened()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            while (true) {
                if (!webCamImage.empty()) {
 
                    try {
                        webCam.read(webCamImage);
                        // Draw a bounding box around each face.
                        faceDetector.detectMultiScale(webCamImage,
                                faceDetections);
                        for (Rect rect : faceDetections.toArray()) {
                        	Imgproc.rectangle(webCamImage, new Point(rect.x,
                                    rect.y), new Point(rect.x + rect.width,
                                    rect.y + rect.height),
                                    new Scalar(0, 255, 0));
                        }
 
                        if (control == 0) {
                            KameraView.setFace(matToBufferedImage(webCamImage));
                        } else {
                            Imgproc.cvtColor(webCamImage, rgbImage,
                                    Imgproc.COLOR_RGBA2GRAY);
                            KameraView.setFace(matToBufferedImage(rgbImage));
                        }
                        KameraView.repaint();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
 
                    }
 
                }
            }
        }
 
    }
 
    public static BufferedImage matToBufferedImage(Mat mat) throws IOException {
        MatOfByte bytemat = new MatOfByte();
 
        Imgcodecs.imencode(".jpg", mat, bytemat);
 
        byte[] bytes = bytemat.toArray();
 
        InputStream in = new ByteArrayInputStream(bytes);
 
        BufferedImage img = ImageIO.read(in);
        return img;
    }
 
}