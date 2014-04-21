function distributeJSon(data,selectedAccountEncoded) {
	var selectedAccount = replaceHtmlUmlaute(selectedAccountEncoded);
	// sort transactions according to in which account they'll be displayed
	var myMap = new Map();
	for (item of data) {
		var account = item.creditor;
		if ( account == selectedAccount) {
			account = item.debitor;
		}
		if( !myMap.has(account)) {
			myMap.set(account,new Array());
		}
		myMap.get(account).push(item);
	}
	
	$("ul li ul li" ).each(function(){
		var title = $(this).attr('title');
		if (myMap.has(title)) {
			$(this).children().append(function(index,html) {
				var encodedTransactions = "";
				myMap.get(title).forEach( function(element, index, array) {
					encodedTransactions = encodedTransactions + "<li>"+element.narration+"</li>"
				})
				return encodedTransactions;
			});
		}
	});
}