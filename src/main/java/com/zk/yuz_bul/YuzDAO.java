package com.zk.yuz_bul;

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
import java.util.Map;

import com.zk.kullanici.Kullanici;
import com.zk.util.Util;

public final class YuzDAO {
	
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/yuz_tanima";

   static final String USER = "root";
   static final String PASS = "";

   Connection conn = null;
   Statement stmt = null;   
	   
   private static final Map<String, Yuz> table = new LinkedHashMap<>();
   private static final String NULL = "NULL";
   private final static Charset ENCODING = StandardCharsets.UTF_8;	
   
   private Kullanici kullanici;
  
   public YuzDAO(){
	   
   }
   public YuzDAO(Kullanici kullanici){
	    this.kullanici = kullanici;
	    //bul();
   }  

  List<Yuz> list() {    
	  //System.out.println(table.values());
	  kulIddenBul();
	  List<Yuz> result = new ArrayList<>(table.values());
	  //System.out.println("Array list:"+result);
	  Collections.sort(result);
	  //System.out.println("Yüz:"+result);
	  return result;
  }  
  
  public void ekle(Yuz yuz) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);	      
	      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO yuz (kullanici_id, goruntu) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
	      pstmt.setString(1, this.kullanici.idAl());
	      pstmt.setBytes(2, yuz.goruntuAl());
	      pstmt.executeUpdate();
	      
	      ResultSet keys = pstmt.getGeneratedKeys(); 
	      keys.next();  
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

  /** Change an existing {@link Kullanici}. */
  public void sil(Yuz yuz) {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM yuz WHERE id=?");
	      pstmt.setInt(1, yuz.idAl());
	      pstmt.executeUpdate();
	      table.clear();
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
  
  public void kulIddenBul() {
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, kullanici_id, goruntu FROM yuz WHERE kullanici_id='"+this.kullanici.idAl()+"' ORDER BY id DESC";
	      ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	    	 int id = rs.getInt("id");
	    	 String kullanici_id = rs.getString("kullanici_id");
		     byte[] goruntu = rs.getBytes("goruntu");
	    	 Yuz yuz = new Yuz(goruntu);
		     yuz.idVer(id);
	    	 table.put(yuz.idAl().toString(), yuz);
	      }
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	   }catch(Exception e){
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
  
  public List<Yuz> bul() {	  
	  List<Yuz> yuzler = new ArrayList<Yuz>(); 
	  try{
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, kullanici_id, goruntu FROM yuz ORDER BY id DESC";
	      ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	    	 int id = rs.getInt("id");
	    	 int kulId = rs.getInt("kullanici_id");
		     byte[] goruntu = rs.getBytes("goruntu");
		     Yuz yuz=new Yuz();
		     yuz.goruntuVer(goruntu);
		     yuz.idVer(id);
		     yuz.kulIdVer(kulId);
		     yuzler.add(yuz);
	      }
	      rs.close();
	      stmt.close();
	      conn.close();	      
	   }catch(SQLException se){
	   }catch(Exception e){
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
	  return yuzler;	   
	}
  
}
