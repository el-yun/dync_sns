package dync.model;
/**
 * File : Code_Repository.java 
 * Description : Dync 프로그램 DO 클래스
 * 
 * @author JangHun
 * Version : 0.1
 * 제작일 : 2014-05-22
 */
public class Code_Repository {
	public static final String CODE_REPOSITORY	= "CODE_REPOSITORY";
	public static final String USER_ID			= "USER_ID";

	private long code_repository;
	private int user_id;

	public Code_Repository() {
		// TODO Auto-generated constructor stub
	}

	public Code_Repository(int code_repository, int user_id) {
		super();
		this.code_repository = code_repository;
		this.user_id = user_id;
	}

	public long getCode_repository() {
		return code_repository;
	}

	public void setCode_repository(long code_repository) {
		this.code_repository = code_repository;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
