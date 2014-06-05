package dync.model;
/**
 * File : Tag.java 
 * Description : Dync 프로그램 DO 클래스
 * 
 * @author JangHun
 * Version : 0.1
 * 제작일 : 2014-05-22
 */
public class Tag {
	public static final String USER_ID			= "USER_ID";
	public static final String ISSUE_ID 		= "ISSUE_ID";
	public static final String TAG_NAME 		= "TAG_NAME";

	private int user_id;
	private int issue_id;
	private String tag_name;

	public Tag() {
		// TODO Auto-generated constructor stub
	}

	public Tag(int user_id, String tag_name, int issue_id) {
		super();
		this.user_id = user_id;
		this.tag_name = tag_name;
		this.issue_id = issue_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getIssue_id() {
		return issue_id;
	}

	public void setIssue_id(int issue_id) {
		this.issue_id = issue_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

}
