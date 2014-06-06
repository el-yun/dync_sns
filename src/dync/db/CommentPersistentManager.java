package dync.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dync.model.Comment;

public class CommentPersistentManager extends ConnectDB{
	
	public boolean insertComment(Comment comment){
		connect();
		
		String sql = "insert into COMMENT(ISSUE_ID,USER_ID,COMMENT_CONTENTS,REG_DATE) values(?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getIssue_id());
			pstmt.setInt(2, comment.getUser_id());
			pstmt.setString(3, comment.getComment_contents());
			pstmt.setTimestamp(4, Timestamp.valueOf(comment.getReg_date()));
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}finally{
			disconnect();
		}
		
		return true;
	}
	
	public boolean deleteComment(int comment_id){
		
		return true;
	}
	
	public boolean updateComment(Comment comment){
		return true;
	}
	
	public Comment getComment(int comment_id){
		Comment comment = new Comment();
		return comment;
	}
	
	public ArrayList<Comment> getCommentList(String key, int value){
		connect();
		ArrayList<Comment> commentList = new ArrayList<>();
		
		String sql = "select * from COMMENT where " + key + "=?";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Comment comment = new Comment();
				
				comment.setComment_id(rs.getInt("COMMENT_ID"));
				comment.setIssue_id(rs.getInt("ISSUE_ID"));
				comment.setUser_id(rs.getInt("USER_ID"));
				comment.setComment_contents(rs.getString("COMMENT_CONTENTS"));
				comment.setReg_date(rs.getString("REG_DATE"));
				
				commentList.add(comment);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println(pstmt.toString());
			e.printStackTrace();
		} finally{
			disconnect();
		}
		return commentList;
	}
	
	
	
}
