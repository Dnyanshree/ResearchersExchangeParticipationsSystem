package nbad.assignment3;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nbad.constants.Strings;
import nbad.model.User;
import nbad.utility.Contact;
import nbad.utility.EmailDetail;
import nbad.utility.MailUtils;
import nbad.utility.UserDB;
/**
 * Servlet implementation class MailSendController
 */
@WebServlet("/MailSendController")
public class MailSendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailSendController() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("serverName", request.getServerName());
		session.setAttribute("portno",request.getServerPort());
		
		Strings.SERVERNAME = request.getServerName();
		Strings.PORTNO = request.getServerPort();
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("serverName", request.getServerName());
		session.setAttribute("portno",request.getServerPort());
		if(Strings.SERVERNAME == null || Strings.SERVERNAME.equals(""))
		
		Strings.SERVERNAME = request.getServerName();
		Strings.PORTNO = request.getServerPort();
		
		String url = "/WEB-INF/home.jsp";
		User user = new User();
		
		
		String reqFrom = request.getParameter("type");
		if(reqFrom == null){
			request.setAttribute("msg", "unknown source page");
		}
		else if(reqFrom.equalsIgnoreCase("spl")){
			
			
			String key = request.getParameter("link");
			System.out.println("The link key is : " + key);
			
			String recoEmail = UserDB.getRecoEmail(key);
			
			System.out.println("The recommender's email : " + recoEmail);
			
			if(recoEmail == null || recoEmail == ""){
				request.setAttribute("msg","This link has expired");
			}
			
			Cookie cookie = UserDB.createCookie("sponsoremail", recoEmail);
			Cookie cookie_bs = UserDB.createCookie("BonusStatus", "1");
			
			response.addCookie(cookie);
			response.addCookie(cookie_bs);
			
			System.out.println("Cookies have been added to the response header.");
			
			url = "/UserController?action=home";
			
		}
		if((user = (User)request.getSession().getAttribute("theUser")) != null){
		 if (reqFrom.equalsIgnoreCase("r")){
			url = "/WEB-INF/confirmr.jsp";
			
			
			EmailDetail recommendationEmail = new EmailDetail();
			recommendationEmail.setName(request.getParameter("fname"));
			recommendationEmail.setEmailFrom(request.getParameter("email"));
			recommendationEmail.setEmailTo(request.getParameter("emailF"));
			recommendationEmail.setMsg(request.getParameter("msg"));
			String key = MailUtils.genkey();
			String bonusLink = "http://"+Strings.SERVERNAME+":"+Strings.PORTNO+"/NBADProject/MailSendController"
					+ "?type=spl&link="+key;
			
			if(UserDB.updateBonus(key,user.getEmail())){
				if(MailUtils.sendEmail(recommendationEmail,bonusLink,Strings.MSGBODY," Recommendation")){
					request.setAttribute("msg", "Email sent successfully");
				}
				else
				{
					request.setAttribute("msg", "Error while sending email");
				}
			}	
		}
		else if(reqFrom.equalsIgnoreCase("c")){
			url = "/WEB-INF/confirmc.jsp";
			
			Contact contactEmail = new Contact();
			contactEmail.setToEmail(request.getParameter("email"));
			contactEmail.setToUsername(request.getParameter("name"));
			contactEmail.setMessage(request.getParameter("message"));
			
			if(MailUtils.sendContactEmail(contactEmail,user.getEmail(),user.getName())){
				request.setAttribute("msg", "Email sent successfully");
			}
			else
			{
				request.setAttribute("msg", "Error while sending email");
			}
		}
		}else
		{
			System.out.println("User session is null type r and type c will not be processed.");
			System.out.println("The Controller was run for request type: " + reqFrom );
		}
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}
	
	
	

	

	
	
}
