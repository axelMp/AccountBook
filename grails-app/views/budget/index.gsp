<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Description</th><th>Amount</th><th>From</th><th>Until</th><th>Continuous</th><th>Debitor</th><th>Creditor</th></tr>
		
	  <% plan.each { transaction -> %>
        <tr><td><%="${transaction.narration}" %></td>
			<td><%="${transaction.amount.cents}" %></td>
			<td><%="${transaction.startsOn.toString()}" %></td>
			<td><%="${transaction.endsOn.toString()}" %></td>
			<td><%="${transaction.isContinuous.toString()}" %></td>
			<td><%="${transaction.debitor.name}" %></td>
			<td><%="${transaction.creditor.name}" %></td>
			</tr>
	  <%}%>
	  
	</table>
	</body>
</html>
