package dync.model;

public class Comment {
	public static final String COMMENT_ID 		= "COMMENT_ID";
	public static final String ISSUE_ID 		= "ISSUE_ID";
	public static final String USER_ID 			= "USER_ID";
	public static final String COMMENT_CONTENTS = "COMMENT_CONTENTS";
	public static final String REG_DATE			= "REG_DATE";
	public static final String USER_NAME			= "USER_NAME";

	private int comment_id;
	private int issue_id;
	private int user_id;
	private String user_name;
	private String comment_contents;
	private String reg_date;
	
	public Comment(){
		
	}
	
	public Comment(int comment_id, int issue_id, int user_id,
			String comment_contents, String reg_date) {
		this.comment_id = comment_id;
		this.issue_id = issue_id;
		this.user_id = user_id;
		this.comment_contents = comment_contents;
		this.reg_date = reg_date;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
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

	public String getComment_contents() {
		return comment_contents;
	}

	public void setComment_contents(String comment_contents) {
		this.comment_contents = comment_contents;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	public String getUserName() {
		return comment_contents;
	}

	public void setUserName(String UserName) {
		this.user_name = UserName;
	}
}
