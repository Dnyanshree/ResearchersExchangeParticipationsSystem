package nbad.utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import nbad.constants.Strings;
import nbad.model.Answer;
import nbad.model.Study;


public class StudyDB {
	
	static ArrayList<Study> studyList = new ArrayList<Study>();

	public StudyDB(String email) {
		studyList = genAllStudies();
	}
	
	public ArrayList<Study> getStudiesWithStatus(String status)
	{
		
		System.out.println("Called getStudiesWithStatus");
		System.out.println("Status : " + status);
		ArrayList<Study> returnList = new ArrayList<Study>();
		
		Iterator<Study> study_statusList = studyList.iterator();
		while(study_statusList.hasNext())
		{
			Study study_status = study_statusList.next();
			
			if(study_status.getStatus().equalsIgnoreCase(status))
			{
				returnList.add(study_status);
			}
		}
		return returnList;
	}
	
	public Study getStudyfromCode(String studyCode)
	{
		Study studyFromCode = new Study();
		System.out.println("Called getStudyfromCode");
		System.out.println("Code : " + studyCode);
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECT_STUDIES_ByCODE);
			preparedStatement.setString(1, studyCode);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				studyFromCode.setCode(rs.getString(1));
				studyFromCode.setDescription(rs.getString(2));
				studyFromCode.setEmail(rs.getString(3));
				studyFromCode.setDateCreated(rs.getString(4));
				studyFromCode.setQuestion(rs.getString(5));
				studyFromCode.setImageURL(rs.getString(6));
				studyFromCode.setRequestedParticipants(rs.getInt(7));
				studyFromCode.setNumOfParticipants(rs.getInt(8));
				studyFromCode.setStatus(rs.getString(9));
				studyFromCode.setName(rs.getString(10));
				studyFromCode.setAnswers(getAnswerForCode(studyCode));
				
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("getStudyfromCode: Error while retrieving studies filtered by code");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("getStudyfromCode : Error attempting to close connection.");
				e.printStackTrace();
			}
		}
		
		
		
		return studyFromCode;
	}
	
	public ArrayList<Study> getStudies(){
		return studyList;
	}
	
	
	
	public ArrayList<Study> genAllStudies(){
		ArrayList<Study> genStudy_List = new ArrayList<Study>();
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECTALL_STUDIES);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				Study study = new Study();
				study.setCode(rs.getString(1));
				study.setDescription(rs.getString(2));
				study.setEmail(rs.getString(3));
				study.setDateCreated(rs.getString(4));
				study.setQuestion(rs.getString(5));
				study.setImageURL(rs.getString(6));
				study.setRequestedParticipants(rs.getInt(7));
				study.setNumOfParticipants(rs.getInt(8));
				study.setStatus(rs.getString(9));
				study.setName(rs.getString(10));
				study.setAnswers(getAnswerForCode(rs.getString(1)));
				genStudy_List.add(study);
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error while getting result of SELECT_STUDIES_ByEMAIL");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("genStudy: Error while attempting to close connection.");
				e.printStackTrace();
			}
		}
		
		
		return genStudy_List;
	}
	
	public ArrayList<Study> getStudiesbyEmail(String email){
		ArrayList<Study> genStudy_List = new ArrayList<Study>();
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECT_STUDIES_ByEMAIL);
			
			
			preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				Study study = new Study();
				study.setCode(rs.getString(1));
				study.setDescription(rs.getString(2));
				study.setEmail(rs.getString(3));
				study.setDateCreated(rs.getString(4));
				study.setQuestion(rs.getString(5));
				study.setImageURL(rs.getString(6));
				study.setRequestedParticipants(rs.getInt(7));
				study.setNumOfParticipants(rs.getInt(8));
				study.setStatus(rs.getString(9));
				study.setName(rs.getString(10));
				study.setAnswers(getAnswerForCode(rs.getString(1)));
				genStudy_List.add(study);
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error while getting result of SELECT_STUDIES_ByEMAIL");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("getStudiesbyEmail : error attempting to close connection");
				e.printStackTrace();
			}
		}
		
		
		return genStudy_List;
	}

	private ArrayList<Answer> getAnswerForCode(String studyCode) {
		Connection conn = null;
		ArrayList<Answer> ansList = new ArrayList<Answer>();
		try {
			conn = DBConn.DBConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECT_ANSWERS_ByCODE);
			preparedStatement.setString(1, studyCode);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				Answer ans = new Answer();
				ans.setEmail(rs.getString(1));
				ans.setChoice(rs.getInt(2));
				ans.setStudyCode(rs.getString(3));
				ans.setDateSubmitted(rs.getDate(4));
				
				ansList.add(ans);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("getAnswerForCode : Error while attempting to retrive answers by code.");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("getAnswerForCode: Error while attempting to close connection.");
				e.printStackTrace();
			}
		}
		
		
		return ansList;
	}

	public void updateStudy(Study updateStudy) {
		System.out.println("Inside Update Study");
		
				updateStudyTable(updateStudy);
			
		}

	private void updateStudyTable(Study updateStudy) {
		
		Connection conn = null;
		try {
			conn = DBConn.DBConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.UPDATE_STUDY_ByCODE);
			
			preparedStatement.setString(1, updateStudy.getName());
			preparedStatement.setString(2, updateStudy.getDescription());
			preparedStatement.setString(3,updateStudy.getEmail());
			preparedStatement.setString(4, updateStudy.getDateCreated());
			preparedStatement.setString(5, updateStudy.getQuestion());
			preparedStatement.setString(6, updateStudy.getImageURL());
			preparedStatement.setInt(7, updateStudy.getRequestedParticipants());
			preparedStatement.setInt(8, updateStudy.getNumOfParticipants());
			preparedStatement.setString(9, updateStudy.getStatus());
			preparedStatement.setString(10, updateStudy.getCode());
			
			// TODO - answer details not updated into database?
			
			
			int result = preparedStatement.executeUpdate();
			if(result > 0){
				System.out.println("updateStudyTable : Study Table updated successfully");
			}
			else
			{
				System.out.println("updateStudyTable: Result of the update statement is : " + result);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("updateStudyTable: error while attempting to close connection.");
				e.printStackTrace();
			}
		}
	}
	
	

	public void displayStudyList(ArrayList<Study> display_studylist) {
		Iterator<Study> display_studyIterator= display_studylist.iterator();
		
		while(display_studyIterator.hasNext())
		{
			System.out.println(display_studyIterator.next().toString());
		}
	}

	public int addNewStudy(Study study) {
		java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		studyList.add(study);
		int result = 0;
		Connection conn = null;
		try {
			
			conn = DBConn.DBConnection();
		
		PreparedStatement preparedStatement = conn.prepareStatement(Strings.SELECT_MAX_STUDYCODE);
		ResultSet rs =preparedStatement.executeQuery();

		String code = "";
		if(rs != null){
		while(rs.next()){
			if(rs.getString(1) == null)
				code = "101";
			else
				code = String.valueOf(Integer.valueOf(rs.getString(1)) + 1);
			}
		}
		
		System.out.println("The code for the new question: " + code);
		
		preparedStatement = conn.prepareStatement(Strings.INSERT_NEWSTUDY);
		java.util.Date dateStr = null;
		try {
			dateStr = dateFormatter.parse(study.getDateCreated());
		} catch (ParseException e) {
			System.out.println("addNewStudy : error while attempting to parse date.");
			e.printStackTrace();
		}
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
		
		
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, study.getName());
		preparedStatement.setString(3, study.getDescription());
		preparedStatement.setString(4, study.getEmail());
		preparedStatement.setDate(5, dateDB);
		preparedStatement.setString(6, study.getQuestion());
		preparedStatement.setString(7, study.getImageURL());
		preparedStatement.setInt(8, study.getRequestedParticipants());
		preparedStatement.setInt(9, study.getNumOfParticipants());
		preparedStatement.setString(10, study.getStatus());
		
		result = preparedStatement.executeUpdate();
		if(result > 0)
		{
			System.out.println("addNewStudy : Executed successfully");
		}
		else
		{
			System.out.println("addNewStudy : Execution returns no rows updated");
		}
		
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error while executing addnew Study");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}

	public int updateAnswers(int choice, String studyCode, String createDate, String email) {
		java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Connection conn = null;
		int result = 0;
		try {
			conn = DBConn.DBConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(Strings.INSERT_NEWANSWER);
			
			Date normalDate = dateFormatter.parse(createDate);
			
			java.sql.Date sqlDate = new java.sql.Date(normalDate.getTime());
			
			
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, choice);
			preparedStatement.setString(3, studyCode);
			preparedStatement.setDate(4, sqlDate);
			
			result = preparedStatement.executeUpdate();
			
			if(result > 0){
				System.out.println("Answers inserted successfully");
			}
			
		}catch(MySQLIntegrityConstraintViolationException e){
			System.out.println("One user may not respond twice to the same survey");
			return -1;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("updateAnswers : error while updating answers");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("updateAnswers : Error while attempting to parse date");
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("error while attempting to close connection");
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
}
