<form name="input" action="index" method="get" >
	view budget for account: <select name="selected" size="1">
		<option></option>
		<% accounts.each { account -> %>
			<option<g:if test="${null != selectedAccount && account.name == selectedAccount.name}"><%=" selected"%></g:if><g:else><%=""%></g:else>
			value="<%="${account.name}" %>"><%="${account.name}" %></option>
	    <%}%>
		</select>
	<input type="submit" Value="Submit">
</form> 