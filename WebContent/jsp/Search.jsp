<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--<jsp:useBean id="ret" scope="request" class="java.util.ArrayList" />-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form id="formid" name="form1" method="post" action="${pageContext.request.contextPath}/SearchCode">
			<input type = "text" name = "search_Word">
			<input type= submit value="SEARCH">
	</form>

</body>
</html>