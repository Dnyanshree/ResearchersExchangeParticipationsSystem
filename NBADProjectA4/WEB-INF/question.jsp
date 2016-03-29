<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<title>Question</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="/WEB-INF/header1.jsp" %>
<div id="mainQues">
<div id="sidebar">
<%@include file="/WEB-INF/sidebar.jsp" %>
</div>
<div id="page-wrap">
<p class="p_pgtitle" >
Question
</p>
<div id="imgques">
<img id="imgsze"  src="${studyfromcode.getImageURL()}" >
<p>
${studyfromcode.getQuestion()} (1 strongly agree, - 7 strongly disagree)
</p>
<br>
<form action="/NBADProject/StudyController" method="post">
<input type="hidden" value="answer" name="action"/>
<input type="hidden" value="${studyfromcode.getCode() }" name="code"/>
<select name="choice" >
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
  <option value="6">6</option>
  <option value="7">7</option>
</select>
<br>
<br>
<input type="submit" value="Submit">
</form>
</div>
</div>
</div>
<br>
<br>
<br>
<br>
<%@include file="/WEB-INF/footer.jsp" %>
</body>

</html>