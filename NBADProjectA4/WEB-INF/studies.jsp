<!DOCTYPE html >
<html>
<head>
<title>Studies.jsp</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="/WEB-INF/header1.jsp" %>
<div class="div_studies">
My Studies
</div>
<div id="div_mar">
<a href="/NBADProject/StudyController?action=newstudy">Add New Study</a>
<br>
<a href="/NBADProject/StudyController?action=main">&lt;&lt; Back to Main Page</a>
</div>
<div class="div_table">
<table class="tab_table">
<tr class="tr_table">
<th>Study Name</th>
<th>Requested Participants</th>
<th>Participations</th>
<th>Status</th>
<th>Action</th>
</tr>
				<c:forEach var="study" items="${studiesList}">
				
				<tr class="tr_field">
					<td>${study.getName() }</td>
					<td>${study.getRequestedParticipants() }</td>
					<td>${study.getNumOfParticipants() }</td>
																												<!-- /NBADProject/StudyController?action=participate&code=${study.getCode()} -->
					<td><input class="td_btn" type="button" value="${study.getStatus() }" onClick="parent.location='/NBADProject/StudyController?action=${study.getStatus()}&code=${study.getCode()}'"/></td>
					<td><input class="td_btn" type="button" onClick="parent.location='/NBADProject/StudyController?action=edit&code=${study.getCode()}'" value="Edit"/></td>
				</tr>
				</c:forEach>
</table>
</div>
<%@include file="/WEB-INF/footer2.jsp" %>
</body>
</html>