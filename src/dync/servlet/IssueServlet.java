package dync.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dync.db.PersistentManager;
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
	private PersistentManager pm = new PersistentManager();

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
		RequestDispatcher rd = null;
		File file = null;
		String savePath = "C:/Users/Administrator/Documents/GitHub/dync_sns/saveFile"; // <-
																						// 요기를
																						// 바꿔주면
																						// 다운받는
																						// 경로가
		Enumeration files = null;
		String action = null;
		String strIssue_id = request.getParameter(Issue.ISSUE_ID);
		int maxSize = 5 * 1024 * 1024; // 최대 업로드 파일 크기 5MB(메가)로 제한
		try {
			multi = new MultipartRequest(request, savePath, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			fileName = multi.getFilesystemName("UPLOAD"); // 파일의 이름 얻기

			files = multi.getFileNames();
			String name = (String) files.nextElement();
			file = multi.getFile(name);
			if (fileName == null) { // 파일이 업로드 되지 않았을때
				System.out.print("파일 업로드 되지 않았음");
			} else { // 파일이 업로드 되었을때
				//System.out.println("File Name  : " + fileName);
				action = multi.getParameter(REQ_ACTION);
			}// else
		} catch (Exception e) {
			System.out.print("예외 발생 : " + e);
		}
		System.out.println("IssueServlet 실행");
		// System.out.println(action);
		if (action == null) {
			System.out.println("action = null");
			return;
		}

		if (action.equals(ACTION_INSERT)) {
			System.out.println("insert 요청");
			Issue issue = makeIssueBean(multi);

			PrintWriter out = response.getWriter();
			if (pm.insertIssue(issue)) {
				String jspPath = "/jsp/dbTest.jsp";
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher(jspPath);
				dispatcher.forward(request, response);
			} else {
				/*
				 * String jspPath = "/jsp/errorPage.jsp"; RequestDispatcher
				 * dispatcher =
				 * getServletContext().getRequestDispatcher(jspPath);
				 * dispatcher.forward(request, response);
				 */
				throw new ServletException("DB Query Error");
			}
		}
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

		Issue issue = new Issue(issue_id, user_id, type, subject, contents, display, recommand, reg_date, upload);

		return issue;
	}

}
