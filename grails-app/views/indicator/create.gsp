<!DOCTYPE html>
<html>
	<body>
	
	<form name="input" action="create" method="get">
		name: <input type="text" name="name"></br>
		validUntil : <input type="text" name="validUntil"> (dd-MM-yyyy)</br>
		threshold: <input type="text" name="cents"> <select name="currency" size="1">
		<% currencies.each { currency -> %>
        <option><%="${currency.toString()}" %></option>
	  <%}%>
		</select> </br>
		reachThresholdEveryDay: <select name="reachThresholdEveryDay" size="1">
			<option>true</option>
			<option>false</option>
		</select></br>	
		account: <select name="account" size="1">
		<% accounts.each { account -> %>
           <option><%="${account.name}" %></option>
	    <%}%>
		</select> </br> 
		<input type="submit" value="Submit">
	</form> 
	</body>
</html>
