package dync.db;
 
import java.sql.SQLException;
import java.sql.Timestamp;
 
import dync.model.Code;
 
public class CodePersistentManager extends ConnectDB{
	public boolean insertCode(Code code){
		connect();
		String sql = "insert into code(CODE_ID,CODE_REPOSITORY,CODE_SUBJECT,BASE_LANGUAGE,CODE_CONTENTS,REVISION,USING,REG_DATE)" +
					"values(?,?,?,?,?,?,?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code.getCode_id());
			pstmt.setInt(2, code.getCode_repository());
			pstmt.setString(3, code.getCode_subject());
			pstmt.setString(4,code.getBase_language());
			pstmt.setString(5, code.getCode_contents());
			pstmt.setInt(6, code.getRevision());
			if(code.isUsing()) pstmt.setInt(7, 1);
			else pstmt.setInt(7, 0);
			pstmt.setTimestamp(8, Timestamp.valueOf(code.getReg_date()));
			
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
	
	public boolean deleteCode(String columnName, int columnValue){
		connect();
		
		String sql = "delete from code where " + columnName + "=?";
		
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
}
