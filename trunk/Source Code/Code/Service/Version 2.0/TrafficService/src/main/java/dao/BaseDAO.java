package dao;

import java.sql.Connection;

import utility.DBConnect;

public class BaseDAO {
	public static Connection getConnect() throws Exception {
		return new DBConnect().getConnect();
	}

}
