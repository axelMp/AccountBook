<script type="text/javascript">
function selectAccount(select,url) {
  var selectedValue = select.options[select.options.selectedIndex].value;
  if (selectedValue) {
    url = url + "?selected=" + encodeURIComponent(selectedValue);
  }
  window.location.href= url;
}
</script>

<form action="" >
	view budget for account: <select name="selected" size="1" onchange="selectAccount(this,'<g:createLink controller="Budget" action="index"/>')">
		<option></option>
		<% accounts.each { account -> %>
			<option<g:if test="${null != selectedAccount && account.name == selectedAccount.name}"><%=" selected"%></g:if><g:else><%=""%></g:else>
			value="<%="${account.name}" %>"><%="${account.name}" %></option>
	    <%}%>
		</select>
</form> 