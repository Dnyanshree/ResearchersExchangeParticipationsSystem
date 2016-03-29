
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Recommendation Page</title>


<link rel="stylesheet" type="text/css" href="css/common.css">
</head>

<body>
	<%@ include file="/WEB-INF/header1.jsp"%>
	<div id="div_studies_recommend">Recommend a Friend</div>
	<div id="addStudy">
		<a href="/NBADProject/StudyController?action=main">&lt;&lt; Back to Main Page</a>
	</div>
	<form id="formstyle" method="post" action="/NBADProject/MailSendController?type=r">  <!--/MailSendController?type=r  -->

		<div id="new">
			<label class="newlabel" >Name *</label> <input size="35" class="textstyle" id="name" name="fname" type="text" placeholder="Full Name" value="${sessionScope.theUser.getName() }" required>
			 <br> <br>
			  <label class="newlabel" >Email *</label> <input size="35" class="textstyle" id="email" name="email" type="email" placeholder="Email Address" value="${sessionScope.theUser.getEmail() }" required>
			<br> <br>
			 <label class="newlabel" >Friend's Email *</label> <input size="35" class="textstyle" id="emailF" name="emailF" type="email" placeholder="Email Address" required> <br> <br> 
			 <label	class="newlabel" >Message *</label>
			<textarea name="msg" class="textstyle" name="textarea" rows="7" cols="50" placeholder="Write your message here" required></textarea>
		</div>
		<br> <br>

		<div id="buttondiv">
			<input class="buttonstyle" type="submit" value="Submit" name="submit"><br>
			<br>

		</div>
	</form>
	<div id="msg">
		<b>When your friend logs in and participates in one user study,
			you will get 2 coins bonus </b>
	</div>
	<br>

	<%@ include file="/WEB-INF/footer2.jsp"%>
</body>
</html>

