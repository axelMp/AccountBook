<!DOCTYPE html>
<html>
	<body>
	<form name="input" action="create" method="get">
		narration: <input type="text" name="narration"></br>
		occurredOn : <input type="text" name="occurredOn"> (dd-MM-yyyy)</br>
		amount: <input type="text" name="cents"> <select name="currency" size="1">
		<% currencies.each { currency -> %>
        <option><%="${currency.toString()}" %></option>
	  <%}%>
		</select> </br> 
		debitor: <select name="debitor" size="1">
		  <% accounts.each { account -> %>
           <option><%="${account.name}" %></option>
	      <%}%>
		</select> </br> 
		
		creditor: <select name="creditor" size="1">
		  <% accounts.each { account -> %>
           <option><%="${account.name}" %></option>
	      <%}%>
		</select> </br> 
		
		<input type="submit" value="Submit">
	</form> 
	</body>
</html>
