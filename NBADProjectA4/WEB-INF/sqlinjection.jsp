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
	<a href="/NBADProject/UserController?action=home">Back</a>
	<h1>SQL Injection</h1>
	<p>This page will allow you access to certain meta data of the
		tables in the database. Feel free to expose SQL's flaws.</p>

	<form method="post" action="SQLInjectionController">
		<input type="hidden" name="action" value="execute"> <label>Provide
			the name of the user you want to view the details of:</label> <input
			type="text" name="whereclause" placeholder="enter where clause here">
		<input type="number" name="mode" placeholder="1 or 2"> <input
			type="submit" value="execute">
	</form>


	<%@ include file="/WEB-INF/footer.jsp"%>

</body>
</html>