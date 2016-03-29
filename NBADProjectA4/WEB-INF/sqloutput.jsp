<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SQL Injection Example</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<h1>SQL Injection</h1>
	<p>
		<a href="/NBADProject/SQLInjectionController?action=sqlinjection">Back</a> <br><br>
		mode Name Email Address <br>
		${requestScope.msg}
	</p>
	<%@ include file="/WEB-INF/footer.jsp"%>



</body>
</html>