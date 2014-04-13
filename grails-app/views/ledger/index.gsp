<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Select ledger</th></tr>
		
	  <% ledgers.each { ledger -> %>
        <tr><td><a href="select?name=${ledger.name}"><%="${ledger.name}" %></a> <form name="input" action="delete" method="get"><input type="submit" value="Delete"></form></td></tr>
	  <%}%>
	  
	</table>
	</body>
</html>
