<!DOCTYPE html>
<html>
	<body>
	<form name="input" action="create" method="get">
		description: <input type="text" name="description"></br>
		occurredOn : <input type="text" name="occurredOn"></br>
		amount: <input type="text" name="cents"> <select name="currency" size="1">
		<% currencies.each { currency -> %>
        <option><%="${currency.toString()}" %></option>
	  <%}%>
		</select> </br> 
		from: <select name="from" size="1">
		  <% accounts.each { account -> %>
           <option><%="${account.name}" %></option>
	      <%}%>
		</select> </br> 
		
		to: <select name="to" size="1">
		  <% accounts.each { account -> %>
           <option><%="${account.name}" %></option>
	      <%}%>
		</select> </br> 
		
		<input type="submit" value="Submit">
	</form> 
	</body>
</html>
