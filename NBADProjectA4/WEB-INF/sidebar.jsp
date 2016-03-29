<table class="sidebartab">
<tr class="sidebarRow" >
<td class="sidebarCol" >
Coins (<a href="">${sessionScope.theUser.getCoins()}</a>)
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
Participants (<a href="">${sessionScope.theUser.getStudies()}</a>)
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
Participation (<a href="" >${sessionScope.theUser.getParticipation()}</a>)
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
&nbsp;
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
<a href="/NBADProject/UserController?action=main">Home</a>
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
<a href="/NBADProject/StudyController?action=participate">Participate</a>
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
<a href="/NBADProject/StudyController?action=studies">My Studies</a>
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
<a href="/NBADProject/StudyController?action=recommend">Recommend</a>
</td>
</tr>
<tr class="sidebarRow" >
<td class="sidebarCol" >
<a href="/NBADProject/StudyController?action=contact">Contact</a>
</td>
</tr>
</table>