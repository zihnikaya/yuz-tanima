package com.zk.yuz;

public class Yuz {
	 
	private Integer id;
	private byte[] goruntu;
 
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
 
	public byte[] goruntuAl() {
		return this.goruntu;
	}
 
	public void goruntuVer(byte[] goruntu) {
		this.goruntu = goruntu;
	}
 
}