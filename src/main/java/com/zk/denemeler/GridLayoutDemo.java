package com.zk.denemeler;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;

public class GridLayoutDemo extends Frame {

	public GridLayoutDemo(){
		add(new Button("Bir"));
		add(new Button("İki"));
		add(new Button("Üç"));
		add(new Button("Dört"));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GridLayoutDemo grid = new GridLayoutDemo();
		// 2 satır 5 kolonlu grid yap
		grid.setLayout(new GridLayout(2,5));
		grid.setSize(300,200);
		grid.setVisible(true);
		
	}

}
