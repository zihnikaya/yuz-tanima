package com.zk.denemeler;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

public class OrtMatris {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mat matris = new Mat(new Size(2,2), CvType.CV_16SC1, new Scalar(0));
		matris.put(0, 0, 1);
		matris.put(0, 1, 2);
		matris.put(1, 0, 3);
		matris.put(1, 1, 4);
		System.out.println(matris.dump());
		Mat matris1 = new Mat(new Size(2,2), CvType.CV_16SC1, new Scalar(0));
		matris1.put(0, 0, 5);
		matris1.put(0, 1, 6);
		matris1.put(1, 0, 7);
		matris1.put(1, 1, 8);
		System.out.println(matris1.dump());
		Mat topMatris = new Mat(new Size(2,2), CvType.CV_16SC1, new Scalar(0));		
		Core.add(matris, topMatris, topMatris);
		System.out.println(topMatris.dump());
		Mat ortMatris = new Mat(new Size(2,2), CvType.CV_16SC1, new Scalar(0));		
		Core.divide(topMatris,Scalar.all(3),ortMatris);
		System.out.println(ortMatris.dump());
	}
}
