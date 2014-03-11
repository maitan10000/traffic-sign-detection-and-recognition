package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import utility.DBConnect;

public class BaseDAO {
	public static Connection getConnect() throws Exception {
		return new DBConnect().getConnect();
	}

}
