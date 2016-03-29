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
			<input type="hidden" name="mode" value="updatenewpwd"> 
			<input type="hidden" name="email" value="${requestScope.email}">
			
			<label for="password" class="newlabel"> Password reset for user ${requestScope.email} </label>
			<br><br>
			
			
			<label for="password" class="newlabel"> New Password* </label>		
					<input name="password"  size="35" id="password" class="textstyle" type="password"
					placeholder="Password" required> <br>
				<br>
				<br> <label for="password" class="newlabel">Confirm
					Password*</label>
				<input name="confpassword" size="35" class="textstyle" id="passwordc"
					type="password"  placeholder="Password" required>
				<div id="buttondiv">
				<input class="buttonstyle" type="submit" value="Submit" />
			</div>		
		</div>
	</form>
	<%@ include file="/WEB-INF/footer.jsp"%>
</body>
</html>
	