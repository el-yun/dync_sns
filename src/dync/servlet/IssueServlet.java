package dync.servlet;

import java.io.File;
import java.io.IOException;
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
		RequestDispatcher rd = null;
		File file = null;
		String savePath = "C:/Users/Administrator/Documents/GitHub/dync_sns/saveFile"; // <-
																						// �슂湲곕��
																						// 諛붽퓭二쇰㈃
																						// �떎�슫諛쏅뒗
																						// 寃쎈줈媛�
		Enumeration files = null;
		String action = null;
		String strIssue_id = request.getParameter(Issue.ISSUE_ID);
		int maxSize = 5 * 1024 * 1024; // 理쒕� �뾽濡쒕뱶 �뙆�씪 �겕湲� 5MB(硫붽�)濡� �젣�븳
		try {
			multi = new MultipartRequest(request, savePath, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			fileName = multi.getFilesystemName("UPLOAD"); // �뙆�씪�쓽 �씠由� �뼸湲�

			files = multi.getFileNames();
			String name = (String) files.nextElement();
			file = multi.getFile(name);
			if (fileName == null) { // �뙆�씪�씠 �뾽濡쒕뱶 �릺吏� �븡�븯�쓣�븣
				System.out.print("�뙆�씪 �뾽濡쒕뱶 �릺吏� �븡�븯�쓬");
			} else { // 한글 인코딩 테스
				// System.out.println("File Name  : " + fileName);
				action = multi.getParameter(REQ_ACTION);
			}// else
		} catch (Exception e) {
			System.out.print("�삁�쇅 諛쒖깮 : " + e);
		}
		System.out.println("IssueServlet �떎�뻾");
		// System.out.println(action);
		if (action == null) {
			System.out.println("action = null");
			return;
		}

		if (action.equals(ACTION_INSERT)) {
			System.out.println("insert �슂泥�");
			Issue issue = makeIssueBean(multi);
			PrintWriter out = response.getWriter();
			if (ipm.insertIssue(issue)) {
				gotoJsp(request, response, "/jsp/dbTest.jsp");
			} else {
				System.out.println("insert �떎�뙣");
				request.setAttribute("errorMessage", "�쑀�슚�븯吏� �븡�� USER_ID");
				gotoJsp(request, response, "/jsp/errorPage.jsp");

				// throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_DELETE)) {
			System.out.println("delete �슂泥�");
			String columnName = multi.getParameter("COLUMN_NAME");
			int columnValue = Integer.parseInt(multi
					.getParameter("COLUMN_VALUE"));
			if (ipm.deleteIssue(columnName, columnValue)) {
				gotoJsp(request, response, "/jsp/dbTest.jsp");
			} else {
				throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_EDIT)) {
			System.out.println("edit �슂泥�");
			String issue_id = multi.getParameter("ISSUE_ID");
			Issue issue = ipm.getIssue(Integer.parseInt(issue_id));
			request.setAttribute("issue", issue);
			String jspPath = "/jsp/dbTest.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(jspPath);
			dispatcher.forward(request, response);
		} else if (action.equals(ACTION_UPDATE)) {
			System.out.println("update �슂泥�");
			Issue issue = makeIssueBean(multi);
			if (ipm.updateIssue(issue)) {
				gotoJsp(request, response, "/jsp/dbTest.jsp");
			} else {
				System.out.println("update �떎�뙣");

				gotoJsp(request, response, "/jsp/errorPage.jsp");
				// throw new ServletException("DB Query Error");
			}
		} else if (action.equals(ACTION_GET_ISSUE)) {
			System.out.println("getIssue �슂泥�");
			int issue_id = Integer.parseInt(request.getParameter("ISSUE_ID"));
			Issue issue = ipm.getIssue(issue_id);
			JSONObject json = new JSONObject();
			json.put(Issue.ISSUE_ID, issue.getIssue_id());
			json.put(Issue.USER_ID, issue.getUser_id());
			json.put(Issue.TYPE, issue.getType());
			json.put(Issue.SUBJECT, issue.getSubject());
			json.put(Issue.CONTENTS, issue.getContents());
			json.put(Issue.DISPLAY, issue.isDisplay());
			json.put(Issue.RECOMMAND, issue.getRecommand());
			json.put(Issue.REG_DATE, issue.getReg_date());

			request.setAttribute("json", json);
			String jspPath = "/jsp/dbTest.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(jspPath);
			dispatcher.forward(request, response);
			System.out.println(json);
		} else if (action.equals(ACTION_LIST)) {
			System.out.println("list �슂泥�");
			ArrayList<Issue> issueList = ipm.getIssueList();
			request.setAttribute("issueList", issueList);

			gotoJsp(request, response, "/jsp/dbTest.jsp");
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

}
