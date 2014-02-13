package sample.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	public static Connection DBConnect(){
		Connection con = null;
		try{
		String userName = "root";
	    String pass = "123";
	    String url = "jdbc:mysql://127.0.0.1:3306/cpdb";
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    con = DriverManager.getConnection(url, userName, pass);		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}
	
	
}
