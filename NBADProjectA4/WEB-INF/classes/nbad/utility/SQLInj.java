package nbad.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInj {
	public static String executeSQLINJQuery(String whereClause,int mode){
		System.out.println("In executeSQLINJQuery");
		Connection conn  = null;
		StringBuilder sb = new StringBuilder();
		try {
			conn = DBConn.DBConnection();
			Statement stmt = conn.createStatement();
			if(mode == 1){
			String sql = "SELECT UNAME,EMAIL FROM DBTEST.USER WHERE UID="+whereClause;
			
			System.out.println(sql + " from : executeSQLINJQuery");
			ResultSet rs = stmt.executeQuery(sql);
			sb.append("mode 1 \n");
			while(rs.next()){
				sb.append(rs.getString(1));
				sb.append(" ");
				sb.append(rs.getString(2));
				sb.append("\n");
			}
			}
			else if (mode == 2){
				PreparedStatement preparedStatement = conn.prepareStatement("SELECT UNAME,EMAIL FROM DBTEST.USER WHERE UNAME=?");
				preparedStatement.setString(1, whereClause);
				ResultSet rs = preparedStatement.executeQuery();
				
				sb.append("mode 2 \n");
				while(rs.next()){
					sb.append(rs.getString(1));
					sb.append(" ");
					sb.append(rs.getString(2));
					sb.append("\n");
				}
				
			
			}
			stmt.close();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("executeSQLINJQuery : Class not found exception");
			sb.append("ERROR :" + e.getMessage());
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("executeSQLINJQuery : SQL syntax error");
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("executeSQLINJQuery : Unable to close connection");
				sb.append("ERROR :" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return sb.toString();
		
	}
}
