<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="css/common.css">
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>
	<div id="loginstyle">
	<p>${requestScope.msg }</p>
		<form action="UserController" method="post">
			<div id="new">
				<input type="hidden" name="action" value="login"> <label
					class="newlabel" for="email">Email Address*</label> <input
					size="35" class="textstyle" id="email" type="email" name="username"
					placeholder="Email Address" required>
			</div>
			<br>

			<div id="newPass">
				<label class="newlabel" for="password">Password*</label> <input
					size="35" class="textstyle" id="password" name="password"
					type="password" placeholder="Password" required>
			</div>
			<br> <br>
			<div id="buttondiv">
				<input class="buttonstyle" type="submit" value="Login" name="submit">
			</div>
		</form>

		<br>
		<br> <br>
		<br> <a class="signup" href="/NBADProject/UserController?action=signuppage">Sign up for a new
			account</a>
		<br> <a class="signup" href="/NBADProject/UserController?action=forgotpassword&mode=inputemail">Forgot Password</a>
	</div>

	<%@ include file="/WEB-INF/footer.jsp"%>
</body>
</html>
