package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	java.sql.Connection conn = null;

	public Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/LIBRARY";
		String username = "root";
		String password = "9937102734";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to database..");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect to the database!!", e);
		}

		return conn;
	}

}
