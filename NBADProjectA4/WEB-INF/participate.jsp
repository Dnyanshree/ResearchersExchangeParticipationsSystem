<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Participate Page</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="/WEB-INF/header1.jsp"%>
	<div id="main">
		<div id="sidebar">
			<%@include file="/WEB-INF/sidebar.jsp"%>
		</div>
		<div id="page-wrap">
			<p class="parasidebar">Studies</p>
			<table id="tabparticipate">
				<tr class="participate_tr">
					<th>Study Name</th>
					<th>Image</th>
					<th>Question</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="study" items="${requestScope.studylist}">
				
				<tr class="participate_tr1">
					<td>${study.getName() }</td>
					<td><img src="${study.getImageURL() }"
						alt="Image unavailable" id="img"></td>
					<td>${study.getQuestion() }</td>
					<td><input id="buttonstle" type="button" value="Participate"
						onClick="parent.location='/NBADProject/StudyController?action=participate&code=${study.getCode()}'" /></td>
				</tr>
				</c:forEach>
				
			</table>
		</div>
	</div>

	<%@include file="/WEB-INF/footer.jsp"%>
</body>
</html>