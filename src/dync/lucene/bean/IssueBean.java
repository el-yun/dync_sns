package dync.lucene.bean;

public class IssueBean {
	private String subject;
	private String contents;
	private String issueId;
	
	public String getSubject() {
		return subject;
	}
	public String getContent() {
		return contents;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setContent(String contents) {
		this.contents = contents;
	}

}
