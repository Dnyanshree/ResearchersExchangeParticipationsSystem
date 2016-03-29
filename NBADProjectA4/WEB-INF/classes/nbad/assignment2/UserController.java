package nbad.assignment2;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException;

import nbad.constants.Strings;
import nbad.model.User;
import nbad.utility.EmailDetail;
import nbad.utility.MailUtils;
import nbad.utility.UserDB;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	
	public UserController() {
		super();
		
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/*
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute("serverName", request.getServerName());
		session.setAttribute("portno",request.getServerPort());
		
		Strings.SERVERNAME = request.getServerName();
		Strings.PORTNO = request.getServerPort();
		
		String action = request.getParameter("action");
		String url = "/UserController?action=home";
		
		User user = (User) session.getAttribute("theUser");

		/*
		 * If there is no action parameter, or if it has an unknown value,
		 * dispatches directly to the website’s main page (home.jsp).
		 */
		if (action == null) {
			
			System.out.println("reqeust servername : " + Strings.SERVERNAME);
			System.out.println("request portno : " + Strings.PORTNO);
			url = "/WEB-INF/home.jsp";
			
			
		}else if (action.equalsIgnoreCase("activate")) {
			String token = request.getParameter("token");
			
			if(UserDB.ActivateUser(token)){
				request.setAttribute("msg", "Congratulations! your email has been verified. Please go ahead to login");
				url = "/UserController?action=signinpage";
			}
			else
			{
				request.setAttribute("msg","Something went wrong and your link did not activate your account"
						+ "\n retry feature not yet implemented");
				
				url = "/UserController?action=signinpage";	
			}
		}
		else if (action.equalsIgnoreCase("main")) { //action=main
			
			/*
			 * If action is “main” Checks the session for “theUser” object, If
			 * it exists Dispatches to main.jsp If it does not exist Dispatches
			 * to login.jsp
			 */
			
			System.out.println("from : Action = main");
			if(request.getSession().getAttribute("theUser") == null){
				System.out.println("theUser session attribute still not be set");
			}
			else
			{
				System.out.println("The user attribute is set to: ");
				System.out.println(((User)request.getSession().getAttribute("theUser")).toString());
			}
			
			System.out.println("Call to main ");
			if((user = (User) request.getSession().getAttribute("theUser")) != null){
				url = "/WEB-INF/main.jsp";
			}
			else
			{
				url = "/UserController?action=signinpage";	
			}
			

		}else if (action.equalsIgnoreCase("signinpage")){
			url = "/WEB-INF/login.jsp";
		}else if (action.equalsIgnoreCase("signuppage")){
			url = "/WEB-INF/signup.jsp";
		}else if (action.equalsIgnoreCase("logout")){
			request.getSession().invalidate();
			
			session = request.getSession();
			
			session.setAttribute("serverName", request.getServerName());
			session.setAttribute("portno",request.getServerPort());
			
			
			url = "/UserController?action=home";
			
		}else if (action.equalsIgnoreCase("login")) {		//action=login
			
			/*
			 * If action is “login” >Checks the http request for parameters
			 * called: email and password >Validates if the combination exists
			 * in the database >If they are correct, >Creates a User bean for
			 * the user >Adds the User bean to the current session as “theUser”.
			 * >Dispatches to the main.jsp >If they are not correct, >Adds an
			 * error message to the http request object , call the parameter
			 * “msg”. >Dispatches to the login.jsp page.
			 */

			String username = request.getParameter("username").toString();
			String password = request.getParameter("password").toString();

			System.out.println(username);
			//System.out.println(password);


			if ((user = UserDB.ValidateUser(username, password)) != null) {
				request.setAttribute("msg", "Login Successful! Welcome " + user.getName());
				request.getSession().setAttribute("theUser", user);
				
				if(request.getSession().getAttribute("theUser") == null){
					System.out.println("theUser session attribute still not set");
				}
				else
				{
					System.out.println("The user attribute is set to: ");
					System.out.println(((User)request.getSession().getAttribute("theUser")).toString());
				}
				
				url = "/UserController?action=main"; 
			} else {
				request.setAttribute("msg", "Invalid Credentials / Unknown User");
				url = "/UserController?action=signinpage";
			}
			
			
		} else if (action.equalsIgnoreCase("create")) { //action=create

			/*
			 * If action is “create” >Checks the http request for parameters:
			 * name, email, password, and confirm password. >Validates the above
			 * information for possible errors. >If there is any error: >Adds an
			 * error message to the http request object, call the parameter
			 * “msg”. >Adds the above information to the http request.
			 * >Dispatches to the signup.jsp page. >If there is no errors
			 * >Creates a User bean for the user >Adds the User bean to the
			 * current session as “theUser” >dispatches to the main.jsp.
			 */
			
			/*
			 * Additional feature : 
			 * check if user exists if yes, redirect to signup page with error msg
			 * 
			 * check if password has one spl character small letter capital letter and number if not, redirect to signup page with error msg
			 * 
			 * add a new column in user table that says validated. validated by default is 0,
			 *  
			 * once user is signed in, records is added to user table, generate an unique id and append it as a 
			 * parameter to login action in servlet send out an email with this link to the email with which user
			 * has registered. (in case user wants to resend link, have a link that takes user to a page to enter email to perform this action.
			 * 
			 * create a tempuser table that stores the unique id and user email and time.
			 * link should redirect to login page.
			 * 
			 * login page should invalidate the link, set the activation status to 1.
			 * only users with activation status 1 are allowed to login.
			 */

			System.out.println("Inside Create");
			user = new User();

			String name = request.getParameter("name").toString();
			String email = request.getParameter("email").toString();
			String password = request.getParameter("password").toString();
			String cpassword = request.getParameter("confpassword").toString();

			System.out.println(name + "," + email + "," + password + ";");
			session = request.getSession();
			
			if (name != null && email != null && password != null && cpassword != null && password.equals(cpassword)) 
			{
				String msg1 = "",msg2 ="";
				if((msg1 = UserDB.checkDBforExistingUserEmail(email)) != null && (msg2 = UserDB.checkpasswordStrength(password)) != null)
				{	
					System.out.println("message returned : msg1 and msg2 :" + msg1 + " : " + msg2);
					if(!msg1.equalsIgnoreCase(Strings.STATUS) || !msg2.equalsIgnoreCase(Strings.STATUS)){
										request.setAttribute("msg", "Registration failed for the reason(s): <br> username check:" + msg1 + ""
												+ "<br> password check:" + msg2);
										url = "/UserController?action=signuppage";
										
					}
					else
					{
				
					user = UserDB.SignUpUser(name, email, password);

						if (user != null) {
							
							request.setAttribute("msg", "Registration successful, please check your email for verification link");
							session.removeAttribute("theUser");
							//session.setAttribute("theUser", user); // commented to facilitate new functionality
							// user should verify email address before being allowed to login.
							
							url = "/UserController?action=home";
						} else {
							request.setAttribute("msg", "Registration failed : issues with creating temp user / sending email / creating user in DB");
							url = "/UserController?action=signuppage";
						}
					}			
			} else {
				System.out.println("Errors in output of UserDB.checkDBforExistingUserEmail and checkpasswordStrength");
				url = "/UserController?action=signuppage";
				}
			}
		else
		{
			System.out.println("Step 1 Fails in user signup.");
			
			
			System.out.println("NULL values received by servlet.");
			request.setAttribute("msg", "Registration failed");
			url = "/UserController?action=signuppage";
		}
		} else if (action.equalsIgnoreCase("how")) {		//action=how
			/*
			 * If action is “how” >Checks the session for “theUser” object, >If
			 * it exists >Dispatches to main.jsp >if it does not exist
			 * >Dispatches to how.jsp
			 */
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				url = "/WEB-INF/howl.jsp";
			} else {
				url = "/WEB-INF/how.jsp";
			}

		} else if (action.equalsIgnoreCase("about")) {		//action=about
			/*
			 * If action is “about” >Checks the session for “theUser” object,
			 * >If it exists >Dispatches to about.jsp >If it does not exist
			 * >Dispatches to aboutl.jsp
			 */
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				url = "/WEB-INF/aboutl.jsp";
			} else {
				url = "/WEB-INF/about.jsp";
			}
		} else if (action.equalsIgnoreCase("home")) {		//action=home
			/*
			 * If action is “home” Checks the session for “theUser” object, If
			 * it exists Dispatches to main.jsp If it does not exist Dispatches
			 * to home.jsp
			 */
			session = request.getSession();
			if ((user = (User) session.getAttribute("theUser")) != null) {
				
				session.removeAttribute("theUser");
				session.setAttribute("theUser", user);
				url = "/UserController?action=main";
			} else {
				url = "/WEB-INF/home.jsp";
			}
			
			
		} 
		else if (action.equalsIgnoreCase("forgotpassword")){
			/*
			 * <forgotpassword of mode input email> mode will be triggered the forgot password link is clicked in signinpage
			 * Upon filling up a valid email and submitting, the application will call the <forgot password in mailsend mode>.
			 * mail send will send out an email to the specified email address with the reset password link. Upon clicking the link
			 * takes you to <fpwdresetpage with token> attribute. The controller checks if the email associated with the token
			 * exists, redirect the request to <resetpwd> which will open resetpassword.jsp. Once the user types in the new
			 * password, the form submission takes the user to <forgotpassword of mode updatenewpwd>
			 * 
			 * 
			 */
			
			
			String mode = request.getParameter("mode");
			
			if(mode.equalsIgnoreCase("inputemail")){
				url = "/UserController?action=fpwdemail";
			}
			else if (mode.equalsIgnoreCase("mailsend")){
				String email = request.getParameter("restpwdemail"); 
				
				System.out.println("Check if active username exists with the specified email");
				String checkval = "";
				if((checkval = UserDB.checkDBforExistingUserEmail(email)) != null){
					if(checkval.equalsIgnoreCase("User email already registered")){
						System.out.println("send email to the the user with URL with generated token");
						
						EmailDetail emailDetail = new EmailDetail(email.split("@")[0],Strings.ADMINEMAIL,email,"") ;
						
						String token = UUID.randomUUID().toString();
						String link = "http://"+Strings.SERVERNAME+":"+Strings.PORTNO+"/NBADProject/UserController?action=fpwdresetpage&token="+token ;
						
						try {
							UserDB.updatefpwdDetails(email,token);
						
						
						
						MailUtils.sendEmail_generic(emailDetail, "REP : Password Reset Assistance", Strings.FPWDMSGBODY, link);
						
						request.setAttribute("msg", "An Email has been sent to the provided ID with the reset password link");
						url="/UserController?action=signinpage";
						} catch (SQLException e) {
							request.setAttribute("msg", "An unforeseen error occurred during the password reset process, kindly attempt password reset once again."
									+ " Ignore any further messages.");
							url="/UserController?action=signinpage";
							if (e instanceof MySQLTransactionRollbackException){
								UserDB.DeleteUserpwdRestRecord(email);
							}
							e.printStackTrace();
						}
					}
				}
			}
			else if (mode.equalsIgnoreCase("updatenewpwd")){
				String password = request.getParameter("password");
				String confpassword = request.getParameter("confpassword");
				String email = request.getParameter("email");
				if(email == null || email.equalsIgnoreCase("")){
					System.out.println("Email attribute not set.");
					request.setAttribute("msg", "Error occurred during password reset");
					url="/UserController?action=signinpage";
				}
				else if(password.equalsIgnoreCase(confpassword)){
					if(UserDB.UpdatePassword(email,password)){
						request.setAttribute("msg", "Password reset successfully");
						url="/UserController?action=signinpage";
					}
				}
				else
				{
					System.out.println("Passwords do not match");
					request.setAttribute("msg","Passwords do not match" );
				}
			}
		}
		else if (action.equalsIgnoreCase("fpwdresetpage")){
			
			
			String token = request.getParameter("token");
			System.out.println(token +" has been fetched");
			String useremail = "";
			if(token != null){
			useremail = UserDB.retrieveEmail(token);
			System.out.println(useremail +  " has been fetched");		
			}
			else
				System.out.println("action=fpwdresetpage : Token in email link is not valid and is null");
			
			if(useremail != null){
				System.out.println("redirecting request to action: resetpwd");
				url="/UserController?action=resetpwd&email="+useremail;
			}else
			{
				System.out.println("action=fpwdresetpage : no email present for corresponding token");
				request.setAttribute("msg", "no email present for corresponding token, please attempt the password reset again");
			}
		}
		else if (action.equalsIgnoreCase("resetpwd")){
			String email = request.getParameter("email");
			System.out.println("Received Request to rest password for user :" + email);
			request.setAttribute("email", email);
			url = "/WEB-INF/resetpassword.jsp";
		}
		else if (action.equalsIgnoreCase("fpwdemail")){
			url = "/WEB-INF/fpwdemail.jsp";
		}
		System.out.println("Redirecting to : " + url);
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
