<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="errorMessage" scope="request" class="java.lang.String"/>
<h1>오류 발생</h1>
<h4>오류 내용</h4>
<textarea rows="10" cols="50" disabled="disabled">${errorMessage}</textarea>

</body>
</html>