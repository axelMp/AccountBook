<li><%="$accountType"%> <g:render template="accountTableTemplate"/>
<ul>
<%accounts.each { account -> %>
 <g:if test="${account.accountType == accountType && account.name != selectedAccount.name}">
  <g:render template="accountTemplate" model="[account:account]" />
 </g:if>
<%}%>
</ul>