package nbad.assignment4;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SQLInjectionController
 */
public class SQLInjectionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SQLInjectionController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		
		String action = request.getParameter("action");
		
		if(action == null){
			url = "/UserController?action=home";
		}
		else if (action.equalsIgnoreCase("sqlinjection")){
			url = "/WEB-INF/sqlinjection.jsp";
			
		} else if (action.equalsIgnoreCase("execute")){
			
			int mode = Integer.parseInt(request.getParameter("mode"));
			System.out.println(mode + " is the mode");
			String whereClause = request.getParameter("whereclause");
			System.out.println(whereClause + " is the where clause");
			String output = nbad.utility.SQLInj.executeSQLINJQuery(whereClause, mode);
			
			request.setAttribute("msg", output);
			url = "/SQLInjectionController?action=display";
		} else if (action.equalsIgnoreCase("display")){
			url = "/WEB-INF/sqloutput.jsp";
		}
	
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
