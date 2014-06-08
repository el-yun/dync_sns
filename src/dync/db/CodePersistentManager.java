package dync.db;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dync.model.Code;
import dync.util.ConvertChar;
 
public class CodePersistentManager extends ConnectDB{
	public boolean insertCode(Code code){
		connect();
		String sql = "insert into CODE(CODE_REPOSITORY,CODE_SUBJECT,BASE_LANGUAGE,CODE_CONTENTS,REVISION,`USING`,REG_DATE)" +
					"values(?,?,?,?,?,?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, code.getCode_repository());
			pstmt.setString(2, code.getCode_subject());
			pstmt.setString(3,code.getBase_language());
			pstmt.setString(4, code.getCode_contents());
			pstmt.setInt(5, code.getRevision());
			if(code.isUsing()) pstmt.setInt(6, 1);
			else pstmt.setInt(6, 0);
			pstmt.setTimestamp(7, Timestamp.valueOf(code.getReg_date()));
			
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
		
		String sql = "delete from CODE where " + columnName + "=?";
		
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
	
	public boolean updateCode(Code code){
		connect();
		
		String sql = "update CODE set CODE_REPOSITORY=?,CODE_SUBJECT=?,BASE_LANGUAGE=?,CODE_CONTENTS=?,REVISION=?,`USING`=?,REG_DATE=? where CODE_ID=?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, code.getCode_repository());
			pstmt.setString(2, code.getCode_subject());
			pstmt.setString(3, code.getBase_language());
			pstmt.setString(4, code.getCode_contents());
			pstmt.setInt(5, code.getRevision());
			if(code.isUsing()) pstmt.setInt(6, 1);
			else pstmt.setInt(6, 0);
			pstmt.setTimestamp(7, Timestamp.valueOf(code.getReg_date()));
			pstmt.setInt(8, code.getCode_id());
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		
		return true;
	}
	//select AUTO_INCREMENT from TABLES where TABLE_SCHEMA = 'You're Database' and TABLE_NAME = 'Table Name'
	public int getAutoId(){
		connect();
		int get_id = 0;
		String sql = "SHOW TABLE STATUS FROM `dyncdb` LIKE 'code'";
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
	public Code getCode(int code_id){
		connect();
		
		String sql = "select * from CODE where CODE_ID = ?";
		
		Code code = new Code();
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code_id);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			code.setCode_id(rs.getInt("CODE_ID"));
			code.setCode_repository(rs.getInt("CODE_REPOSITORY"));
			code.setCode_subject(rs.getString("CODE_SUBJECT"));
			code.setBase_language(rs.getString("BASE_LANGUAGE"));
			code.setCode_contents(rs.getString("CODE_CONTENTS"));
			code.setRevision(rs.getInt("REVISION"));
			code.setUsing(rs.getBoolean("USING"));
			code.setReg_date(rs.getString("REG_DATE"));
			
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return code;
		
	}
	public ArrayList<Code> getCodeList(){
		connect();
		
		String sql = "select * from CODE order by REG_DATE DESC";
		
		ArrayList<Code> codeList = new ArrayList<Code>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Code code = new Code();
			
				code.setCode_id(rs.getInt("CODE_ID"));
				code.setCode_repository(rs.getInt("CODE_REPOSITORY"));
				code.setCode_subject(rs.getString("CODE_SUBJECT"));
				code.setBase_language(rs.getString("BASE_LANGUAGE"));
				code.setCode_contents(rs.getString("CODE_CONTENTS"));
				code.setRevision(rs.getInt("REVISION"));
				code.setUsing(rs.getBoolean("USING"));
				if(rs.getInt("USING")==1) code.setUsing(true);
				else code.setUsing(false);
				code.setReg_date(rs.getString("REG_DATE"));
				codeList.add(code);
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return codeList;
	}
	public ArrayList<Code> getCodeList(String key,int value){
		connect();
		
		String sql = "select * from CODE where " + key + "=?";
		
		ArrayList<Code> codeList = new ArrayList<Code>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, value);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Code code = new Code();
			
				code.setCode_id(rs.getInt("CODE_ID"));
				code.setCode_repository(rs.getInt("CODE_REPOSITORY"));
				code.setCode_subject(rs.getString("CODE_SUBJECT"));
				code.setBase_language(rs.getString("BASE_LANGUAGE"));
				code.setCode_contents(rs.getString("CODE_CONTENTS"));
				code.setRevision(rs.getInt("REVISION"));
				code.setUsing(rs.getBoolean("USING"));
				if(rs.getInt("USING")==1) code.setUsing(true);
				else code.setUsing(false);
				code.setReg_date(rs.getString("REG_DATE"));
				codeList.add(code);
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return codeList;
	}
	public ArrayList<Code> getCodeList(String key,long value){
		connect();
		
		String sql = "select * from CODE where " + key + "=?";
		
		ArrayList<Code> codeList = new ArrayList<Code>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, value);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Code code = new Code();
			
				code.setCode_id(rs.getInt("CODE_ID"));
				code.setCode_repository(rs.getInt("CODE_REPOSITORY"));
				code.setCode_subject(rs.getString("CODE_SUBJECT"));
				code.setBase_language(rs.getString("BASE_LANGUAGE"));
				code.setCode_contents(rs.getString("CODE_CONTENTS"));
				code.setRevision(rs.getInt("REVISION"));
				code.setUsing(rs.getBoolean("USING"));
				if(rs.getInt("USING")==1) code.setUsing(true);
				else code.setUsing(false);
				code.setReg_date(rs.getString("REG_DATE"));
				codeList.add(code);
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return codeList;
	}
	public ArrayList<Code> getCodeList(String key,String value){
		connect();
		
		String sql = "select * from CODE where " + key + "= ?";
		
		ArrayList<Code> codeList = new ArrayList<Code>();
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				Code code = new Code();
			
				code.setCode_id(rs.getInt("CODE_ID"));
				code.setCode_repository(rs.getInt("CODE_REPOSITORY"));
				code.setCode_subject(rs.getString("CODE_SUBJECT"));
				code.setBase_language(rs.getString("BASE_LANGUAGE"));
				code.setCode_contents(rs.getString("CODE_CONTENTS"));
				code.setRevision(rs.getInt("REVISTION"));
				code.setUsing(rs.getBoolean("USING"));
				if(rs.getInt("USING")==1) code.setUsing(true);
				else code.setUsing(false);
				code.setReg_date(rs.getString("REG_DATE"));
				codeList.add(code);
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return codeList;
	}
}
