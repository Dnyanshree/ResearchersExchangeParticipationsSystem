
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign up Page</title>
        <link href="css/common.css" rel="stylesheet" type="text/css">
    </head>
    <body>
     <%@ include file="/WEB-INF/header.jsp" %>
     
		<div id="formstyle_signup">
		<p class="signupP">${requestScope.msg }</p>
		<form action="UserController" method="post" >

			<div id="new">
				<input type="hidden" name="action" value="create"> <label
					for="name" class="newlabel">Name*</label>
					
				<input  name="name" size="35" class="textstyle" id="name" type="text"
					placeholder="Name" required> <br>
				<br>
				<br> <label for="email" class="newlabel">Email*</label> 
				
				<input name="email" size="35" class="textstyle" id="email" type="email" 
					placeholder="Email Address" required> <br> <br>
				<br> <label class="newlabel" for="password">Password*</label>
				
				<input name="password"  size="35" id="password" class="textstyle" type="password"
					placeholder="Password" required> <br>
				<br>
				<br> <label for="password" class="newlabel">Confirm
					Password*</label>
				<input name="confpassword" size="35" class="textstyle" id="passwordc"
					type="password"  placeholder="Password" required>
				<br>
				<br>
				<br>
			</div>
			<div id="buttondiv">
				<input class="buttonstyle" type="submit" value="Create Account" />
			</div>
		</form>
	</div>
    <%@ include file="/WEB-INF/footer.jsp" %>
    </body>
</html>
