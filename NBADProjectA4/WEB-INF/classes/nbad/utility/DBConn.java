package nbad.utility;

import java.sql.DriverManager;
import java.sql.SQLException;
import nbad.constants.Strings;



public class DBConn {

	public static java.sql.Connection DBConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		java.sql.Connection conn = null;
		conn = DriverManager.getConnection(Strings.CONNECTION_PARAMS, Strings.DB_USERNAME,Strings.DB_PASSWORD);
		
		return conn;
		
		
	}
}
