package dync.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dync.db.CodePersistentManager;
import dync.model.Code;
import dync.model.Issue;

/**
 * Servlet implementation class CodeServlet
 */
@WebServlet("/CodeServlet")
public class CodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REQ_ACTION = "action";
	private static final String ACTION_LIST = "list";
	private static final String ACTION_TAG_VIEW = "tag_view";
	private static final String ALL = "all";
	private static final String ACTION_INSERT = "insert";
	private static final String ACTION_EDIT = "edit";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_DELETE = "delete";
	
	CodePersistentManager cpm = new CodePersistentManager();
	
	/**
	 * Default constructor.
	 */
    public CodeServlet() {
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
		System.out.println("CodeServlet 실행");
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
			Code code = makeCodeBean(request);
			PrintWriter out = response.getWriter();
			if(cpm.insertCode(code)){
				String jspPath = "/jsp/dbTest.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);
				dispatcher.forward(request, response);
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
			if(cpm.deleteCode(columnName, columnValue)){
				String jspPath = "/jsp/dbTest.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);
				dispatcher.forward(request, response);
			}else{
				throw new ServletException("DB Query Error");
			}
		}
	}
	
	private Code makeCodeBean(HttpServletRequest request)
	{
		String strCode_id = request.getParameter(Code.CODE_ID);
		if(strCode_id == null)
		{
			return null;
		}
		int code_id = Integer.parseInt(strCode_id);
		
		int code_repository = Integer.parseInt(request.getParameter(Code.CODE_REPOSITORY));
		String code_subject = request.getParameter(Code.CODE_SUBJECT);
		String base_language = request.getParameter(Code.BASE_LANGUAGE);
		String code_contents = request.getParameter(Code.CODE_CONTENTS);
		int revision = Integer.parseInt(request.getParameter(Code.REVISION));
		boolean using = Boolean.parseBoolean(request.getParameter(Code.USING));
		//String reg_date = request.getParameter(Issue.REG_DATE);
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// get current date time with Date()
		//Date date = new Date();
		//System.out.println(dateFormat.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		
		String reg_date = dateFormat.format(cal.getTime());
		
		Code code = new Code(code_id,code_repository,code_subject,base_language,code_contents,revision,using,reg_date);		
		return code;
	}

}
