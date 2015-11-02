package com.zk.denemeler;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class KovMatris {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mat A = new Mat(new Size(3,5),CvType.CV_32FC1);
		//double[] data = {90,60,90,90,90,30,60,60,60,60,60,90,30,30,30};
		double[] data = {90,60,90,90,90,30,60,60,60,60,60,90,30,30,30};
		A.put(0, 0, data);
		System.out.println(A.dump());
		Mat kov = new Mat(new Size(3,3),CvType.CV_32FC1);
		Mat ort = new Mat(new Size(3,3),CvType.CV_32FC1);
		Core.calcCovarMatrix(A,kov,ort,Core.COVAR_NORMAL | Core.COVAR_ROWS);
		System.out.println(kov.dump());				
	}

}
