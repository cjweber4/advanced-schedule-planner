import java.sql.Connection;
import java.sql.DriverManager;


/** Used to connect to the coursesDB database */
public class GetConnection {

	public static Connection getConnection() throws Exception {
		try {
			String jdbcDriver = "com.mysql.jdbc.Driver";
			Class.forName(jdbcDriver).newInstance();
			String url = "jdbc:mysql://localhost:3306/coursesdb";
			Connection conn = DriverManager.getConnection(url, "root", "Redwing1");
			System.out.println("connected");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		} 
		return null;
	}
}
