package dync.db;

import java.sql.SQLException;

import dync.model.Tag;

public class TagPersistentManager extends ConnectDB {
	
	public boolean insertTag(Tag tag){
		connect();
		String sql = "insert into TAG(USER_ID,TAG_NAME,ISSUE_ID) values(?,?,?)";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tag.getUser_id());
			pstmt.setString(2, tag.getTag_name());
			pstmt.setInt(3, tag.getIssue_id());
			pstmt.executeUpdate();
		}catch(SQLException e){
			System.out.println(pstmt.toString());
			e.printStackTrace();
			return false;
		}finally{
			disconnect();
		}
		
		return true;
	}
	
	public boolean deleteTag(String key,int value){
		connect();
		String sql = "delete from TAG where " + key + "=?";
	    
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			pstmt.executeUpdate();
		}catch(SQLException e){
			System.out.println(pstmt.toString());
			e.printStackTrace();
			return false;
		}finally{
			disconnect();
		}
		return true;
	}
	
	public boolean deleteTag(String key,String value){
		connect();
		String sql = "delete from TAG where " + key + "=?";
	    
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
		}catch(SQLException e){
			System.out.println(pstmt.toString());
			e.printStackTrace();
			return false;
		}finally{
			disconnect();
		}
		return true;
	}
	
	
	
	
}
