<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Accounts</th><th>type</th></tr>
		
	  <% accounts.each { account -> %>
        <tr><td><%="${account.name}" %></td><td><%="${account.accountType}" %></td></tr>
	  <%}%>
	  
	</table>
	</body>
</html>
