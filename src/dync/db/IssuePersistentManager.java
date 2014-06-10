package dync.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import dync.model.Comment;
import dync.model.Issue;
import dync.util.StringCut;

public class IssuePersistentManager extends ConnectDB {
	public int returnid = 0;
	
	public boolean insertIssue(Issue issue){
		connect();
		String sql = "insert into ISSUE(USER_ID,TYPE,SUBJECT,CONTENTS,DISPLAY,RECOMMAND,TAG,REG_DATE,UPLOAD)" +
					"values(?,?,?,?,?,?,?,?,?)";
		try{
			StringCut Subject = new StringCut(issue.getContents(), 40, "UTF-8");
			Subject.nl2no();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, issue.getUser_id());
			pstmt.setString(2, issue.getType());
			pstmt.setString(3, Subject.result);
			pstmt.setString(4, issue.getContents());
			if(issue.isDisplay()) pstmt.setInt(5, 1);
			else pstmt.setInt(5, 0);
			pstmt.setInt(6, issue.getRecommand());
			pstmt.setString(7, issue.getTag());
			pstmt.setTimestamp(8, Timestamp.valueOf(issue.getReg_date()));
			pstmt.setString(9, issue.getUpload());
			pstmt.executeUpdate();
			get_insert_id(pstmt);
			System.out.println(issue.getContents());
		}catch(SQLException e){
			System.out.println(pstmt.toString());
			e.printStackTrace();
			return false;
		}finally{
			disconnect();
		}
		return true;
	}

	public int getAutoId(){
		connect();
		int get_id = 0;
		String sql = "SHOW TABLE STATUS FROM `dyncdb` LIKE 'ISSUE'";
		try{
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			get_id = rs.getInt("Auto_increment");
			
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return get_id - 1;
		
	}
	public void get_insert_id(PreparedStatement pstmt){
		ResultSet rs;
		try {
			rs = pstmt.getGeneratedKeys();
			if (rs.next()){
				returnid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean deleteIssue(String columnName, int columnValue){
		connect();
		
		String sql = "delete from ISSUE where " + columnName + "=?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, columnValue);
			pstmt.executeUpdate();
			
			System.out.println(pstmt.toString());
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			disconnect();
		}
		return true;
	}
	
	public Issue getIssue(int issue_id){
		connect();
		
		String sql = "select * from ISSUE where ISSUE_ID=?";
		Issue issue = new Issue();
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, issue_id);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			issue.setIssue_id(rs.getInt("ISSUE_ID"));
			issue.setUser_id(rs.getInt("USER_ID"));
			issue.setType(rs.getString("TYPE"));
			issue.setSubject(rs.getString("SUBJECT"));
			issue.setContents(rs.getString("CONTENTS"));
			if(rs.getInt("DISPLAY")==1) issue.setDisplay(true);
			else issue.setDisplay(false);
			issue.setRecommand(rs.getInt("RECOMMAND"));
			issue.setTag(rs.getString("TAG"));
			issue.setReg_date(rs.getString("REG_DATE"));
			issue.setUpload(rs.getString("UPLOAD"));
			
		}catch (SQLException e) {
			System.out.println("존재하지 않는 ISSUE_ID");
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		
		return issue;
	}
	
	public boolean updateIssue(Issue issue){
		connect();
		
		String sql = "update ISSUE set USER_ID=?,TYPE=?,SUBJECT=?,CONTENTS=?,DISPLAY=?,RECOMMAND=?,TAG=?,REG_DATE=?,UPLOAD=? where ISSUE_ID=?";
		
		
		try{

			StringCut Subject = new StringCut(issue.getContents(), 40, "UTF-8");
			Subject.nl2no();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, issue.getUser_id());
			pstmt.setString(2, issue.getType());
			pstmt.setString(3, Subject.result);
			pstmt.setString(4, issue.getContents());
			if(issue.isDisplay()) pstmt.setInt(5, 1);
			else pstmt.setInt(5, 0);
			pstmt.setInt(6, issue.getRecommand());
			pstmt.setString(7, issue.getTag());
			pstmt.setTimestamp(8, Timestamp.valueOf(issue.getReg_date()));
			pstmt.setString(9, issue.getUpload());
			pstmt.setInt(10, issue.getIssue_id());
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		
		return true;
	}
	
	public ArrayList<Issue> getIssueList(){
		connect();
		ArrayList<Issue> issueList = new ArrayList<Issue>();
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		
		String sql = "select * from ISSUE order by REG_DATE DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			CommentPersistentManager ctpm = new CommentPersistentManager();
			while(rs.next()) {
				Issue issue = new Issue();
				commentList = ctpm.getCommentList("comments.ISSUE_ID", rs.getInt("ISSUE_ID"));
				issue.setIssue_id(rs.getInt("ISSUE_ID"));
				issue.setUser_id(rs.getInt("USER_ID"));
				issue.setType(rs.getString("TYPE"));
				issue.setSubject(rs.getString("SUBJECT"));
				issue.setContents(rs.getString("CONTENTS"));
				if(rs.getInt("DISPLAY")==1) issue.setDisplay(true);
				else issue.setDisplay(false);
				issue.setRecommand(rs.getInt("RECOMMAND"));
				issue.setTag(rs.getString("TAG"));
				issue.setReg_date(rs.getString("REG_DATE"));
				issue.setUpload(rs.getString("UPLOAD"));
				if(commentList != null) issue.setCommentList(commentList);
				issueList.add(issue);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return issueList;
		
	}
	
	public ArrayList<Issue> getIssueList(String key,int value){
		connect();
		ArrayList<Issue> issueList = new ArrayList<Issue>();
		
		String sql = "select * from ISSUE where "+ key +"=?" + "order by REG_DATE DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Issue issue = new Issue();
				
				issue.setIssue_id(rs.getInt("ISSUE_ID"));
				issue.setUser_id(rs.getInt("USER_ID"));
				issue.setType(rs.getString("TYPE"));
				issue.setSubject(rs.getString("SUBJECT"));
				issue.setContents(rs.getString("CONTENTS"));
				if(rs.getInt("DISPLAY")==1) issue.setDisplay(true);
				else issue.setDisplay(false);
				issue.setRecommand(rs.getInt("RECOMMAND"));
				issue.setTag(rs.getString("TAG"));
				issue.setReg_date(rs.getString("REG_DATE"));
				issue.setUpload(rs.getString("UPLOAD"));
				issueList.add(issue);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return issueList;
		
	}
	
	public ArrayList<Issue> getIssueList(String key,String value){
		connect();
		ArrayList<Issue> issueList = new ArrayList<Issue>();
		
		String sql = "select * from ISSUE where "+ key +"=?" + "order by REG_DATE DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Issue issue = new Issue();
				
				issue.setIssue_id(rs.getInt("ISSUE_ID"));
				issue.setUser_id(rs.getInt("USER_ID"));
				issue.setType(rs.getString("TYPE"));
				issue.setSubject(rs.getString("SUBJECT"));
				issue.setContents(rs.getString("CONTENTS"));
				if(rs.getInt("DISPLAY")==1) issue.setDisplay(true);
				else issue.setDisplay(false);
				issue.setRecommand(rs.getInt("RECOMMAND"));
				issue.setTag(rs.getString("TAG"));
				issue.setReg_date(rs.getString("REG_DATE"));
				issue.setUpload(rs.getString("UPLOAD"));
				issueList.add(issue);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		
		
		return issueList;
		
	}
	
	public boolean checkIssue(int issue_id){
		ArrayList<Issue> issueList = getIssueList();
		
		for(Issue issue : issueList){
			if(issue.getIssue_id() == issue_id){
				return true;
			}
		}
		
		return false;
	}
	
	
	
}
