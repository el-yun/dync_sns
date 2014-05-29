package dync.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dync.db.RepositoryPersistentManager;
import dync.db.UserPersistentManager;
import dync.model.Code_Repository;
import dync.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String REQ_ACTION = "action";

	private static final String ACTION_LIST = "list";
	private static final String ACTION_TAG_VIEW = "tag_view";
	private static final String ALL = "all";
	private static final String ACTION_INSERT = "insert";
	private static final String ACTION_EDIT = "edit";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_DELETE = "delete";
	
	UserPersistentManager upm = new UserPersistentManager();
	RepositoryPersistentManager rpm = new RepositoryPersistentManager();
	/**
	 * Default constructor.
	 */
    public UserServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException
	{
		System.out.println("UserServlet 실행");
		String contextPath = getServletContext().getContextPath();
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter(REQ_ACTION);
		
		if(action == null) {
			System.out.println("action = null");
			return;
		}
		
		if(action.equals(ACTION_INSERT))
		{
			System.out.println("insert 요청");
			User user = makeUserBean(request);
			PrintWriter out = response.getWriter();
			if(upm.insertUser(user)){
				Random r = new Random();
				r.setSeed(System.currentTimeMillis());
				int repository_id = (int)(r.nextDouble()*100000000);
				Code_Repository cr = new Code_Repository(repository_id,user.getUser_id());
				if(rpm.insertRepository(cr)){
					String jspPath = "/jsp/dbTest.jsp";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);
					dispatcher.forward(request, response);
				}else{
					throw new ServletException("DB Query Error");
				}
				
			}else {
				/*
				String jspPath = "/jsp/errorPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);
				dispatcher.forward(request, response);
				*/
				throw new ServletException("DB Query Error");
			}
		}else if(action.equals(ACTION_DELETE))
		{
			
			System.out.println("delete 요청");
			String columnName = request.getParameter("COLUMN_NAME");
			int columnValue = Integer.parseInt(request.getParameter("COLUMN_VALUE"));
			if(upm.deleteUser(columnName, columnValue)){
				String jspPath = "/jsp/dbTest.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);
				dispatcher.forward(request, response);
			}else{
				throw new ServletException("DB Query Error");
			}
		}
	}
	
	private User makeUserBean(HttpServletRequest request)
	{
		String strUser_id = request.getParameter(User.USER_ID);
		if(strUser_id == null)
		{
			return null;
		}
		int user_id = Integer.parseInt(strUser_id);
		
		
		String user_naverhash = request.getParameter(User.USER_NAVERHASH);
		String user_name = request.getParameter(User.USER_NAME);
		String user_description = request.getParameter(User.USER_DESCRIPTION);
		int code_repository = Integer.parseInt(request.getParameter(User.CODE_REPOSITORY));
		
		User user = new User(user_id,user_naverhash,user_name,user_description,code_repository);
		
		return user;
	}
	
	

}
