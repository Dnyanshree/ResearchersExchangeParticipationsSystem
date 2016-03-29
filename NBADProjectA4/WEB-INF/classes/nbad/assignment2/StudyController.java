package nbad.assignment2;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nbad.constants.Strings;
import nbad.model.Answer;
import nbad.model.Study;
import nbad.model.User;
import nbad.utility.StudyDB;
import nbad.utility.UserDB;
/**
 * Servlet implementation class StudyController
 */
@WebServlet("/StudyController")
@MultipartConfig
public class StudyController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static URL UserFilePath;
	StudyDB studyDB = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudyController() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		HttpSession session = request.getSession();
		session.setAttribute("serverName", request.getServerName());
		session.setAttribute("portno",request.getServerPort());
		
		Strings.SERVERNAME = request.getServerName();
		Strings.PORTNO = request.getServerPort();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		HttpSession session = request.getSession();
		session.setAttribute("serverName", request.getServerName());
		session.setAttribute("portno",request.getServerPort());
		String url = "";
		Strings.SERVERNAME = request.getServerName();
		Strings.PORTNO = request.getServerPort();
		
		User user = (User) session.getAttribute("theUser");
		
		if(user == null){
			request.setAttribute("action", "signinpage");
			url = "/WEB-INF/login.jsp";
		}
		else{
		studyDB = new StudyDB(user.getEmail());
		
		Study study = new Study();
		
		String action = request.getParameter("action");
		
		System.out.println("Action in StudyController : " + action);
		url = "/WEB-INF/home.jsp";
		
		
		/* 
		 * 
		Checks the http request for a parameter called “action”
 		If there is no action parameter, or if it has an unknown value
		Checks the session for “theUser” object,
		 If it exists
			Dispatches to the website’s main page (main.jsp)
		 If it does not exist
		 	Dispatches to the website’s main page (home.jsp)
		 	*
		 	*/
		
		if (action == null) {
			if ((user = (User) session.getAttribute("theUser")) != null) {
				url = "/WEB-INF/main.jsp";
			} else {
				url = "/WEB-INF/home.jsp";
			}
		}else if (action.equalsIgnoreCase("recommend")) {
			if ((user = (User) session.getAttribute("theUser")) != null) {
				url = "/WEB-INF/recommend.jsp";
			} else {
				url = "/WEB-INF/login.jsp";
			}
		}else if (action.equalsIgnoreCase("newstudy")) {
			if ((user = (User) session.getAttribute("theUser")) != null) {
				url = "/WEB-INF/newStudy.jsp";
			} else {
				url = "/WEB-INF/login.jsp";
			}
		}else if (action.equalsIgnoreCase("contact")) {
			if ((user = (User) session.getAttribute("theUser")) != null) {
				url = "/WEB-INF/contact.jsp";
			} else {
				url = "/WEB-INF/login.jsp";
			}
		}else if(action.equalsIgnoreCase("studies"))
			{
				if((user = (User)session.getAttribute("theUser")) != null) 
				{
					System.out.println("Email address :  " + user.getEmail());
					
					request.setAttribute("studiesList", studyDB.getStudiesbyEmail(user.getEmail()));
				
					url="/WEB-INF/studies.jsp";
				}
				else
				{
					url="/WEB-INF/login.jsp";
				}
			}
		/*
 		If action is “participate”
		    Checks the session for “theUser” object,
		 If it exists
		    Checks the http request object for parameter called “StudyCode”
		 		If does not exist
		 			Retrieves the open studies (status = start) from the DB.
					Puts them in the http request object.
		 			Dispatches to the pariticipate.jsp.
		 		If it exists
		 			Retrieves the study record (question) from the DB.
					Puts it in the http request object.
					Dispatches to the question.jsp.
		 If it does not exist
		 	Dispatches to the login page (login.jsp)*/
		
		else if (action.equalsIgnoreCase("participate")) {
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				String studyCode = request.getParameter("code");
				if (studyCode== null || studyCode.equals("000")) {
					/*
					 * Retrieves the open studies (status = start) from the DB.
					 * Puts them in the http request object
					 */	
					ArrayList<Study> studyList = studyDB.getStudiesWithStatus("start");
					request.setAttribute("studylist", studyList);
					url = "/WEB-INF/participate.jsp";
				} else {
					System.out.println("Study Code : " + studyCode);
					Study studyFromCode = studyDB.getStudyfromCode(studyCode);
					if(studyFromCode != null){
					request.setAttribute("studyfromcode", studyFromCode);
					url = "/WEB-INF/question.jsp";
					}
					else
					{
						request.setAttribute("msg", "Error extracting question for study Code "+ studyCode);
						url = "/WEB-INF/main.jsp";
					}
				}
			} 
			else {
			url = "/WEB-INF/login.jsp";
		}
	}
		/*
 		If action is “edit”
		 	Checks the session for “theUser” object,
		 If it exists
		 	Checks the http request object for a parameter called “StudyCode”.
		 	Pulls a study record from the DB based on the study code and creator’s email address.
			Adds it to the http request object.
			Dispatches to the editstudy.jsp.
		 If it does not exist
		 	Dispatches to the login page (login.jsp)*/
		
		else if (action.equalsIgnoreCase("edit")) {
			if ((user = (User) session.getAttribute("theUser")) != null) {
				String studyCode = request.getParameter("code");
				if (studyCode != null) {
					study = studyDB.getStudyfromCode(studyCode);
					if(study != null){
						request.setAttribute("studytoedit", study);
						url = "/WEB-INF/editStudy.jsp";
					}
					else{
						request.setAttribute("msg", "Error retrieving study with code : " + studyCode);
						url = "/WEB-INF/main.jsp";
					}
					
				}
			} else {
				url = "/WEB-INF/login.jsp";
			}
		}
		 	
		/* If action is “update”
		 	Checks the session for “theUser” object,
		 If it exists
		 	Checks the http request for parameter called “study”
		    Updates the study record in the DB.
			Retrieves the user’s studies from the DB.
			Puts them in the http request object.
			Dispatches to the studies.jsp
		 If it does not exist
		 	Dispatches to the login page (login.jsp)*/
		
		else if (action.equalsIgnoreCase("update")) {
			System.out.println("Inside Update call");
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				System.out.println("User session is not null");
				String StudyCode = request.getParameter("code");
				if (StudyCode != null) {
					/*
					 *  Updates the study record in the DB.
						Retrieves the user’s studies from the DB.
						Puts them in the http request object.
					 */
					System.out.println("study code is not null");
					study = studyDB.getStudyfromCode(StudyCode);
					study.setName(request.getParameter("StudyName"));
					study.setQuestion(request.getParameter("QuesText"));
					study.setRequestedParticipants(Integer.parseInt(request.getParameter("numPart")));
					study.setDescription(request.getParameter("studyDescription"));
					study.setImageURL(request.getParameter("imageFile"));
					
					
					studyDB.updateStudy(study);
					
					url = "/StudyController?action=studies";
				}
				else
				{
					System.out.println("Study code is null");
				}
			} else {
				url = "/WEB-INF/login.jsp";
			}
		} 

		 			 	
		/*If action is “add”
		 	Checks the session for “theUser” object,
		 If it exists
		 	Checks the http request for parameter called “study”.
		    Adds the study record to the DB.
		 	Retrieves the user’s studies from the DB.
			Puts them in the http request object.
			Dispatches to the studies.jsp*/
		else if (action.equalsIgnoreCase("add")) {
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				study = null;
				study = new Study();
				
				if(request.getParameter("StudyName") == null){
					System.out.println("study name (parameter) is null");
				}
				else
				{
					System.out.println("study name (parameter) not null");
				}
				String createDate = dateFormatter.format(new Date());				
				
				
				
				
				study.setName(request.getParameter("StudyName"));
				study.setCode(String.valueOf(new Random().nextInt(200)));
				study.setDateCreated(createDate);
				System.out.println(study.getDateCreated());
				study.setDescription(request.getParameter("description"));
				study.setEmail(user.getEmail());
				study.setImageURL(request.getParameter("imageFile"));
				study.setRequestedParticipants(Integer.parseInt(request.getParameter("numPart")));
				study.setStatus("start");
				study.setQuestion(request.getParameter("QuesText"));
				
			
				
					/*
					 *  Add the study record in the DB.
						Retrieves the user’s studies from the DB.
						Puts them in the http request object.
					 */
				if(studyDB.addNewStudy(study) > 0){
					user.setCoins(user.getCoins()+1);
					user.setStudies(user.getStudies()+1);
				}
					
				UserDB.updateUser(user);	
				request.setAttribute("studiesList", studyDB.getStudiesbyEmail(user.getEmail()));
					url = "/StudyController?action=studies";
			} else {
				url = "/WEB-INF/login.jsp";
			}
		} 
		
		/*If action is “start”
		 	Checks the session for “theUser” object.
		 If it exists
		 	Checks the http request for parameter called “StudyCode”.
		 	Sets the study status to start.
			Retrieves the user’s studies from the DB.
			Puts them in the http request object.
			Dispatches to the studies.jsp.
		 If it does not exist
		 	Dispatches to the login page (login.jsp)*/
		else if (action.equalsIgnoreCase("start")) {
		//	System.out.println("inside action start");
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				String studyCode = request.getParameter("code").toString();
				if (studyCode != null) {
					System.out.println("Study code was resolved to : " + studyCode);
					/*
					 *  Sets the study status to start.
						Retrieves the user’s studies from the DB.
						Puts them in the http request object.
					 */
					//System.out.println("size of StudyDB.sList : " + StudyDB.sList.size());
					
					Study study_statusChange = studyDB.getStudyfromCode(studyCode);
					study_statusChange.setStatus("stop");
					studyDB.updateStudy(study_statusChange);
						
					request.setAttribute("studiesList", studyDB.getStudiesbyEmail(user.getEmail()));
					
					url = "/StudyController?action=studies";
				}
			} else {
				url = "/WEB-INF/login.jsp";
			}
		} 	
		
		
		/*If action is “stop”
		 	Checks the session for “theUser” object,
		 If it exists
		 	Checks the http request for parameter called “StudyCode”.	
		 	Sets the study status to stop.
			Retrieves the user’s studies from the DB.
			Puts them in the http request object.
			Dispatches to the studies.jsp
		 If it does not exist
		 	Dispatches to the login page (login.jsp)*/
		else if (action.equalsIgnoreCase("stop")) {
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				String studyCode = request.getParameter("code").toString();
				if (studyCode != null) {
					/*
					 *  Sets the study status to start.
						Retrieves the user’s studies from the DB.
						Puts them in the http request object.
					 */
					
					Study study_statusChange = studyDB.getStudyfromCode(studyCode);
					study_statusChange.setStatus("start");
					studyDB.updateStudy(study_statusChange);
						
					
					request.setAttribute("studiesList", studyDB.getStudiesbyEmail(user.getEmail()));
					
					
					
					url = "/StudyController?action=studies";
				}
			} else {
				url = "/WEB-INF/login.jsp";
			}
		} 		
		
		/* If action is “answer”
		 	Checks the session for “theUser” object,
		 If it exists
		 	Checks the http request for parameter called “StudyCode” and “choice”.
			Adds the answer to the DB.
			Update the user data (coins and participation in the DB and the session).
		    Retrieves the open studies (status = start) from the DB.
			Puts them in the http request object.
			Dispatches to the pariticipate.jsp.
		 If it does not exist
		 	Dispatches to the login page (login.jsp)*/
	
		else if (action.equalsIgnoreCase("answer")) 
		{
			System.out.println("Inside Answer");
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				String studyCode = request.getParameter("code");
				request.getSession().setAttribute("code", null);
				System.out.println("Study Code : " + studyCode);
				
				int choice = Integer.parseInt(request.getParameter("choice"));
				System.out.println("Choice : " + choice);
				
				
				if ((choice >=1 || choice <= 7) && studyCode!= null) {
					/*
					 * Adds the answer to the DB.
						Update the user data (coins and participation in the DB and the session).
		    			Retrieves the open studies (status = start) from the DB.
						Puts them in the http request object.
					 */
					
					Study study_withans = studyDB.getStudyfromCode(studyCode);
					ArrayList<Answer> ansList = study.getAnswers();
					if(ansList == null)
					{
						ansList = new ArrayList<Answer>();
					}
					Answer ans = new Answer();
					ans.setChoice(choice);
					ans.setEmail(user.getEmail());
					System.out.println("User's Answer choice " + choice);
					System.out.println("Currently logged in User's email " + user.getEmail());
					ansList.add(ans);
					study_withans.setAnswers(ansList);
					study_withans.setNumOfParticipants(study.getNumOfParticipants()+1);
					studyDB.updateStudy(study_withans);
					
					String createDate = dateFormatter.format(new Date());	
					
					int val = studyDB.updateAnswers(choice,studyCode,createDate,user.getEmail());
					
					if(val == -1){
						request.setAttribute("msg", "One user may not participate in a survey more than once");
					}
					else if(val > 0){
						study.setNumOfParticipants(study.getNumOfParticipants()+1);
						user.setCoins(user.getCoins()+1);
						user.setParticipation(user.getParticipation()+1);
						
						System.out.println("Participant has be remunerated.");
						
						String bonus = UserDB.getCookieValue(request.getCookies(),"BonusStatus");
						if(bonus != null){
						if(bonus.equalsIgnoreCase("1")){
							System.out.println("The Value of bonus is : " + bonus);
						String sponsoremail = UserDB.getCookieValue(request.getCookies(),"sponsoremail");
						if(sponsoremail != null){
							System.out.println("The Value of SponsorEmail is : " + sponsoremail);
							User luckyUser = UserDB.getUser(sponsoremail);
							
							System.out.println("Old Value of luckuser's coins :" + luckyUser.getCoins());
							
							luckyUser.setCoins(luckyUser.getCoins() + 2);
							
							System.out.println("New Value of luckuser's coins :" + luckyUser.getCoins());
							
							UserDB.updateUser(luckyUser);
							System.out.println("Lucky User has two coins updated to his account.");
							
							
							Cookie[] cookies = request.getCookies();
							
							for (Cookie cookie : cookies) {
								System.out.println("Cookie name from list :  " +cookie.getName());
								if(cookie.getName().equalsIgnoreCase("BonusStatus") || cookie.getName().equalsIgnoreCase("sponsoremail")){
									System.out.println("setting expiry for : " + cookie.getName());
									cookie.setMaxAge(0);
									
								}
							}
								
						}
						else
						{
							System.out.println("sponsoremail is null");
						}
						}
						else
						{
							System.out.println("bonus is not 1");
						}
						}
						else
						{
							System.out.println("bonus is null");
						}
					}
					
					
					/*
					 * Give the user Coins for Participation
					 * Add the participant count in the study.
					 */
					
					
					
					UserDB.updateUser(user);
					
					request.getSession().setAttribute("code", "000");
					url = "/UserController?action=main";
				}
			} else {
				url = "/WEB-INF/login.jsp";
			}
		}
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);	
	}
	}
