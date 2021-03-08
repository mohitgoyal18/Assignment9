import java.sql.*;
public class DbConnection {

	public static Connection getConnection() {
		
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection(url,"mohit","mohit");
			return con;
		}
		catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		}
		catch (SQLException e) {
				System.out.println("Connection not Established");
			}
     return null;
	}

}