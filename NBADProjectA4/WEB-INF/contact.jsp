
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Contact Page</title>
        
        
         <link rel="stylesheet" type="text/css" href="css/common.css">
    </head>

    <body>
    <%@ include file="/WEB-INF/header1.jsp" %>
<br>      
<div id="div_studies">
Contact
</div>
<div id="addStudy">
<a href="/NBADProject/UserController?action=main">&lt;&lt; Back to Main Page</a>
</div>     
<br>
        <form id="formstyle" method="post" action="/NBADProject/MailSendController?type=c">
           
                <div id="new">
            <label class="newlabel" >Name *</label>
            <input size="35" class="textstyle" name="name" type="text" placeholder="Username" required>
                <br><br>
        
            <label class="newlabel">Email *</label>
            <input size="35" class="textstyle" name="email" type="email" placeholder="Email Address" required>
        <br><br>
         
                   
              <label class="newlabel">Message *</label>
            <textarea  class="textstyle" name="message" rows="7" cols="50" placeholder="Write your message here" required></textarea>
        </div><br>
                       <div id="buttondiv">
<input  class="buttonstyle" type="submit" value="Submit" name="submit">

</div>
          </form>
<%@ include file="/WEB-INF/footer.jsp" %>
    </body>
</html>

