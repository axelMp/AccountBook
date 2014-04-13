<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Description</th><th>From</th><th>To</th></tr>
		
	  <% transactions.each { transaction -> %>
        <tr><td><%="${transaction.description}" %></td>
			<td><%="${transaction.from.name}" %></td>
			<td><%="${transaction.to.name}" %></td>
			</tr>
	  <%}%>
	  
	</table>
	</body>
</html>
