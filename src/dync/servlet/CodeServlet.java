package dync.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dync.db.CodePersistentManager;
import dync.db.UserPersistentManager;
import dync.model.Code;
import dync.model.User;
import dync.util.ConvertChar;

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
	private static final String ACTION_CODE = "code";
	
	CodePersistentManager cpm = new CodePersistentManager();
	UserPersistentManager upm = new UserPersistentManager();
	private PrintWriter out;
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
		//response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "KSC5601"));
		out.flush();
		String action = request.getParameter(REQ_ACTION);
		if(action == null) {
			System.out.println("action = null");
			return;
		}
		
		if(action.equals(ACTION_INSERT))
		{
			System.out.println("insert 요청");
			Code code = makeCodeBean(request);
			if (code != null && cpm.insertCode(code)) {
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "ok");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
				
			} else {
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "no");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
				// throw new ServletException("DB Query Error");
			}
		}else if(action.equals(ACTION_DELETE))
		{
			
			System.out.println("delete 요청");
			String columnName = request.getParameter("COLUMN_NAME");
			int columnValue = Integer.parseInt(request.getParameter("COLUMN_VALUE"));
			if(cpm.deleteCode(columnName, columnValue)){
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "ok");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
			}else{
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "no");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
			}
		}else if(action.equals(ACTION_UPDATE))
		{
			System.out.println("update 요청");
			Code code = makeCodeBean(request);
			if(cpm.updateCode(code)){
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "ok");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
			}else{
				
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "no");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
			}
		}else if(action.equals(ACTION_CODE)){
			System.out.println("getCode 요청");
			int code_id = Integer.parseInt(request.getParameter("CODE_ID"));
			
			if(checkCode(code_id)){
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "no");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
				System.out.println("존재하지 않는 CODE_ID");
			}
			
			Code code = cpm.getCode(code_id);
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(code);
			out.print(jsonArray.toString());
			
		}else if(action.equals(ACTION_LIST)){
			System.out.println("codeList 요청");
			JSONArray jsonArray = new JSONArray();
			String user_id_String = request.getParameter("USER_ID");
			int user_id = 0;
			if(user_id_String.equals("")){
				System.out.println("USER_ID를 입력하세요");
				ArrayList<Code> codeList = cpm.getCodeList();
				
				jsonArray.addAll(codeList);
				
				out.print(jsonArray.toString());
				out.close();
				return;
			}else{
				user_id = Integer.parseInt(user_id_String);
			}
			
			if(upm.checkUser(user_id)){
				User user = upm.getAuth("USER_ID", user_id);
				long code_repository = user.getCode_repository();
				jsonArray.addAll(cpm.getCodeList("CODE_REPOSITORY", code_repository));
				out.print(jsonArray.toString());
			}else{
				System.out.println("해당하는 유저 없음");
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("result", "no");
				jsonArray.add(jsonObject);
				
				out.print(jsonArray.toString());
			}
		}
		out.close();
	}

	private Code makeCodeBean(HttpServletRequest request) {
		int code_id = 0;
		User Auth = user_session_check(request);
		if (Auth != null) {
			ConvertChar cc = new ConvertChar("utf-8");
			String GetCode = request.getParameter(Code.CODE_ID);
			if(GetCode != ""){code_id = Integer.parseInt(GetCode);}

			long code_repository = Auth.getCode_repository();
			String code_subject = request.getParameter(Code.CODE_SUBJECT);
			String base_language = "text";
			String code_contents = cc.encode(request.getParameter(Code.CODE_CONTENTS));
			int revision = 0;
			boolean using = Boolean.parseBoolean(request.getParameter(Code.USING));
			
			// String base_language = request.getParameter(Code.BASE_LANGUAGE); 
			// int revision = Integer.parseInt(request.getParameter(Code.REVISION));
			// String reg_date = request.getParameter(Issue.REG_DATE);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String reg_date = dateFormat.format(cal.getTime());

			Code code = new Code(code_id, code_repository, code_subject,
					base_language, code_contents, revision, using, reg_date);
			return code;
		}
		return null;
	}
		
	private User user_session_check(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session = request.getSession(true);
			User auth = (User) session.getAttribute("auth_session");
			return auth;
		} else {
			return null;
		}
	}
		
	private void gotoJsp(HttpServletRequest request,
			HttpServletResponse response, String path) throws ServletException,
			IOException {
		String jspPath = path;
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}
	
	private boolean checkCode(int code_id){
		ArrayList<Code> codeList = cpm.getCodeList();
		boolean flag = true;
		for(Code code : codeList){
			if(code.getCode_id() == code_id){
				flag = false;
				break;
			}
		}
		return flag;
	}

}
