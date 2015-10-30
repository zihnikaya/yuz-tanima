package com.zk.denemeler;

import java.io.File;

import static org.bytedeco.javacpp.opencv_core.CvType.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.Scalar.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class YeginlikDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Mat img = new Mat(5, 10, CV_8UC1, new Scalar(0));
		Mat img = imread("src/main/resources/att_faces/s1/1.pgm", CV_LOAD_IMAGE_GRAYSCALE);
		Mat yeniImg = new Mat();
		yeniImg.create(img.rows(),img.cols(),CV_8UC1);
		System.out.println(img.get);
	}

}
