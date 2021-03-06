package com.zk.yuz_tanima;

import com.zk.kullanici.Kullanici;
import com.zk.main.MainWindow;
import com.zk.util.Util;
import com.zk.yuz_bul.Yuz;

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

public final class YuzTanimaDAO {
	
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.5.20.12/yuz_tanima";

   static final String USER = "root";
   static final String PASS = "";

   Connection conn = null;
   Statement stmt = null;   
	   
   private static final Map<String, Yuz> table = new LinkedHashMap<>();
   private static final String NULL = "NULL";
   private final static Charset ENCODING = StandardCharsets.UTF_8;	
   
   private Kullanici kullanici;
  
   public YuzTanimaDAO(Kullanici kullanici){
	    this.kullanici=kullanici;
   }  

   List<Yuz> list() {    
	  System.out.println(this.kullanici);
	  bul();
	  List<Yuz> result = new ArrayList<>(table.values());
	  return result;
   }  
  
   public void bul() {
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      
	      //STEP 4: Execute a query
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, kullanici_id, goruntu FROM yuz ORDER BY kullanici_id";
	      ResultSet rs = stmt.executeQuery(sql);
	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name  	         
	    	 int id = rs.getInt("id");
	    	 String kullanici_id = rs.getString("kullanici_id");
		     byte[] goruntu = rs.getBytes("goruntu");
	    	 Yuz yuz = new Yuz(goruntu);
		     yuz.idVer(id);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      //se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      //e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
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
