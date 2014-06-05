package dync.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dync.model.User;

public class UserPersistentManager extends ConnectDB{
	
	public boolean insertUser(User user){
		connect();
		String sql = "insert into USER(USER_ID,USER_NAVERHASH,USER_KAKAOHASH,USER_NAME,USER_DESCRIPTION,CODE_REPOSITORY)" +
					"values(?,?,?,?,?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getUser_id());
			pstmt.setString(2, user.getUser_naverhash());
			pstmt.setString(3, user.getUser_kakaohash());
			pstmt.setString(4, user.getUser_name());
			pstmt.setString(5, user.getUser_description());
			pstmt.setInt(6, user.getCode_repository());
			
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
	
	public boolean deleteUser(String columnName, int columnValue){
		connect();
		
		String sql = "delete from USER where " + columnName + "=?";
		
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
	public User getUser(int user_id){
		connect();
		
		String sql = "select * from USER where ISSUE_ID=?";
		User user = new User();
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			user.setUser_id(rs.getInt("USER_ID"));
			user.setUser_naverhash(rs.getString("USER_NAVERHASH"));
			user.setUser_kakaohash(rs.getString("USER_KAKAOHASH"));
			user.setUser_name(rs.getString("USER_NAME"));
			user.setUser_description(rs.getString("USER_DESCRIPTION"));
			user.setCode_repository(rs.getInt("CODE_REPOSITORY"));
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		
		return user;
	}
	
	public User getAuth(String Param, String Val){
		connect();
		
		User user = new User();
		
		try{
			String sql = "select * from USER where `" + Param + "` = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Val);
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			
			boolean hasResult = rs.next();
			if(hasResult == true){
				user.setUser_id(rs.getInt("USER_ID"));
				user.setUser_naverhash(rs.getString("USER_NAVERHASH"));
				user.setUser_kakaohash(rs.getString("USER_KAKAOHASH"));
				user.setUser_name(rs.getString("USER_NAME"));
				user.setUser_description(rs.getString("USER_DESCRIPTION"));
				user.setCode_repository(rs.getInt("CODE_REPOSITORY"));
			}
			System.out.println(user.getUser_kakaohash());
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		
		return user;
	}
	public User getAuth(String Param, int Val){
		connect();
		
		User user = new User();
		
		try{
			String sql = "select * from USER where " + Param + " = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Val);
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			
			boolean hasResult = rs.next();
			if(hasResult == true){
				user.setUser_id(rs.getInt("USER_ID"));
				user.setUser_naverhash(rs.getString("USER_NAVERHASH"));
				user.setUser_kakaohash(rs.getString("USER_KAKAOHASH"));
				user.setUser_name(rs.getString("USER_NAME"));
				user.setUser_description(rs.getString("USER_DESCRIPTION"));
				user.setCode_repository(rs.getInt("CODE_REPOSITORY"));
			}
			System.out.println(user.getUser_kakaohash());
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		
		return user;
	}
	public ArrayList<User> getUserList(){
		connect();
		
		String sql = "select * from USER";
		ArrayList<User> userList = new ArrayList<>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				User user = new User();
				
				user.setUser_id(rs.getInt("USER_ID"));
				user.setUser_naverhash(rs.getString("USER_NAVERHASH"));
				user.setUser_kakaohash(rs.getString("USER_KAKAOHASH"));
				user.setUser_name(rs.getString("USER_NAME"));
				user.setUser_description(rs.getString("USER_DESCRIPTION"));
				user.setCode_repository(rs.getInt("CODE_REPOSITORY"));
				
				userList.add(user);
			}
			
			
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return userList;
	}
	
	public boolean checkUser (int user_id){
		ArrayList<User> userList = getUserList();
		
		for(User user : userList){
			if(user.getUser_id() == user_id){
				return true;
			}
		}
		
		return false;
	}
}
