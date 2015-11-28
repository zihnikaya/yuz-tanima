package com.zk.yuz_bul;

public class Yuz implements Comparable<Yuz> {
	 
	private Integer id;
	private byte[] goruntu;
	private int kulId;
 
	public Yuz() {		
	}
 
	public Yuz(byte[] goruntu) {
		this.goruntu = goruntu;
	}
 
	public Integer idAl() {
		return this.id;
	}
 
	public void idVer(Integer id) {
		this.id = id;
	}
	
	public Integer kulIdAl() {
		return this.kulId;
	}
 
	public void kulIdVer(Integer id) {
		this.kulId = id;
	}	
 
	public byte[] goruntuAl() {
		return this.goruntu;
	}
 
	public void goruntuVer(byte[] goruntu) {
		this.goruntu = goruntu;
	}

	@Override
	public int compareTo(Yuz o) {
		// TODO Auto-generated method stub
		return 0;
	}
 
}