package dync.model;

/**
 * File : Issue.java 
 * Description : Dync 프로그램 DO 클래스
 * 
 * @author JangHun
 * Version : 0.1
 * 제작일 : 2014-05-22
 */
public class Issue {
	public static final String ISSUE_ID		= "ISSUE_ID";
	public static final String USER_ID 		= "USER_ID";
	public static final String TYPE 		= "TYPE";
	public static final String SUBJECT 		= "SUBJECT";
	public static final String CONTENTS 	= "CONTENTS";
	public static final String DISPLAY 		= "DISPLAY";
	public static final String RECOMMAND 	= "RECOMMAND";
	public static final String TAG 			= "TAG";
	public static final String REG_DATE	 	= "REG_DATE";
	public static final String UPLOAD		= "UPLOAD";
	
	
	private int issue_id;
	private int user_id;
	private String type;
	private String subject;
	private String contents;
	private boolean display;
	private int recommand;
	private String tag;
	private String reg_date;
	private String upload;
	
	


	public Issue(int issue_id, int user_id, String type, String subject,
			String contents, boolean display, int recommand, String tag,
			String reg_date, String upload) {
		super();
		this.issue_id = issue_id;
		this.user_id = user_id;
		this.type = type;
		this.subject = subject;
		this.contents = contents;
		this.display = display;
		this.recommand = recommand;
		this.tag = tag;
		this.reg_date = reg_date;
		this.upload = upload;
	}

	

	public Issue() {
		// TODO Auto-generated constructor stub
	}
	
	public int getIssue_id() {
		return issue_id;
	}

	public void setIssue_id(int issue_id) {
		this.issue_id = issue_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public int getRecommand() {
		return recommand;
	}

	public void setRecommand(int recommand) {
		this.recommand = recommand;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}
	
	

}
