package dync.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dync.db.TagPersistentManager;
import dync.db.UserPersistentManager;
import dync.model.Tag;
import dync.model.User;

/**
 * Servlet implementation class TagServlet
 */
@WebServlet("/TagServlet")
public class TagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String REQ_ACTION 	  = "action";
    private static final String ACTION_INSERT = "insert";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_LIST   = "list";
    
    TagPersistentManager tpm = new TagPersistentManager();
    UserPersistentManager upm = new UserPersistentManager();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF8"));
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("tag servlet 요청");
		String action = request.getParameter(REQ_ACTION);
		System.out.println(action);
		
		if(action == null){
			return;
		}
		
		if(action.equals(ACTION_INSERT)){
			System.out.println("insert 요청");
			
			Tag tag = makeTagbean(request);
			
			if(tag.getUser_id() == -1){
				System.out.println("유효하지 않는 user_id(user_session_check = -1 오류)");
				print_json_message(response, "result", "no");
				return;
			} 
				
			if(upm.checkUser(tag.getUser_id())){
				if(tag.getTag_name()==null || tag.getTag_name().equals("-1")){
					System.out.println("tag명을 입력해 주세요");
					print_json_message(response, "result", "no");
					return;
				}else if(tpm.checkTag(tag.getUser_id(), tag.getTag_name())){
					System.out.println("tag명이 존재합니다");
					print_json_message(response, "result", "no");
					return;
				}else{
					if(tpm.insertTag(tag)){
						print_json_message(response, "result", "ok");
					}else{
						System.out.println("tag insert 실패");
						print_json_message(response, "result", "no");
					}
				}
			}else{
				System.out.println("존재하지 않는 user_id[USER DB에 등록되어 있찌 않음]");
				print_json_message(response, "result", "no");
				return;
			}
		}else if(action.equals(ACTION_DELETE)){
			ArrayList<Tag> tagList = new ArrayList<>();
			String tag_name = request.getParameter("TAG_NAME");
			int user_id = 0;
			
			if(tag_name == null || tag_name.equals("")){
				System.out.println("tag_name을 입력해주세요");
				print_json_message(response, "result", "no");
				return;
			}
			
			try{
				user_id = user_session_check(request);
			}catch(Exception e){
				user_id = -1;
			}
			
			if(user_id == -1){
				System.out.println("유효하지 않은 USER_ID 세션 소멸");
				print_json_message(response, "result", "no");
				return;
			}
			
			tagList = tpm.getTagList(user_id);
			if(tagList.size() <=0){
				System.out.println("삭제 할 수 있는 태그가 없습니다. tagList.size = 0");
				print_json_message(response, "result", "no");
				return;
			}else{
				if(tpm.checkTag(user_id, tag_name)){
					if(tpm.deleteTag(user_id, tag_name)){
						print_json_message(response, "result", "ok");
						return;
					}else{
						System.out.println("delete 실패");
						print_json_message(response, "result", "no");
						return;
					}
				}else{
					System.out.println("존재하지 않는 TAG_NAME 입니다");
					print_json_message(response, "result", "no");
					return;
				}
			}
			
		}else if(action.equals(ACTION_LIST)){
			ArrayList<Tag> tagList = new ArrayList<>();
			int user_id = 0;
			try{
				user_id = user_session_check(request);
			}catch(Exception e){
				user_id = -1;
			}
			
			if(user_id == -1){
				System.out.println("유효하지 않은 USER_ID 세션 소멸");
				print_json_message(response, "result", "no");
				return;
			}
			
			tagList = tpm.getTagList(user_id);
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(tagList);
			out.write(jsonArray.toString());
		}
		out.close();
	}
	
	private int user_session_check(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session = request.getSession(true);
			User auth = (User) session.getAttribute("auth_session");
			return auth.getUser_id();
		} else {
			return -1;
		}
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
	
	private Tag makeTagbean(HttpServletRequest request){
		Tag tag = new Tag();
		
		int user_id = 0;
		try{
			user_id = user_session_check(request);
		}catch(Exception e){
			user_id = -1;
		}
		String tag_name = request.getParameter("TAG_NAME");
		
		if(tag_name == null || tag_name.equals("")){
			tag_name = "-1";
		}
		
		tag.setUser_id(user_id);
		tag.setTag_name(tag_name);
		
		
		return tag;
	}
	
	

}
