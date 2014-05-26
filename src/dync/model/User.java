package dync.model;
/**
 * File : User.java 
 * Description : Dync 프로그램 DO 클래스
 * 
 * @author JangHun
 * Version : 0.1
 * 제작일 : 2014-05-22
 */
public class User {
	public static final String USER_ID				= "USER_ID";
	public static final String USER_NAVERHASH 		= "USER_NAVERHASH";
	public static final String USER_NAME 			= "USER_NAME";
	public static final String USER_DESCRIPTION 	= "USER_DESCRIPTION";
	public static final String CODE_REPOSITORY 		= "CODE_REPOSITORY";
	
	private int user_id;
	private String user_naverhash;
	private String user_name;
	private String user_description;
	private int code_repository;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int user_id, String user_naverhash, String user_name,
			String user_description, int code_repository) {
		super();
		this.user_id = user_id;
		this.user_naverhash = user_naverhash;
		this.user_name = user_name;
		this.user_description = user_description;
		this.code_repository = code_repository;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_naverhash() {
		return user_naverhash;
	}

	public void setUser_naverhash(String user_naverhash) {
		this.user_naverhash = user_naverhash;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_description() {
		return user_description;
	}

	public void setUser_description(String user_description) {
		this.user_description = user_description;
	}

	public int getCode_repository() {
		return code_repository;
	}

	public void setCode_repository(int code_repository) {
		this.code_repository = code_repository;
	}

}