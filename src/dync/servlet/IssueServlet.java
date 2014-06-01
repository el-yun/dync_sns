package dync.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dync.db.IssuePersistentManager;
import dync.model.Issue;

/**
 * Servlet implementation class IssueServlet
 */
public class IssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REQ_ACTION = "action";

	private static final String ACTION_LIST = "list";
	private static final String ACTION_TAG_VIEW = "tag_view";
	private static final String ALL = "all";
	private static final String ACTION_INSERT = "insert";
	private static final String ACTION_EDIT = "edit";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_DELETE = "delete";

	MultipartRequest multi;
	String fileName = "";
	private PrintWriter out;
	private static final String ACTION_GET_ISSUE = "get_issue";

	private IssuePersistentManager ipm = new IssuePersistentManager();

	/**
	 * Default constructor.
	 */
	public IssueServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset=euc-kr");
		out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "KSC5601"));
		String action_request = request.getParameter(REQ_ACTION);
		System.out.println(action_request);
		if(action_request == null){
			String savePath = "C:/DEVSNS/saveFile";
			File dir = new File(savePath);
			if (!dir.isDirectory()) {
				System.out.println("폴더를 생성 합니다.");
				if (!dir.mkdirs()) {
					System.out.println("폴더 생성 실패");
				}
			}
			
			
			int maxSize = 5 * 1024 * 1024; // 최대 업로드 파일 크기 5MB(메가)로 제한
			multi = new MultipartRequest(request, savePath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());
			action_request = multi.getParameter(REQ_ACTION);
			System.out.println("multi" + multi.getParameter(REQ_ACTION));
			System.out.println("request"+ request.getParameter(REQ_ACTION));
			multipart_action(action_request, request, response); // 액션 처리
		}else{
			request_action(action_request,request,response);
		}
		
		
		
		
	}
	private void request_action(String action,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		// System.out.println(action);
		if (action == null) {
			System.out.println("action = null");
			return;
		}

		if (action.equals(ACTION_DELETE)) {
			System.out.println("delete 요청");
			String columnName = request.getParameter("COLUMN_NAME");
			int columnValue = Integer.parseInt(request
					.getParameter("COLUMN_VALUE"));
			if (ipm.deleteIssue(columnName, columnValue)) {
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "success");
				jsonArray.add(jsonObject);
				//request.setAttribute("RESULT", jsonArray.toString());
				//gotoJson(request, response);
				out.print(jsonArray.toString());
			} else {
				String errorMessage = "해당 요소가 존재하지 않아 삭제할 수 없습니다.";
				request.setAttribute("errorMessage", errorMessage);
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				
				//throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_EDIT)) {
			System.out.println("edit 요청");
			int issue_id = Integer.parseInt(request.getParameter("ISSUE_ID"));
			
			if(checkIssue(issue_id)){
				request.setAttribute("errorMessage", "유효하지 않은 ISSUE_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				return;
			}
			
			Issue issue = ipm.getIssue(issue_id);
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(issue);
			
			//request.setAttribute("RESULT", jsonArray.toString());
			//gotoJson(request, response);
			out.print(jsonArray.toString());
			
		} else if (action.equals(ACTION_UPDATE)) {
			System.out.println("update 요청");
			Issue issue = makeIssueBean(request);
			if (ipm.updateIssue(issue)) {
				
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "success");
				jsonArray.add(jsonObject);
				//request.setAttribute("RESULT", jsonArray.toString());
				//gotoJson(request, response);
				out.print(jsonArray.toString());
			} else {
				
				request.setAttribute("errorMessage", "update 실패 \nISSUE_ID 또는 요소를 확인해 주세요");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				// throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_GET_ISSUE)) {
			System.out.println("getIssue 요청");
			int issue_id = Integer.parseInt(request.getParameter("ISSUE_ID"));
			
			if(checkIssue(issue_id)){
				request.setAttribute("errorMessage", "유효하지 않은 ISSUE_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				return;
			}
			
			Issue issue = ipm.getIssue(issue_id);
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(issue);
			//request.setAttribute("RESULT", json.toString());
			//gotoJson(request, response);
			
			out.print(jsonArray.toString());
		} else if (action.equals(ACTION_LIST)) {
			System.out.println("list 요청");
			ArrayList<Issue> issueList = ipm.getIssueList();
			JSONObject json = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(issueList);
			//request.setAttribute("RESULT", jsonArray.toString());
			//gotoJson(request, response);
			out.print(jsonArray.toString());
		}
		out.close();
		
	}
	private void multipart_action(String action, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (action.equals(ACTION_INSERT)) {
			Issue issue = makeIssueBean(multi);
			
			if(!checkIssue(issue.getIssue_id())){
				request.setAttribute("errorMessage", "ISSUE 삽입 불가 \n 중복된 ISSUE_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				return;
			}
			try {
				Enumeration files = null;
				File file = null;
				fileName = multi.getFilesystemName("UPLOAD"); // 파일의 이름 얻기
				if(fileName.equals(null)){
					System.out.println("업로드된 파일 없음");
					
				}else{
					files = multi.getFileNames();
					String name = (String) files.nextElement();
					file = multi.getFile(name);
					issue.setUpload("C:/DEVSNS/saveFile/" + fileName);//실제 서버 경로로 파일 저장
				}
			} catch (Exception e) {
				System.out.print("예외 발생 : " + e);
			}
			
			if (ipm.insertIssue(issue)) {
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				
				jsonObject.put("result", "success");
				jsonArray.add(jsonObject);
				//request.setAttribute("RESULT", jsonArray.toString());
				//gotoJson(request, response);
				
				out.print(jsonArray.toString());
				
				
				
			} else {
				request.setAttribute("errorMessage", "유효하지 않은 USER_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				return;
				// throw new ServletException("DB Query Error");
			}
		} 
		out.close();
	}

	private Issue makeIssueBean(HttpServletRequest request) {
		String strIssue_id = request.getParameter(Issue.ISSUE_ID);
		if (strIssue_id == null) {
			return null;
		}
		int issue_id = Integer.parseInt(strIssue_id);
		int user_id = Integer.parseInt(request.getParameter(Issue.USER_ID));
		String type = request.getParameter(Issue.TYPE);
		String subject = request.getParameter(Issue.SUBJECT);
		String contents = request.getParameter(Issue.CONTENTS);
		boolean display = Boolean.parseBoolean(request
				.getParameter(Issue.DISPLAY));
		int recommand = Integer.parseInt(request.getParameter(Issue.RECOMMAND));
		// String reg_date = request.getParameter(Issue.REG_DATE);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		String reg_date = dateFormat.format(cal.getTime());
		String upload = fileName;

		Issue issue = new Issue(issue_id, user_id, type, subject, contents,
				display, recommand, reg_date, upload);

		return issue;
	}
	private Issue makeIssueBean(MultipartRequest multi) {
		String strIssue_id = multi.getParameter(Issue.ISSUE_ID);
		if (strIssue_id == null) {
			return null;
		}
		int issue_id = Integer.parseInt(strIssue_id);
		int user_id = Integer.parseInt(multi.getParameter(Issue.USER_ID));
		String type = multi.getParameter(Issue.TYPE);
		String subject = multi.getParameter(Issue.SUBJECT);
		String contents = multi.getParameter(Issue.CONTENTS);
		boolean display = Boolean.parseBoolean(multi
				.getParameter(Issue.DISPLAY));
		int recommand = Integer.parseInt(multi.getParameter(Issue.RECOMMAND));
		// String reg_date = request.getParameter(Issue.REG_DATE);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// get current date time with Date()
		// Date date = new Date();
		// System.out.println(dateFormat.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();

		String reg_date = dateFormat.format(cal.getTime());
		String upload = fileName;

		Issue issue = new Issue(issue_id, user_id, type, subject, contents,
				display, recommand, reg_date, upload);

		return issue;
	}

	private void gotoJsp(HttpServletRequest request,
			HttpServletResponse response, String path) throws ServletException,
			IOException {
		String jspPath = path;
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}
	
	private void gotoJson(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String jspPath = "/jsp/output_json.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}
	private boolean checkIssue(int issue_id){
		ArrayList<Issue> issueList = ipm.getIssueList();
		boolean flag = true;
		for(Issue issue : issueList){
			if(issue.getIssue_id() == issue_id){
				flag = false;
				break;
			}
		}
		return flag;
	}
}
