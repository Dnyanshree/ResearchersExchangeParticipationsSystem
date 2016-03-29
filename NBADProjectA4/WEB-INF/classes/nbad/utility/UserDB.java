package nbad.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;

import com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException;

import nbad.constants.Strings;
import nbad.model.User;

public class UserDB {
	
	static ArrayList<User> userList;
	static ArrayList<User> uList = new ArrayList<User>();
	PreparedStatement preparedStatement;
	/*
	 * Hard-Coded set of user details (your choice on how to represent) User
	 * getUser(String email) – returns a user object based on the email.
	 * List/Collection<User> getUsers() – returns a set of all the users in the
	 * hardcoded “database”
	 */
	public ArrayList<User> genUser()
	{
		ArrayList<User> aList = new ArrayList<User>();
		Connection conn = null;
		
		try {
			conn = DBConn.DBConnection();
			
			preparedStatement = conn.prepareStatement(Strings.SELECTALL_USERS);
			
			
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while (rs.next())
			{
				User user = new User();
				user.setName(rs.getString(1));
				user.setEmail(rs.getString(2));
				user.setStudies(rs.getInt(4));
				user.setParticipation(rs.getInt(5));
				user.setCoins(rs.getInt(5));
				
				aList.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {

			System.out.println("genUser: Class not found error or SQL query error while fetching user List");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return aList;
	}
	

	
	
	
	
	
	
	
	
	public static void updateUser(User user) {
		/*
		 *  lookup user and update changes.
		 */
		Connection conn = null;
		int result = 0;
		try {
			conn = DBConn.DBConnection();
		
		PreparedStatement preparedStatement = conn.prepareStatement(Strings.UPDATE_USER_ByEMAIL);
		
		preparedStatement.setString(1,user.getName());
		preparedStatement.setInt(2, user.getStudies());
		preparedStatement.setInt(3,user.getParticipation());
		preparedStatement.setInt(4,user.getCoins());
		preparedStatement.setString(5, user.getEmail());
		
		result = preparedStatement.executeUpdate();
		
		if(result > 0)
		{
			System.out.println("updateUser : User details have been updated.");
		}
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("updateUser : Unable to close connection");
				e.printStackTrace();
			}
		}
	
	}
	
	public static User ValidateUser(String username,String password)
	{
		System.out.println("inside validate user");
		
			UserDB userDB = new UserDB();
			uList = userDB.genUser();
		
			
			User user = new User();
			
			System.out.println("ValidateUser : Size of uList : " + uList.size());
			
		for(int i=0;i<uList.size();i++) {
			user = uList.get(i);
			
			
			
			if(user.getEmail().equalsIgnoreCase(username)){
				
				String userPassword = getPasswordForUser(username);
				
				if(password.equalsIgnoreCase(userPassword))
				{
					System.out.println("ValidateUser: The users emails: " + i + " " + user.getEmail());
					System.out.println("ValidateUser : The user's name : " + user.getName());
					return user;
				}
			}
			
			
		}
		return null;
	}
	

	
	
	
	
	private static String getPasswordForUser(String username) {
		String password= "";
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement =  conn.prepareStatement(Strings.SELECT_USER_ByPASSWORD);
			
			preparedStatement.setString(1, username);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while(rs.next())
			{
				password = rs.getString(1);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return password;
	}
	
	
	public static Cookie createCookie(String cookieName, String cookieValue) {
	    Cookie cookie = new Cookie(cookieName, cookieValue);
	    cookie.setPath("/");
	    cookie.setMaxAge(Strings.MAX_AGE_SECONDS);
	    return cookie;
	}


	
	public static User SignUpUser(String name, String email, String password) {
		Connection conn = null;
		String token = null;
		User newUser = new User();
		PreparedStatement preparedStatement = null;
		
		
		System.out.println("Inside SignUp");
		
		
		newUser.setCoins(0);
		newUser.setStudies(0);
		newUser.setParticipation(0);
		newUser.setEmail(email);
		newUser.setName(name);

		/*
		 * insert a record for the user in the table
		 * fetch user information for the user and store it in returnUser
		 */
		
		
		
		try {
			conn = DBConn.DBConnection();
			
			if((token = CreateTempUser(conn,name, email, password)) != null)
			{
			System.out.println("SignUpUser: Temp user creation successful!");
			
			preparedStatement = conn.prepareStatement(Strings.INSERT_NEWUSER);
			
			preparedStatement.setString(1, newUser.getName());
			preparedStatement.setString(2, newUser.getEmail());
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, 0);
			preparedStatement.setInt(5, 0);
			preparedStatement.setInt(6, 0);
			
			int result = preparedStatement.executeUpdate();
			
			System.out.println("SignUpUser : On inserting a new user record \" "
					+ "preparedStatement.executeUpdate() \" "
					+ "returned: " + result);
			
			if(result > 0){
				/*
				 * Temp user created
				 * user inserted into table
				 * sending user an email with token to verify email
				 */
				
				EmailDetail emailDetail = new EmailDetail();
				emailDetail.setEmailTo(newUser.getEmail());
				emailDetail.setEmailFrom(Strings.ADMINEMAIL);
				emailDetail.setMsg("Sample email ");
					String link = "http://" + Strings.SERVERNAME + ":"+Strings.PORTNO +"/NBADProject/UserController?action=activate&token="+token.toString();
				emailDetail.setName("Admin");
				String msgbody = "";
				
				if(MailUtils.sendEmail(emailDetail, link, msgbody,"New User Email verification")){
					System.out.println("SignUpUser: Mail Sent Successfully");
					System.out.println(link);
					
					return newUser;
				}
				else{
					System.out.println("SignUpUser : Issue with sending email");
					return null;
				}
			}else
			{
				System.out.println("SignUpUser : User not created in the table");
				return null;
			}
		}
		else{
			System.out.println("SignUpUser  :Token not generated ");
			return null;
		}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("SignUpUser : Error in inserting new user into table");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
			System.out.println("SignUpUser : sError while closing the connection");
				e.printStackTrace();
			}
		}
		return null;
	}










	private static String CreateTempUser(Connection conn, String name, String email, String password) throws SQLException {
		
		PreparedStatement preparedStatement = conn.prepareStatement(Strings.INSERT_TEMPUSER);
		
		java.util.Date dateStr = new Date();
		java.sql.Date dateDB =  new java.sql.Date(dateStr.getTime());
		
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, password);
		preparedStatement.setDate(4,dateDB);
		UUID token = UUID.randomUUID();
		System.out.println("CreateTempUser : unique id generated : " + token + " converted to String " + token.toString());
		preparedStatement.setString(5, token.toString());
		
		if(preparedStatement.executeUpdate() > 0){
			System.out.println("CreateTempUser : Temp user updated to database");
			return token.toString();
		}else
		{
			System.out.println("CreateTempUser : error in updating tempuser");
			return null;
		}
		
		
	}










	public static boolean updateBonus(String key, String email) {
		Connection conn = null;
		
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.INSERT_BONUS);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, key);
			
			int result = preparedStatement.executeUpdate();
			
			if( result > 0)
			{
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return false;
	}










	public static String getRecoEmail(String key) {
		
		Connection conn = null;
		
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECTBONUSEMAIL_ByKEY);
			preparedStatement.setString(1, key);
			ResultSet rs = preparedStatement.executeQuery();
			String email = "";
			while(rs.next())
			{
				email = rs.getString(1);
			}
			
			
			
			preparedStatement = conn.prepareStatement(Strings.DELETEBONUSEMAIL_ByKEY);
			preparedStatement.setString(1, key);
			
			int result = preparedStatement.executeUpdate();
			
			if(result > 0)
			{
				System.out.println("getRecoEmail: Key successfully invalidated.");
			}
			
			return email;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return null;
	}


	public static User getUser(String sponsoremail) {
		Connection conn = null;
		User luckyUser = new User();
		
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECTUSERByEMAIL);
			preparedStatement.setString(1, sponsoremail);
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				luckyUser.setName(rs.getString(1));
				luckyUser.setEmail(rs.getString(2));
				luckyUser.setParticipation(rs.getInt(4));
				luckyUser.setCoins(rs.getInt(5));
				luckyUser.setStudies(rs.getInt(3));
			}
			return luckyUser;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
			return null;
	}



	public static String getCookieValue(Cookie[] cookies, String cookieName) 
	{
		
		System.out.println("getCookieValue : Name of the cookie = " + cookieName);
		
		
		for (Cookie cookie : cookies) {
			System.out.println("getCookieValue : Cookie name from list :  " +cookie.getName());
			if(cookie.getName().equalsIgnoreCase(cookieName)){
				return cookie.getValue();
			}
		}
		
		return null;
	}


	public static String checkDBforExistingUserEmail(String email) 
	{
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECT_ONLYEMAILS);
			
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println("checkDBforExistingUserEmail : new user email : "+ email);
			
			while(rs.next())
			{
				
				
				if(rs.getString(1).equalsIgnoreCase(email)){
					System.out.println("checkDBforExistingUserEmail : user match found ");
					return "User email already registered";
				}
			}
			
			return Strings.STATUS;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "error in registering user.";
		}finally{
			try{
				conn.close();
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		
	}










	public static String checkpasswordStrength(String password) {
		System.out.println("checkpasswordStrength : Checking password strength");
		boolean hasUppercase = false,hasLowercase = false,hasNumeric = false,hasSplchars = false,doesnothave8characters = false;
		StringBuilder returnString = new StringBuilder();
		
		System.out.println("checkpasswordStrength : Checking password length");
		
		if (password.length() < 8){
		    returnString.append("\n checkpasswordStrength: password is too short \n");
		    System.out.println(returnString.toString());
			doesnothave8characters = true;
		}
		
		System.out.println("checkpasswordStrength: Checking if password has uppercase and lowercase  characters and numbers");
		
		if(password.matches(".*[A-Z].*"))
			hasUppercase = true;
		if(password.matches(".*[a-z].*"))
			hasLowercase = true;
		if(password.matches(".*\\d.*"))
			hasNumeric = true;
		if(password.matches(".*\\W.*"))
			hasSplchars = true;
		
		
		
		
		
		if(!hasLowercase)
			returnString.append("\ncheckpasswordStrength : the password needs to have at least 1 lowercase alphabet\n");
		if(!hasUppercase)
			returnString.append("\ncheckpasswordStrength :  the password needs to have at least 1 uppercase alphabet\n");
		if(!hasNumeric)
			returnString.append("\ncheckpasswordStrength :  the password needs to have at least 1 number\n");
		if(!hasSplchars)
			returnString.append("\ncheckpasswordStrength : the password needs to have at least 1 non-alphanumeric character\n");
		
		System.out.println(returnString.toString());
		
		if((hasLowercase && hasUppercase && hasNumeric && hasSplchars && !doesnothave8characters))
			return Strings.STATUS;
		else
			return returnString.toString();
	}
	
	



	public static boolean ActivateUser(String token) {
		
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			String user_email = null;
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECTUSER_FromTOKEN);
			preparedStatement.setString(1, token);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				user_email = rs.getString(1);
			}
			
			if(user_email == null){
				System.out.println("ActivateUser : Token Expired or no such token stored");
			}
			else
			{
				preparedStatement = conn.prepareStatement(Strings.UPDATEACTIVATIONSTATUS_ForEMAIL);
				preparedStatement.setString(1, user_email);
				
				int result = 0;
				if((result = preparedStatement.executeUpdate()) > 0 ){
					System.out.println("ActivateUser : No of rows updated : \n here updates will the number of users"
							+ " who got their profiles activated. The value should be one. \n in the current "
							+ "execution, the number of records updated is :" + result);
				
					
					preparedStatement = conn.prepareStatement(Strings.DELETETOCKENRECORD);
					preparedStatement.setString(1, token);	
					
					result = preparedStatement.executeUpdate();
					
					if(result > 0)
						System.out.println("ActivateUser : Link has been invalidated");
					
					
					return true;
				}
				else
				{
					System.out.println("ActivateUser : No rows got updated, user email with token in the link does not exist in User table.");
					return false;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try{
			conn.close();
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return false;
	}



	public static void updatefpwdDetails(String email, String token) throws SQLException {
		Connection conn = null;
		
		try {
			conn = DBConn.DBConnection();
			
			Date dte = new Date();
			java.sql.Date dateDB = new java.sql.Date(dte.getTime());
			
			System.out.println(dte.getTime());
			
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.FPWDTABLEUPDATE);
			preparedStatement.setString(1, email);
			preparedStatement.setDate(2, dateDB);
			preparedStatement.setString(3, token);
			
			int result = preparedStatement.executeUpdate();
			
			if(result > 0){
				System.out.println("updatefpwdDetails: Successful insert into FPWDTABLE");
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("updatefpwdDetails: Class NotFound error");
			e.printStackTrace();
		} catch (SQLException e) {
			if(e instanceof MySQLTransactionRollbackException){
			      throw e;
			    }
			System.out.println("updatefpwdDetails:  SQL Exception");
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("updatefpwdDetails: SQL Exception while closing connection");
				e.printStackTrace();
			}
		}
		
	}










	public static String retrieveEmail(String token) {
		System.out.println("Inside: retrieveEmail");
		
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			
			PreparedStatement preparestatement = conn.prepareStatement(Strings.RETRIEVEEMAIL_FromTOKEN);
			preparestatement.setString(1, token);
			
			ResultSet rs = preparestatement.executeQuery();
			String username = "";
			while(rs.next()){
				username = rs.getString(1);
				return username;
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("retrieveEmail: ClassNotFoundException or SQLException");
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("retrieveEmail: SQLException while closing connection");
				e.printStackTrace();
			}
		}
		
		return null;
	}










	public static boolean UpdatePassword(String email, String password) {
		System.out.println("Inside : UpdatePassword");
		Connection conn = null;
		
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.UPDATERESETPWD);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, email);
			
			int result = preparedStatement.executeUpdate();
			
			if(result > 0){
				return true;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("UpdatePassword : Class Not found Exception");
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("UpdatePassword : SQL Exception");
				e.printStackTrace();
			}
		}
		
		return false;
	}







	/*
	 * The below record is used to handle the case where there may be instances where the email sent 
	 * for password reset has been lost and the user tries to reset the password again generating a new 
	 * email. - The conflict results in the user getting locked out permanently unless admin intervenes.
	 */


	public static void DeleteUserpwdRestRecord(String email) {
		Connection conn = null;
		
		try{
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.DELETEFPWDRECORD);
			preparedStatement.setString(1, email);
			
			int result = preparedStatement.executeUpdate();
			
			if(result > 0){
				System.out.println("Successfully removed rest password records for user with email " + email);
			}
		}catch(SQLException e){
			System.out.println("DeleteUserpwdRestRecord : Issue with executing sql delete ");
		} catch (ClassNotFoundException e) {
			System.out.println("DeleteUserpwdRestRecord : Class Not Found Exception ");
			e.printStackTrace();
		}
	}
	
}
