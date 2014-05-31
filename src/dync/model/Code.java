package dync.model;
/**
 * File : Code.java 
 * Description : Dync 프로그램 DO 클래스
 * 
 * @author JangHun
 * Version : 0.1
 * 제작일 : 2014-05-22
 */
public class Code {
	public static final String CODE_ID				= "CODE_ID";
	public static final String CODE_REPOSITORY 		= "CODE_REPOSITORY";
	public static final String CODE_SUBJECT 		= "CODE_SUBJECT";
	public static final String BASE_LANGUAGE 		= "BASE_LANGUAGE";
	public static final String CODE_CONTENTS 		= "CODE_CONTENTS";
	public static final String REVISION				= "REVISION";
	public static final String USING 				= "USING";
	public static final String REG_DATE	 			= "REG_DATE";

	private int code_id;
	private int code_repository;
	private String code_subject;
	private String base_language;
	private String code_contents;
	private int revision;
	private boolean using;
	private String reg_date;

	public Code() {
		// TODO Auto-generated constructor stub
	}

	public Code(int code_id, int code_repository, String code_subject,
			String base_language, String code_contents, int revision,
			boolean using, String reg_date) {
		super();
		this.code_id = code_id;
		this.code_repository = code_repository;
		this.code_subject = code_subject;
		this.base_language = base_language;
		this.code_contents = code_contents;
		this.revision = revision;
		this.using = using;
		this.reg_date = reg_date;
	}

	public int getCode_id() {
		return code_id;
	}

	public void setCode_id(int code_id) {
		this.code_id = code_id;
	}

	public int getCode_repository() {
		return code_repository;
	}

	public void setCode_repository(int code_repository) {
		this.code_repository = code_repository;
	}

	public String getCode_subject() {
		return code_subject;
	}

	public void setCode_subject(String code_subject) {
		this.code_subject = code_subject;
	}

	public String getBase_language() {
		return base_language;
	}

	public void setBase_language(String base_language) {
		this.base_language = base_language;
	}

	public String getCode_contents() {
		return code_contents;
	}

	public void setCode_contents(String code_contents) {
		this.code_contents = code_contents;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
		
}
