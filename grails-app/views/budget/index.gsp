<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Description</th><th>Amount</th><th>From - Until</th><th>Execution</th><th>Debitor</th><th>Creditor</th></tr>
		
	  <% plan.each { transaction -> %>
        <tr><td><%="${transaction.narration}" %></td>
			<td><g:amountFormat amount="${transaction.amount}"/></td>
			<td><g:dateFormat date="${transaction.startsOn}"/> - <g:dateFormat date="${transaction.endsOn}"/></td>
			<td><%="${transaction.executionOfPlannedTransaction.toString()}" %></td>
			<td><%="${transaction.debitor.name}" %></td>
			<td><%="${transaction.creditor.name}" %></td>
			</tr>
	  <%}%>
	  
	</table>
	</body>
</html>
