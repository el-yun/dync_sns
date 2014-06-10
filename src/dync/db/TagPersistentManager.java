package dync.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public boolean deleteTag(int user_id,String value){
		connect();
		String sql = "delete from TAG where USER_ID=? and TAG_NAME=?";
	    
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setString(2, value);
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
	
	public ArrayList<Tag> getTagList(int user_id){
		connect();
		String sql = "select * from TAG where `USER_ID`=?";
		ArrayList<Tag> tagList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Tag tag = new Tag();
				tag.setUser_id(rs.getInt("USER_ID"));
				tag.setTag_name(rs.getString("TAG_NAME"));
				tag.setIssue_id(rs.getInt("ISSUE_ID"));
				tagList.add(tag);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tagList;
	}
	
	public boolean checkTag(int user_id,String tag_name){
		ArrayList<Tag> tagList = getTagList(user_id);
		for(Tag tag : tagList){
			if(tag.getTag_name().equals(tag_name)){
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
}
