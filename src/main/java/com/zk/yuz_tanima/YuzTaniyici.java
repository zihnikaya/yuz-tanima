package com.zk.yuz_tanima;

import java.nio.IntBuffer;
import java.util.List;
import java.util.ListIterator;

import com.zk.App;
import com.zk.kullanici.Kullanici;
import com.zk.kullanici.KullaniciDAO;
import com.zk.yuz_bul.Yuz;
import com.zk.yuz_bul.YuzDAO;

import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class YuzTaniyici {
    public static void tani() {
    	Mat testYuz = imdecode(new Mat(App.matOfByteArr), CV_LOAD_IMAGE_UNCHANGED);
        
        KullaniciDAO kulDAO = new KullaniciDAO();
        
        YuzDAO yuzDAO = new YuzDAO();        
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
    	
        FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        faceRecognizer.train(goruntuler, labels);
        int predictedLabel = faceRecognizer.predict(testYuz);
        
        Kullanici kul = kulDAO.idDenBul(predictedLabel);
        App.tanimaSonucu.setText("Merhaba "+kul.adAl()+" "+kul.soyadAl());
        App.taniBut.setVisible(false);
        App.taniBut.removeAll();
        App.sinGirButGoster();
    }
}
