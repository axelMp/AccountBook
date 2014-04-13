<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Description</th><th>Amount</th><th>Date</th><th>Debitor</th><th>Creditor</th></tr>
		
	  <% transactions.each { transaction -> %>
        <tr><td><%="${transaction.description}" %></td>
			<td><%="${transaction.amount.cents}" %></td>
			<td><%="${transaction.occurredOn.toString()}" %></td>
			<td><%="${transaction.debitor.name}" %></td>
			<td><%="${transaction.creditor.name}" %></td>
			</tr>
	  <%}%>
	  
	</table>
	</body>
</html>
