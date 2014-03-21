package utility;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
	public Connection getConnect() throws Exception {
		try {
			String realPath = this.getClass().getClassLoader().getResource("")
					.getPath().replace("classes/", "config.properties");
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(realPath);
			prop.load(in);

			String connectionURL = prop.getProperty("connectionURL").trim();
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL, prop
					.getProperty("dbUser").trim(),
					prop.getProperty("dbPassword").trim());
			return connection;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}
}
