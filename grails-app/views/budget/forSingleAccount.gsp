<!DOCTYPE html>
<html>
	<head>
	<g:javascript src="jquery-2.1.0.js" />
	<g:javascript src="budgetPerAccount.js" />
	<g:javascript src="utilities.js" />
	<script type="text/javascript">
		function fixCharactersInAccountSelection() {
			$("form:first-of-type select option").each( replaceHtmlUmlauteInOption );
		}
		
		function onLoad() {
			fixCharactersInAccountSelection();
			// just append your onLoad function here
			
			var accountName = "<%="$selectedAccount.name"%>";
			$.ajax( {
					url: "index", 
					data: {"selected": "<%="$selectedAccount.name"%>","format":"json"} , 
					success: function(data){distributeJSon(data,"<%="$selectedAccount.name"%>")},
					datatype: "json"
					});
		}
	</script>
	</head>
	<body onLoad="onLoad()">
	<g:render template="accountSelectionTemplate" model="[accounts: accounts,selectedAccount: selectedAccount]" />
	<ul>
	<% org.book.account.domain.AccountType.values().each { accountType -> %>
		<g:render template="accountTypeTemplate" model="[accountType:accountType,accounts: accounts,selectedAccount: selectedAccount]" />
    <%}%>
	
	
    <g:render template="accountTemplate" model="[account:selectedAccount]" />
	</ul>
	</body>
</html>
