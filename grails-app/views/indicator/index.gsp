<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Indicator</th></tr>
		
	  <% indicators.each { indicator -> %>
        <tr><td><%="${indicator.name}" %></td></tr>
	  <%}%>
	  
	</table>
	</body>
</html>
