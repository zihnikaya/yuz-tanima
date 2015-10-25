package com.zk.denemeler;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

import java.io.File;
import java.io.FilenameFilter;

public class YuzTanima {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String trainingDir = "srs/main/resources/att_faces";
        Mat testImage = imread("src/main/resources/att_faces/s1/1.pgm", CV_LOAD_IMAGE_GRAYSCALE);

        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);
		//System.out.println(testGoruntu.toString());
	}

}
