<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit Study</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
<script src="javascript/common.js"></script>
</head>
<body>
<%@include file="/WEB-INF/header1.jsp" %>
<div id="Study">
Editing a study
</div>
<div id="eStudy">
<a href="/NBADProject/StudyController?action=studies">&lt;&lt; Back to Main Page</a>
</div>
<form id="formstyle" method="post" enctype="multipart/form-data" action="/NBADProject/StudyController">
<input type="hidden" value="update" name="action"/>
<input type="hidden" value="${studytoedit.getCode()}"  name="code"/>
<div id="edit">
<label class="newlabel">Study Name* </label><input size="35" class="textstyle" type="text" name="StudyName" value="${studytoedit.getName() }" required><br><br>
<label class="newlabel">Question Text* </label><input size="35" class="textstyle"  type="text" name="QuesText" value="${studytoedit.getQuestion() }" required><br><br>

<label class="newlabel">Image* </label><div id="imgdiv">
<input type="text" name="imageFile" />
<img id="img"  src="${studytoedit.getImageURL() }" alt="Image not available"> &nbsp;&nbsp;
<input class="uploadstyle"  type="button" value="Browse" name="upload" onclick="openSearch()">
</div>
<br><br>
<label class="newlabel"># Participants* </label><input size="35" class="textstyle"  type="text" name="numPart" value="${studytoedit.getRequestedParticipants() }" required><br><br>
<label class="newlabel">Description* </label><textarea name="studyDescription" class="textstyle" rows="5" cols="36"  required>${studytoedit.getDescription() }</textarea><br><br>
</div>
<div id="buttondiv">
<input  class="buttonstyle" type="submit" value="Update" name="update">
<br>
<br>
</div>

</form>

<%@include file="/WEB-INF/footer2.jsp" %>
</body>
</html>