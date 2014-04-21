<!DOCTYPE html>
<html>
	<g:javascript src="jquery-2.1.0.js" />
	<g:javascript src="utilities.js" />
	<script type="text/javascript">
		function fixCharactersInAccountSelection() {
			$("form:first-of-type select option").each( replaceHtmlUmlauteInOption );
		}
		
		function onLoad() {
			fixCharactersInAccountSelection();
			// just append your onLoad function here
		}
	</script>
	<body onLoad="onLoad()">
	<g:render template="accountSelectionTemplate" model="[accounts: accounts,selectedAccount: null]" />
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
