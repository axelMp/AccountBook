<!DOCTYPE html>
<html>
	<body>
	<form name="input" action="create" method="get">
		narration: <input type="text" name="narration"></br>
		startsOn : <input type="text" name="startsOn"> (dd-MM-yyyy)</br>
		endsOn : <input type="text" name="endsOn"> (dd-MM-yyyy)</br>
		isContinuous: <select name="isContinuous" size="1">
			<option>true</option>
			<option>false</option>
		</select></br>
		amount: <input type="text" name="cents"> 
		<select name="currency" size="1">
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
