package dync.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;

import dync.db.IssuePersistentManager;
import dync.model.Issue;

/**
 * Servlet implementation class IssueServlet
 */
@MultipartConfig
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
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.addHeader("Access-Control-Max-Age", "86400");
		String action_request = request.getParameter(REQ_ACTION);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		System.out.println(action_request);
		
		if( action_request == null || action_request.equals(ACTION_INSERT)){
			
			/*
			String savePath = "C:/DEVSNS/saveFile/" + issue_id ;
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
			*/
			multipart_action(action_request, request, response); // 액션 처리
		}else{
			request_action(action_request,request,response);
		}
		
		
		
		
	}
	private void request_action(String action,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "KSC5601"));
		response.setContentType("text/html;charset=euc-kr");
		
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
				JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "ok");
		        jsonArray.add(jsonObject);
		        out.write(jsonArray.toString());
			} else {
				JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "no");
		        jsonArray.add(jsonObject);
		        out.write(jsonArray.toString());
				//throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_EDIT)) {
			System.out.println("edit 요청");
			String issue_id = request.getParameter("ISSUE_ID");
			Issue issue = ipm.getIssue(Integer.parseInt(issue_id));
			request.setAttribute("issue", issue);
			String jspPath = "/jsp/dbTest.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(jspPath);
			dispatcher.forward(request, response);
		} else if (action.equals(ACTION_UPDATE)) {
			System.out.println("update 요청");
			Issue issue = makeIssueBean(request);
			if (ipm.updateIssue(issue)) {
				JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "ok");
		        jsonArray.add(jsonObject);
		        out.write(jsonArray.toString());
			} else {
				System.out.println("update 실패");

				JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "no");
		        jsonArray.add(jsonObject);
		        out.write(jsonArray.toString());
				// throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_GET_ISSUE)) {
			System.out.println("getIssue 요청");
			int issue_id = Integer.parseInt(request.getParameter("ISSUE_ID"));
			ArrayList<Issue> issueList = ipm.getIssueList();
			boolean flag = true;
			for(Issue issue : issueList){
				if(issue.getIssue_id() == issue_id){
					flag = false;
					break;
				}
				
			}
			if(flag){
				request.setAttribute("errorMessage", "유효하지 않은 ISSUE_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				return;
			}
			
			Issue issue = ipm.getIssue(issue_id);
			JSONArray json = new JSONArray();
			json.add(issue);
			out.print(json.toString());
			System.out.println(json);
		} else if (action.equals(ACTION_LIST)) {
			System.out.println("list 요청");
			ArrayList<Issue> issueList = ipm.getIssueList();
			JSONObject json = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(issueList);
			out.print(jsonArray.toString());
		}
		out.close();
	}
	private void multipart_action(String action, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		if (action.equals(ACTION_INSERT)) {
			System.out.println("insert 요청");
			
			final String path = "C:/DEVSNS/saveFile/" + request.getParameter("ISSUE_ID");
			File dir = new File(path);
			if (!dir.isDirectory()) {
				System.out.println("폴더를 생성 합니다.");
				if (!dir.mkdirs()) {
					System.out.println("폴더 생성 실패");
				}
			}
		    final Part filePart = request.getPart("UPLOAD");
		    final String fileName = getFileName(filePart);
		    String fullFileName = path + File.separator + fileName;
		    System.out.println(fileName);

		    OutputStream out = null;
		    InputStream filecontent = null;
		    
		    

		    try {
		        out = new FileOutputStream(new File(fullFileName));
		        filecontent = filePart.getInputStream();

		        int read = 0;
		        final byte[] bytes = new byte[1024];

		        while ((read = filecontent.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
		        //writer.println("New file " + fileName + " created at " + path);
		    } catch (FileNotFoundException fne) {
		        JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "no");
		        jsonArray.add(jsonObject);
		        writer.write(jsonArray.toString());

		    } finally {
		        if (out != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
		        /*
		        if (writer != null) {
		            writer.close();
		        }
		        */
		    }
			
			Issue issue = makeIssueBean(request);
			issue.setUpload(fullFileName);
			
			/*
			Issue issue = makeIssueBean(multi);
			PrintWriter out = response.getWriter();
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
				}
			} catch (Exception e) {
				System.out.print("예외 발생 : " + e);
			}
			*/
			if (ipm.insertIssue(issue)) {
				JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "ok");
		        jsonArray.add(jsonObject);
		        writer.write(jsonArray.toString());
			} else {
				JSONArray jsonArray = new JSONArray();
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("result", "no");
		        jsonArray.add(jsonObject);
		        writer.write(jsonArray.toString());
				/*
				System.out.println("insert 실패");
				request.setAttribute("errorMessage", "유효하지 않은 USER_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");
				*/
				// throw new ServletException("DB Query Error");
			}
			
			
		} 
		
		writer.close();

		
		
		
	}
	
	private static String getFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	
	private static String getValue(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
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
}
