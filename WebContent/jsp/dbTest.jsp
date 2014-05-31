<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DB Test page</title>
</head>

<jsp:useBean id="issue" scope="request" class="dync.model.Issue"/>
<jsp:useBean id="json" scope="request" class="net.sf.json.JSONObject"/>
<jsp:useBean id="issueList" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="issueJSONList" scope="request" class="java.lang.String"/>
<body>
	<div style="border:1pt solid blue; width : auto; height : 700px;">
		<div style="border:1pt solid red; width : 300px; height : 600px;float:left;text-align: left;">
			<h2>DB삽입</h2>
			<form id="insertIssueForm" name="insertIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol" enctype="multipart/form-data">
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
			<form id="deleteIssueForm" name="deleteIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol" enctype="multipart/form-data">
				<input type=hidden id="actionId" name="action" value="delete">
				COLUMN_NAME <input type="text" name="COLUMN_NAME" ><br>
				COLUMN_VALUE <input type="text" name="COLUMN_VALUE"><br>
				<input type="submit" value="삭제">
			</form>
			<form id="getIssueForm" name="getIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol?action=get_issue" enctype="multipart/form-data">
				<h5>json 불러오기 ISSUE_ID 입력</h5>
				<input type="text" name="ISSUE_ID">
				<input type="submit" value="JSON 불러오기">
			</form>
			<textarea rows="5" cols="30">${json}</textarea>
		</div>
		<div style="border:1pt solid red; width : 300px; height : 600px;float:left;text-align: left;">
			<h2>DB편집</h2>
			<form id="editIssueForm" name="editIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol?action=edit" enctype="multipart/form-data">
				<input type="number" name="ISSUE_ID">
				<input type="submit" value="불러오기">
			</form>
			<form id="updateIssueForm" name="updateIssueForm" method="post" action="${pageContext.request.contextPath}/issuecontrol" enctype="multipart/form-data">
			<!-- <form id="insertIssueForm" name="insertIssueForm" method="post" action="/Dync/dync.servlet.IssueServlet">-->
				<input type=hidden id="actionId" name="action" value="update">
				ISSUE_ID <input type="hidden" name="ISSUE_ID" min="0" max="2100000000" value="${issue.getIssue_id()}" ><br>
				USER_ID  <input type="number" name="USER_ID" min="0" max="2100000000" value="${issue.getUser_id()}"><br>
				TYPE	 <input type="text" name="TYPE" value="${issue.getType()}" ><br>
				SUBJECT  <input type="text" name="SUBJECT" value="${issue.getSubject()}"><br>
				CONTENTS <input type="text" name="CONTENTS" value="${issue.getContents()}"><br>
				DISPLAY  <br><input type="radio" name="DISPLAY" value="true"> TRUE<br>
						 <input type="radio" name="DISPLAY" value="false"> FALSE<br>
				RECOMMAND<input type="text" name="RECOMMAND" value="${issue.getRecommand()}" ><br>
				UPLOAD   <input type="text" name="UPLOAD" value="${issue.getUpload()}"><br>
				<input type="submit" value="수정">
			</form>
		</div>
		<div style="float:left;">
			<div style="border:1pt solid silver; margin-left:20px; width : 300px; height : 400px;float:left;">
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
					<input type="submit" value="삽입">
				</form>
				<form id="deleteCodeForm" name="deleteCodeForm" method="post" action="${pageContext.request.contextPath}/codecontrol">
					<input type=hidden id="actionId" name="action" value="delete">
					COLUMN_NAME <input type="text" name="COLUMN_NAME" ><br>
					COLUMN_VALUE <input type="text" name="COLUMN_VALUE"><br>
					<input type="submit" value="삭제">
				</form>
				<form id="getCodeForm" name="getCodeForm" method="post" action="${pageContext.request.contextPath}/codecontrol">
					<input type=hidden id="actionID" name="action" value="code">
					CODE_ID<input type="text" name="CODE_ID">
					<input type="submit" value="실행">
				</form>
			</div>
			<div style="border:1pt solid silver; margin-left:20px; width : 350px; height : 400px;float:left;">
				<h2>유저 추가(repository 자동 생성)</h2>
				<form id="insertUserForm" name="inserUserForm" method="post" action="${pageContext.request.contextPath}/usercontrol" >
					<input type=hidden id="actionId" name="action" value="insert">
					USER_ID <input type="number" name="USER_ID" min="0" max="2100000000" value=""><br>
					USER_NAVERHASH <input type="text" name="USER_NAVERHASH"><br>
					USER_NAME <input type="text" name="USER_NAME"><br>
					USER_DESCRIPTION <input type="text" name="USER_DESCRIPTION"><br>
					CODE_REPOSITORY <input type="number" name="CODE_REPOSITORY" min="0" max="2100000000" value=""><br>
					<input type="submit" value="삽입">
				</form>
			</div>
			<div style="height:auto">
				<p>
				<form method="post" action="${pageContext.request.contextPath}/issuecontrol?action=list" enctype="multipart/form-data">
				<input type="submit" value="list 출력">
				</form>
				<h3>List 출력</h3>
				<table border="1">
					<tr>
						<th>ISSUE_ID</th>
						<th>USER_ID</th>
						<th>TYPE</th>
						<th>SUBJECT</th>
						<th>CONTENTS</th>
						<th>DISPLAY</th>
						<th>RECOMMAND</th>
						<th>REG_DATE</th>
						<th>UPLOAD</th>
					</tr>
					
					<c:if test="${fn:length(issueList) > 0}">
						<c:forEach begin="0" end="${fn:length(issueList) - 1}" varStatus="issue">
						<tr>
							<td>${issueList[issue.index].getIssue_id()}</td>
							<td>${issueList[issue.index].getUser_id()}</td>
							<td>${issueList[issue.index].getType()}</td>
							<td>${issueList[issue.index].getSubject()}</td>
							<td>${issueList[issue.index].getContents()}</td>
							<td>${issueList[issue.index].isDisplay()}</td>
							<td>${issueList[issue.index].getRecommand()}</td>
							<td>${issueList[issue.index].getReg_date()}</td>
							<td>${issueList[issue.index].getUpload()}</td>
						</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
		</div>
	</div>
</body>
</html>