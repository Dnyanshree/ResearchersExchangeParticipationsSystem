<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Forgot Password</title>
<link rel="stylesheet" type="text/css" href="css/common.css">
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<form action="UserController" method="post">
		<div id="new">
			<input type="hidden" name="action" value="forgotpassword">
			<input type="hidden" name="mode" value="mailsend"> 
			
			<label class="newlabel2" for="email">Email Address*</label> 
			<input size="35" class="textstyle" id="email" type="email" name="restpwdemail"
					placeholder="Email Address" required>
			<div id="buttondiv">
				<input class="buttonstyle" type="submit" value="Submit" />
			</div>		
			<p> Please check for the reset password link sent to the email address you have specified.</p>
		</div>
	</form>
	<%@ include file="/WEB-INF/footer.jsp"%>
</body>
</html>
	