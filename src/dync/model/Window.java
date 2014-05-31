package dync.model;
/**
 * File : Window.java 
 * Description : Dync 프로그램 DO 클래스
 * 
 * @author JangHun
 * Version : 0.1
 * 제작일 : 2014-05-22
 */
public class Window {
	public static final String ISSUE_ID		= "ISSUE_ID";
	public static final String CODE_ID 		= "CODE_ID";
	public static final String START_LINE 	= "START_LINE";
	public static final String END_LINE 	= "END_LINE";
	public static final String WINDOW_PATH 	= "WINDOW_PATH";
	
	private int issue_id;
	private int code_id;
	private int start_line;
	private int end_line;
	private String window_path;

	public Window() {
		// TODO Auto-generated constructor stub
	}

	public Window(int issue_id, int code_id, int start_line, int end_line,
			String window_path) {
		super();
		this.issue_id = issue_id;
		this.code_id = code_id;
		this.start_line = start_line;
		this.end_line = end_line;
		this.window_path = window_path;
	}

	public int getIssue_id() {
		return issue_id;
	}

	public void setIssue_id(int issue_id) {
		this.issue_id = issue_id;
	}

	public int getCode_id() {
		return code_id;
	}

	public void setCode_id(int code_id) {
		this.code_id = code_id;
	}

	public int getStart_line() {
		return start_line;
	}

	public void setStart_line(int start_line) {
		this.start_line = start_line;
	}

	public int getEnd_line() {
		return end_line;
	}

	public void setEnd_line(int end_line) {
		this.end_line = end_line;
	}

	public String getWindow_path() {
		return window_path;
	}

	public void setWindow_path(String window_path) {
		this.window_path = window_path;
	}
	
	
	
}
