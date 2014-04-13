<!DOCTYPE html>
<html>
	<body>
	<table>
	<tr><th>Select book</th></tr>
		
	  <% books.each { book -> %>
        <tr><td><a href="/book/select?name=${book.name}"><%="${book.name}" %></a> <form name="input" action="delete" method="get"><input type="submit" value="Delete"></form></td></tr>
	  <%}%>
	  
	</table>
	</body>
</html>
