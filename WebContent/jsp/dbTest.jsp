<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DB Test page</title>
</head>


<body>
	<div style="border:1pt solid blue; width : auto; height : 700px;">
		<div style="border:1pt solid red; width : 300px; height : 600px;float:left;text-align: left;">
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
				UPLOAD   <input type="file" name="UPLOAD"><br>
				<input type="submit" value="삽입">
			</form>
			<form id="deleteIssueForm" name="deleteIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol">
				<input type=hidden id="actionId" name="action" value="delete">
				COLUMN_NAME <input type="text" name="COLUMN_NAME" ><br>
				COLUMN_VALUE <input type="text" name="COLUMN_VALUE"><br>
				<input type="submit" value="삭제">
			</form>
		</div>
		<div style="border:1pt solid blue; width : 80px; height : 50px;float:left;margin-top:150px;">
			<input type="button" name="editBtn" value=">>">
		</div>
		<div style="border:1pt solid red; width : 300px; height : 600px;float:left;text-align: left;">
			<h2>DB편집</h2>
		</div>
		<div style="border:1pt solid silver; margin-left:20px; width : 300px; height : 300px;float:left;">
			<h2>Code 추가</h2>
			<form id="insertCodeForm" name="inserCodeForm" method="post" action="${pageContext.request.contextPath}/codecontrol">
				<input type=hidden id="actionId" name="action" value="insert">
				CODE_ID <input type="number" name="CODE_ID" min="0" max="2100000000" value=""><br>
				CODE_REPOSITORY <input type="number" name="CODE_REPOSITORY" min="0" max="2100000000" value=""><br>
				CODE_SUBJECT <input type="text" name="CODE_SUBJECT" ><br>
				BASE_LANGUAGE <input type="text" name="BASE_LANGUAGE"><br>
				CODE_CONTENTS <input type="text" name="CODE_CONTENTS"><br>
				REVISION <input type="number" name="REVISION"><br>
				USING <br><input type="radio" name="USING" value="true"> TRUE<br>
						 <input type="radio" name="USING" value="false"> FALSE<br>
			</form>
		</div>
	</div>
</body>
</html>