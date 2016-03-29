<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>Main Page</title>

<link href="css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="/WEB-INF/header1.jsp"%>

<div id="main">
<div id="sidebar">
<%@include file="/WEB-INF/sidebar.jsp" %>
</div>

<p>${requestScope.msg}</p>
</div>
<%@include file="/WEB-INF/footer.jsp" %>
</body>
</html>