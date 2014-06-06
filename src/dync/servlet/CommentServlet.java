package dync.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dync.db.CodePersistentManager;
import dync.db.CommentPersistentManager;
import dync.db.IssuePersistentManager;
import dync.db.UserPersistentManager;
import dync.model.Comment;
import dync.model.Issue;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REQ_ACTION = "action";

	private static final String ACTION_LIST = "list";
	private static final String ACTION_TAG_VIEW = "tag_view";
	private static final String ALL = "all";
	private static final String ACTION_INSERT = "insert";
	private static final String ACTION_EDIT = "edit";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_DELETE = "delete";
	
	CommentPersistentManager ctpm = new CommentPersistentManager();
	IssuePersistentManager ipm = new IssuePersistentManager();
	UserPersistentManager upm = new UserPersistentManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("comment servlet 요청");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF8"));
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String action = request.getParameter(REQ_ACTION);
		
		
		if(action == null){
			return ;
		}
		
		if(action.equals(ACTION_INSERT)){
			System.out.println("insert 요청");
			Comment comment = makeCommentBean(request);
			if(comment.getComment_contents()==null || comment.getComment_contents().equals("")){
				System.out.println("comment 내용을 입력해 주세요");
				print_json_message(response, "result", "no");
				return;
			}
			
			int issue_id = comment.getIssue_id();
			if(ipm.checkIssue(issue_id)){
				int user_id = ipm.getIssue(issue_id).getUser_id();
				comment.setUser_id(user_id);
				
				if(ctpm.insertComment(comment)){
					print_json_message(response, "result", "ok");
				}else{
					print_json_message(response, "result", "no");
				}
			}else{
				System.out.println("존재하지 않는 ISSUE_ID 입니다.");
				print_json_message(response, "result", "no");
			}
			
		}else if(action.equals(ACTION_DELETE)){
			
		}else if(action.equals(ACTION_UPDATE)){
			
		}else if(action.equals(ACTION_LIST)){
			System.out.println("list 출력 요청");
			ArrayList<Comment> commentList = new ArrayList<>();
			String issue_id_String = request.getParameter("ISSUE_ID");
			if(issue_id_String == null){
				System.out.println("ISSUE_ID를 입력하세요");
				print_json_message(response, "result", "no");
				return;
			}
			int issue_id = Integer.parseInt(request.getParameter("ISSUE_ID"));
			
			if(ipm.checkIssue(issue_id)){
				commentList = ctpm.getCommentList("ISSUE_ID", issue_id);
				JSONArray jsonArray = new JSONArray();
				jsonArray.addAll(commentList);
				out.print(jsonArray.toString());
			}else{
				System.out.println("존재하지 않는 ISSUE_ID");
				print_json_message(response, "result", "no");
			}
			
			
		}
		
		out.close();
	}
	
	private Comment makeCommentBean(HttpServletRequest request){
		Comment comment = new Comment();
		String issue_id = request.getParameter("ISSUE_ID");
		String comment_contents = request.getParameter("COMMENT_CONTENTS");
		
		if(issue_id == null || issue_id.equals("")){
			issue_id = "-1";
		}
		
		comment.setIssue_id(Integer.parseInt(issue_id));
		comment.setComment_contents(comment_contents);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		comment.setReg_date(dateFormat.format(cal.getTime()));
		
		
		return comment;
	}
	private void print_json_message(HttpServletResponse response, String key,String value) throws UnsupportedEncodingException, IOException{
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF8"));
		response.setContentType("text/html;charset=utf-8");
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);
		jsonArray.add(jsonObject);
		out.write(jsonArray.toString());
		out.close();
	}

}
