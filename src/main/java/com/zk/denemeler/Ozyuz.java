package com.zk.denemeler;

import java.io.File;
import java.io.FilenameFilter;

public class Algoritma {

	public static void main(String[] args) {
        String dizinAdi = "src/main/resources/att_faces";

        File fl = new File(dizinAdi);

        FilenameFilter dosyaFiltrele = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        
        File[] yuzler=null;
		if(fl.isDirectory()){
			String[] s = fl.list();
	        for(int i=0; i<s.length; i++){
	        	File f = new File(dizinAdi+'/'+s[i]);	        	
	        	if(f.isDirectory()){
	        		System.out.println(f);
	        		yuzler = f.listFiles(dosyaFiltrele);
	        		System.out.println(yuzler);
	        	}
	        }
		}
		
		for (File yuz : yuzler) {
			System.out.println(yuz.toString());
		}
			
        //System.out.println(testGoruntu.toString());
	}

}
