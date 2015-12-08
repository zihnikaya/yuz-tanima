package com.zk.kullanici;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import com.zk.kullanici.Kullanici;
import com.zk.main.MainWindow;
import com.zk.util.Util;

public final class KullaniciDAO {
	
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/yuz_tanima?useUnicode=true&characterEncoding=UTF-8";

   static final String USER = "root";
   static final String PASS = "";

   Connection conn = null;
   Statement stmt = null;   
	   
  private static final Map<String, Kullanici> fTable = new LinkedHashMap<>();
  private static final String NULL = "NULL";
  private final static Charset ENCODING = StandardCharsets.UTF_8;	
  
  public void KullaniciDao(){
	  bul();   
  }  

  List<Kullanici> list() {    
	bul();
	List<Kullanici> result = new ArrayList<>(fTable.values());
    Collections.sort(result);
    return result;
  }  
  
  public void ekle(Kullanici aKullanici) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      
	      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO kullanici (ogrenci_no, ad, soyad,bolum) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
	      pstmt.setString(1, aKullanici.ogrNoAl());
	      pstmt.setString(2, aKullanici.adAl());
	      pstmt.setString(3, aKullanici.soyadAl());
	      pstmt.setString(4, aKullanici.bolumAl());
	      pstmt.executeUpdate();
	      
	      ResultSet keys = pstmt.getGeneratedKeys(); 
	      keys.next();  
	      bul(keys.getString(1));
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	}  

  void degistir(Kullanici aKullanici) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE kullanici SET ogrenci_no=?, ad=?, soyad=?, bolum=? WHERE id=?");
	      pstmt.setString(1, aKullanici.ogrNoAl());
	      pstmt.setString(2, aKullanici.adAl());
	      pstmt.setString(3, aKullanici.soyadAl());
	      pstmt.setString(4, aKullanici.bolumAl());
	      pstmt.setString(5, aKullanici.idAl());
	      pstmt.executeUpdate();	      
	      bul();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
  }

  void sil(Kullanici aKullanici) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM kullanici WHERE id=?");
	      pstmt.setString(1, aKullanici.idAl());
	      pstmt.executeUpdate();
	      fTable.clear();
	      bul();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
  }  
  
  public List<Kullanici> bul() {
	  List<Kullanici> kullar=null; 
	  try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, ogrenci_no, ad, soyad,bolum FROM kullanici";
	      ResultSet rs = stmt.executeQuery(sql);	      
	      while(rs.next()){
	    	 String id = rs.getString("id");
	    	 String ogrNo = rs.getString("ogrenci_no");
		     String ad = rs.getString("ad");
		     String soyad = rs.getString("soyad");
		     String bolum = rs.getString("bolum");
		     Kullanici kullanici = new Kullanici(id, ogrNo, ad, soyad, bolum);
		     fTable.put(kullanici.idAl(), kullanici);
		     kullar = new ArrayList<Kullanici>(); 
		     kullar.add(kullanici);
	      }
	      Collections.sort(kullar);
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	   return kullar;
	}
  
  public void bul(int id) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, ogrenci_no, ad, soyad, bolum FROM kullanici WHERE id='"+id+"'";
	      ResultSet rs = stmt.executeQuery(sql);
	      rs.next();
    	  String kulId = rs.getString("id");
    	  String ogrNo = rs.getString("ogrenci_no");
	      String ad = rs.getString("ad");
	      String soyad = rs.getString("soyad");
	      String bolum = rs.getString("bolum");
	      Kullanici kullanici = new Kullanici(kulId, ogrNo, ad, soyad, bolum);
		  fTable.put(kullanici.idAl(), kullanici);
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	} 
  
  public Kullanici idDenBul(int id) {
	  Kullanici kullanici=null; 
	  try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, ogrenci_no, ad, soyad, bolum FROM kullanici WHERE id='"+id+"'";
	      ResultSet rs = stmt.executeQuery(sql);
	      rs.next();
	      String kulId = rs.getString("id");
	      String ogrNo = rs.getString("ogrenci_no");
	      String ad = rs.getString("ad");
	      String soyad = rs.getString("soyad");
	      String bolum = rs.getString("bolum");
	      kullanici = new Kullanici(kulId, ogrNo, ad, soyad, bolum);
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	   return kullanici;
	}  
    
  public void bul(String ogrNo) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, ogrenci_no, ad, soyad, bolum FROM kullanici WHERE ogrenci_no='"+ogrNo+"'";
	      ResultSet rs = stmt.executeQuery(sql);
	      rs.next();
	      String id = rs.getString("id");
	      String ad = rs.getString("ad");
	      String soyad = rs.getString("soyad");
	      String bolum = rs.getString("bolum");
	      Kullanici kullanici = new Kullanici(id, ogrNo, ad, soyad, bolum);
		  fTable.put(kullanici.idAl(), kullanici);
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	}   
  
  
  
  private void appendTo(StringBuilder aText, Object aField, String aAppend) {
    if (Util.textAra(Util.format(aField))) {
      aText.append(Util.format(aField));
    }
    else {
      aText.append(NULL);
    }
    aText.append(aAppend);
  }

  private static String maybeNull(String aText) {
    return NULL.equals(aText) ? null : aText;
  }


}
