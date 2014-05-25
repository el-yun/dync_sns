<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DB Test page</title>
</head>


<body>
	<div style="boder : 1px dashed #BDBDBD; width : 300px; height : 600px;float:left;text-align: left;">
		<h2>DB삽입</h2>
		<form id="insertIssueForm" name="insertIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol">
		<!-- <form id="insertIssueForm" name="insertIssueForm" method="post" action="/Dync/dync.servlet.IssueServlet">-->
			<input type=hidden id="actionId" name="action" value="insert">
			ISSUE_ID <input type="number" name="ISSUE_ID" min="0" max="2100000000" value=""><br>
			USER_ID  <input type="number" name="USER_ID" min="0" max="2100000000" value=""><br>
			TYPE	 <input type="text" name="TYPE" ><br>
			SUBJECT  <input type="text" name="SUBJECT" ><br>
			CONTENTS <input type="text" name="CONTENTS" ><br>
			DISPLAY  <br><input type="radio" name="DISPLAY" value="true"> TRUE<br>
					 <input type="radio" name="DISPLAY" value="false"> FALSE<br>
			RECOMMAND<input type="text" name="RECOMMAND" ><br>
			REG_DATE <input type="datetime" name="REG_DATE" ><br>
			날짜입력 예시 : yyyy-mm-dd hh:mm:ss<br>
			<input type="submit" value="삽입">
		</form>
	</div>
	<div style="boder : 1px dashed #BDBDBD; width : 300px; height : 600px;float:left;text-align: left;">
		<h2>DB편집</h2>
	</div>
</body>
</html>