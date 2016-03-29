<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>New Study</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
<script src="javascript/common.js"></script>
</head>
<body>
<%@include file="/WEB-INF/header1.jsp" %>
<div id="newStudy">
Adding a study
</div>
<div id="addStudy">
<a href="/NBADProject/StudyController?action=studies">&lt;&lt; Back to Main Page</a>
</div>
<form id="formstyle" enctype="multipart/form-data" action="/NBADProject/StudyController?action=add" method="post">
<div id="new">

<label class="newlabel">Study Name* </label><input size="35" class="textstyle" type="text" name="StudyName" required><br><br>
<label class="newlabel">Question Text* </label><input size="35" class="textstyle"  type="text" name="QuesText" required><br><br>
<label class="newlabel">Image* </label> <input type="text" name="imageFile" /> <input class="uploadstyle"  type="button" value="Browse" name="upload" onclick="openSearch()"><br><br>
<label class="newlabel"># Participants * </label><input size="35" class="textstyle"  type="text" name="numPart" required><br><br>
<label class="newlabel">Description * </label><textarea name="description" class="textstyle" rows="10" cols="36" required></textarea><br><br>
</div>
<div id="buttondiv">
<input  class="buttonstyle" type="submit" value="Submit" name="submit">
<br>
<br>
</div>
</form>
<%@include file="/WEB-INF/footer2.jsp" %>
</body>
</html>