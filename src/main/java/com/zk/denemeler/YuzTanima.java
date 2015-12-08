package com.zk.denemeler;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;

import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciDAO;
import com.zk.yuz_bul.Yuz;
import com.zk.yuz_bul.YuzDAO;

public class YuzTanima {

	public static void main(String[] args) {
    	Mat testImage = imread("src/main/resources/zk_yuz.jpg", CV_LOAD_IMAGE_GRAYSCALE);
        System.out.println(testImage.arraySize());
        
        KullaniciDAO kulDAO = new KullaniciDAO();
        List<Kullanici> kullar = kulDAO.bul();
        ListIterator<Kullanici> kulIter = kullar.listIterator();
        
        YuzDAO yuzDAO = new YuzDAO(kulIter.next());        
        List<Yuz> yuzler = yuzDAO.bul();
    	ListIterator<Yuz> yuzlerIter = yuzler.listIterator();
    	
        MatVector goruntuler = new MatVector(yuzDAO.topYuz());

        Mat labels = new Mat(yuzDAO.topYuz(),1,CV_32SC1);
        IntBuffer labelsBuf = labels.getIntBuffer();
        
    	int sayac=0;
    	while(yuzlerIter.hasNext()){
    		Yuz yuz = yuzlerIter.next();
    		Mat mat = imdecode(new Mat(yuz.goruntuAl()), CV_LOAD_IMAGE_UNCHANGED);
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
