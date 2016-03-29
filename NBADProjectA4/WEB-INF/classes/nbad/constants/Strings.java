package nbad.constants;

public class Strings {
	

	public static  String SERVERNAME = "";
	public static  int PORTNO = 8080;
	public static final int MAX_AGE_SECONDS = 600;

	public static final String STATUS = "CLEARED";
	public static final String MSGBODY = "To do your friend some good in return, please login to the system using the URL below and your friend will get a bonus for your participation: ";
	
	public static final String ADMINEMAIL = "Admin@REP.edu";
	
	public static final String FPWDMSGBODY = "You may click on the link below to reset your password. It redirects to a page where you may reset your password and enter your new credentials.  ";
	
	/*
	 * Database connection parameters
	 */
	
	
	/*
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "root";
	public static final String CONNECTION_PARAMS = "jdbc:mysql://localhost:3306/nbad2";
	public static final String DB_NAME = "nbad2";
	*/
	
	
	public static final String DB_USERNAME = "adminm4jim8y";
	public static final String DB_PASSWORD = "YJ-wkn6YHsgT";
	public static final String DB_NAME = "assignmenttwo";
	public static final String CONNECTION_PARAMS = "jdbc:mysql://127.11.91.2:3306/"+DB_NAME;
	
	
	
	/*
	 * GMAIL SMTP connection parameters
	 */
	
	public static final String USERNAME = "nbadprojectemail@gmail.com";
	public static final String PASSWORD = "alphabeta@123";
	
	
	
	
	
	/*
	 * SQL queries :
	 * 
	 */
	
	
	public static final String SELECTALL_USERS = "SELECT UNAME,EMAIL,STUDIES,PARTICIPATION,COINS "
			+ "FROM "+DB_NAME+".USER";
	
	
	public static final String SELECT_USER_ByEMAIL = "SELECT UNAME,EMAIL,STUDIES,PARTICIPATION,COINS "
			+ "FROM "+DB_NAME+".USER WHERE EMAIL = ?";
	
	
	public static final String UPDATE_USER_ByEMAIL = "UPDATE "+DB_NAME+".USER SET UNAME = ?,STUDIES = ?,"
			+ "PARTICIPATION = ?, COINS = ? WHERE EMAIL = ?";
	
	
	public static final String SELECT_USER_ByPASSWORD = "SELECT PASSWORD "
			+ "FROM "+DB_NAME+".USER WHERE EMAIL = ? AND ACTIVATION = 1";
	
	
	public static final String INSERT_NEWUSER = "INSERT INTO "+DB_NAME+".USER (UNAME, EMAIL,PASSWORD,STUDIES,PARTICIPATION,COINS,ACTIVATION)"
					+ " VALUES (?,?,?,?,?,?,0)";

	
	public static final String SELECT_STUDIES_ByEMAIL = "SELECT SCODE,DESCRIPTION,EMAIL,DATECREATED,QUESTION,IMAGEURL,"
			+ "REQPARTICIPANTS,ACTPARTICIPANTS,SSTATUS,SNAME FROM "+DB_NAME+".STUDY WHERE EMAIL = ?";
	
	
	public static final String UPDATE_STUDY_ByCODE = "UPDATE "+DB_NAME+".STUDY SET SNAME =?,DESCRIPTION = ?,EMAIL = ?,"
			+ "DATECREATED = ?,QUESTION = ?,IMAGEURL = ?,REQPARTICIPANTS = ?, ACTPARTICIPANTS = ?, SSTATUS = ?"
			+ " WHERE SCODE = ?";
	
	public static final String SELECT_MAX_STUDYCODE = "SELECT MAX(SCODE) FROM "+DB_NAME+".STUDY";
		
	public static final String INSERT_NEWSTUDY = "INSERT INTO "+DB_NAME+".STUDY ("
			+ "SCode,SName,Description,Email,DateCreated,Question,ImageURL,ReqParticipants,"
			+ "ActParticipants,SStatus) Values (?,?,?,?,?,?,?,?,?,?)";
	
	
	public static final String SELECT_STUDIES_ByCODE = "SELECT SCODE,DESCRIPTION,EMAIL,DATECREATED,QUESTION,IMAGEURL,"
			+ "REQPARTICIPANTS,ACTPARTICIPANTS,SSTATUS,SNAME FROM "+DB_NAME+".STUDY WHERE SCODE = ?";
	
	
	public static final String INSERT_NEWANSWER = "INSERT INTO "+DB_NAME+".ANSWER (EMAIL,CHOICE,SCODE, DATESUBMITTED) "
			+ "VALUES (?,?,?,?)";
	
	
	public static final String SELECT_ANSWERS_ByCODE = "SELECT EMAIL,CHOICE,SCODE, DATESUBMITTED FROM "+DB_NAME+".ANSWER WHERE SCODE = ?";
	
	
	public static final String SELECTALL_STUDIES =  "SELECT SCODE,DESCRIPTION,EMAIL,DATECREATED,QUESTION,IMAGEURL,"
			+ "REQPARTICIPANTS,ACTPARTICIPANTS,SSTATUS,SNAME FROM "+DB_NAME+".STUDY";

	
	public static final String INSERT_BONUS = "INSERT INTO "+DB_NAME+".BONUSCOINS (EMAIL,KEYVALUE) VALUES"
			+ "(?,?)";
	
	
	public static final String SELECTBONUSEMAIL_ByKEY = "SELECT EMAIL FROM "+DB_NAME+".BONUSCOINS WHERE KEYVALUE = ?";
	
	
	public static final String SELECTUSERByEMAIL = "SELECT UNAME,EMAIL,STUDIES,PARTICIPATION,COINS "
			+ "FROM "+DB_NAME+".USER WHERE EMAIL = ?";
	
	public static final String DELETEBONUSEMAIL_ByKEY = "DELETE FROM "+DB_NAME+".BONUSCOINS WHERE KEYVALUE = ?";
	
	public static final String SELECT_ONLYEMAILS = "SELECT EMAIL FROM "+DB_NAME+".USER";
	
	public static final String INSERT_TEMPUSER = "INSERT INTO "+DB_NAME+".tempuser (UName,Email,Password,IssueDate,Token) VALUES"
			+ "(?,?,?,?,?)";
	
	public static final String SELECTUSER_FromTOKEN = "SELECT EMAIL FROM "+DB_NAME+".tempuser WHERE TOKEN =  ? and datediff(now(),IssueDate) <=1";
	
	public static final String UPDATEACTIVATIONSTATUS_ForEMAIL = "UPDATE USER SET ACTIVATION = 1 where EMAIL = ?";
	
	public static final String DELETETOCKENRECORD = "DELETE FROM "+DB_NAME+".tempuser where token=?";
	
	public static final String FPWDTABLEUPDATE = "INSERT INTO RESETPWD_INFO (Email,IssueDate,Token) VALUES (?,?,?)";
	
	public static final String RETRIEVEEMAIL_FromTOKEN = "SELECT EMAIL FROM "+DB_NAME+".RESETPWD_INFO WHERE Token = ? and datediff(now(),IssueDate) <=1";
	
	public static final String UPDATERESETPWD = "UPDATE "+DB_NAME+".USER set Password = ? where Email = ?";
	
	public static final String DELETEFPWDRECORD = "DELETE FROM "+DB_NAME+".RESETPWD_INFO WHERE EMAIL = ?";
	
	

	
	
	
	
	
	
	
	
	
	
	
}
