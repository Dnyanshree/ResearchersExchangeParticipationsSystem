<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Home Page</title>

<link href="css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="/WEB-INF/header.jsp" %>
<div>
		  <p>${requestScope.msg }</p>
          <img src="images/avatar.jpg" id="home_img" alt="images/avatar.jpg" />
</div>
<%@include file="/WEB-INF/footer1.jsp" %>
</body>
</html>