/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipasswordserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Monse
 */
public class ConnectionDB {
  
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://";
         static final String USUARIO = "root";
         static final String PASS = "2580";
         static final String BD = "mipassword";
         static final String HOST = "localhost";
         
	

	public static Connection conectar( ) {
	    Connection res = null;
	    try {
	      // Registrar JDBC driver
	      Class.forName(JDBC_DRIVER);
	      String url = DB_URL + HOST + '/' + BD;
	      res = DriverManager.getConnection(url, USUARIO, PASS);
	    } catch (SQLException sqe) {
	      sqe.printStackTrace();
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    return res;
	  }
  
}
