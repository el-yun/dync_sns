package dync.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dync.lucene.demo.IndexSearchCode;
import dync.lucene.demo.IndexSearchIssue;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dync.db.CodePersistentManager;
import dync.db.IssuePersistentManager;
import dync.model.Code;
import dync.model.Issue;

public class SearchServlet extends HttpServlet {
	
	private IssuePersistentManager ipm = new IssuePersistentManager();
	private CodePersistentManager cpm = new CodePersistentManager();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ArrayList<String> arrOverLabResult = new ArrayList<String>();
		ArrayList<String> arrOverLabResultCode = new ArrayList<String>();
		ArrayList<String> arrResult = new ArrayList<String>();
		ArrayList<String> arrResultCode = new ArrayList<String>();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "KSC5601"));
		
		String keyword = request.getParameter("search_Word");
		System.out.println(keyword);
		IndexSearchIssue iSearch = new IndexSearchIssue();
		IndexSearchCode iSearchCdoe = new IndexSearchCode();
		arrOverLabResult = iSearch.getData(keyword);
		arrOverLabResultCode = iSearchCdoe.getData(keyword);
		System.out.println("너냐:"+arrOverLabResult);
		System.out.println("ㅋㅋ:"+arrOverLabResultCode);
		/******************************************************/
		arrResult = removeOverLab(arrOverLabResult); //중복요소 제거
		arrResultCode = removeOverLab(arrOverLabResultCode);
		/******************************************************/
		ArrayList<Issue> issueList = ipm.getIssueList();
		ArrayList<Code> codeList = cpm.getCodeList();
		ArrayList<Issue> searchIssue = new ArrayList<Issue>();	
		ArrayList<Code> searchCode = new ArrayList<Code>();	
		int tUserId;
		int tCodeId;

		for (int j = 0; j < arrResult.size(); j++) {
			for (int i = 0; i < issueList.size(); i++){
				tUserId = Integer.valueOf(arrResult.get(j));
				if (tUserId == -1) {

				} else if (tUserId == Integer.valueOf(issueList.get(i)
						.getIssue_id())) {
					searchIssue.add(issueList.get(i));
				}
			}
		}
		for (int j = 0; j < arrResultCode.size(); j++) {
			tCodeId = Integer.valueOf(arrResultCode.get(j));
			if (tCodeId == -1) {
			}
			else
			searchCode.add(cpm.getCode(Integer.valueOf(arrOverLabResultCode.get(j))));
		}
	
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(searchIssue);
		jsonArray.addAll(searchCode);
		out.print(jsonArray.toString());
		///////////////////////////////////////////////////////
		out.close();
	}

	private ArrayList<String> removeOverLab(ArrayList<String> arrOverLabResult){
		ArrayList<String> arrResult =new ArrayList<String>();
		for(int i = 0; i < arrOverLabResult.size() ; i++){
			if(i==0) arrResult.add(arrOverLabResult.get(i));
			else{
				for(int j = 0 ; j < arrResult.size(); j++){
					if(Integer.valueOf(arrResult.get(j))!=Integer.valueOf(arrOverLabResult.get(i))){
						arrResult.add(arrOverLabResult.get(i));
						break;
					}
				}
			}
		}
		return arrResult;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
