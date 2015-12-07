package com.zk.denemeler;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.opencv.core.MatOfInt;

import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciDAO;
import com.zk.yuz_bul.Yuz;
import com.zk.yuz_bul.YuzDAO;

public class YuzTanima {

	public static void main(String[] args) {
    	Mat testImage = imread("src/main/resources/zk_yuz.jpg", CV_LOAD_IMAGE_GRAYSCALE);
        
        MatVector goruntuler = new MatVector();

        Mat labels = new Mat(15,1,CV_32SC1);
        System.out.println("labels:"+labels);
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
    		
    		System.out.println(Arrays.toString(yuz.goruntuAl()));
    		System.out.println(yuz.goruntuAl().length);
    		
    		Mat img = new Mat(yuz.goruntuAl());
    		
    		Mat matDec = new Mat();
    		imdecode(new MatOfInt(yuz.goruntuAl()), 0, matDec);
    		
    		Mat mat = new Mat();
            mat.put(img);
    		
    		goruntuler.put(sayac, mat);
    		labelsBuf.put(sayac, yuz.kulIdAl());
    		sayac++;
        }
    	
        //FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()

        faceRecognizer.train(goruntuler, labels);

        int predictedLabel = faceRecognizer.predict(testImage);

        System.out.println("Predicted label: " + predictedLabel);    

	}

}
