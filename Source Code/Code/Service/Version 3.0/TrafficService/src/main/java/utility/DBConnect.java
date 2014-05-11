package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	/**
	 * Get DB Connection
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConnect() throws ClassNotFoundException, SQLException {
		String connectionURL = GlobalValue.getConnectionURL();
		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(connectionURL,
				GlobalValue.getDbUser(), GlobalValue.getDbPasswork());
		return connection;
	}
}
