package com.zk.yuz_tanima;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.bytedeco.javacpp.BytePointer;
import org.opencv.core.CvType;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciDAO;
import com.zk.yuz_bul.Yuz;
import com.zk.yuz_bul.YuzDAO;

import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * I couldn't find any tutorial on how to perform face recognition using OpenCV and Java,
 * so I decided to share a viable solution here. The solution is very inefficient in its
 * current form as the training model is built at each run, however it shows what's needed
 * to make it work.
 *
 * The class below takes two arguments: The path to the directory containing the training
 * faces and the path to the image you want to classify. Not that all images has to be of
 * the same size and that the faces already has to be cropped out of their original images
 * (Take a look here http://fivedots.coe.psu.ac.th/~ad/jg/nui07/index.html if you haven't
 * done the face detection yet).
 *
 * For the simplicity of this post, the class also requires that the training images have
 * filename format: <label>-rest_of_filename.png. For example:
 *
 * 1-jon_doe_1.png
 * 1-jon_doe_2.png
 * 2-jane_doe_1.png
 * 2-jane_doe_2.png
 * ...and so on.
 *
 * Source: http://pcbje.com/2012/12/doing-face-recognition-with-javacv/
 *
 * @author Petter Chris
 * tian Bjelland
 */
public class YuzTaniyici {
    public static void tani() {
        
    	Mat testImage = imread("src/main/resources/yuz_goruntu.jpg", CV_LOAD_IMAGE_GRAYSCALE);
        
        MatVector goruntuler = new MatVector();

        Mat labels = new Mat(1, CV_32SC1);
        IntBuffer labelsBuf = labels.getIntBuffer();

        KullaniciDAO kulDAO = new KullaniciDAO();
        List<Kullanici> kullar = kulDAO.bul();
        ListIterator<Kullanici> kulIter = kullar.listIterator();
        YuzDAO yuzDAO = new YuzDAO(kulIter.next());
        
        List<Yuz> yuzler = yuzDAO.bul();
        ListIterator<Yuz> yuzlerIter = yuzler.listIterator();
    	int sayac=0;
    	while(yuzlerIter.hasNext()){
    		Yuz yuz = yuzlerIter.next();
    		System.out.println("to string:"+yuz.goruntuAl().toString());
    		System.out.println("uzunluk:"+yuz.goruntuAl().length);
    		for(int i=0; i< yuz.goruntuAl().length; i++){
    			//System.out.print(i+":"+yuz.goruntuAl()[i]+',');
    		}
    		//System.out.println(yuz.kulIdAl().toString()+'-'+yuz.idAl().toString());
    		//BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
    		//System.out.println(goruntu);
    		//goruntuler.put(sayac, goruntu);
    		labelsBuf.put(sayac,yuz.kulIdAl());
    		sayac++;
        }
    	
        /* 
        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);

            int label = Integer.parseInt(image.getName().split("\\-")[0]);

            images.put(counter, img);

            labelsBuf.put(counter, label);

            counter++;
        }
	    */
        //FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()

        faceRecognizer.train(goruntuler, labels);

        int predictedLabel = faceRecognizer.predict(testImage);

        System.out.println("Predicted label: " + predictedLabel);
    }
}
