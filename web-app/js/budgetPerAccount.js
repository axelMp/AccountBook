function visualizeAsTextField(nameOfField, text) {
   return "<input type=\"text\" name=\""+nameOfField+"\" value=\""+text+"\">";
}

function visualizeDate(name,dateD) {
	return "<input type=\"text\" name=\""+name+"\" value=\""+dateD+"\">";
}

function visualizeScheduleForEditing(aPlannedTransaction) {
	return "from "+visualizeDate("startsOn",aPlannedTransaction.startsOn) + " until " + visualizeDate("endsOn",aPlannedTransaction.endsOn) + " with policy "+aPlannedTransaction.executionPolicy;
}

function visualizeAmountForEditing(aPlannedTransaction) {
	var amount = visualizeAsTextField("cents",aPlannedTransaction.cents/100.0);
	if ( aPlannedTransaction.currency == "EUR" ) {
		amount = amount + "€";
	}
	return amount;
}

function visualizeForEditing(aPlannedTransaction) {
	var WHITESPACE = "&nbsp;"
	var title = visualizeAsTextField("title",aPlannedTransaction.narration);
	var amount = visualizeAmountForEditing(aPlannedTransaction);
	var schedule = visualizeScheduleForEditing(aPlannedTransaction);
	return "<form>"+title + WHITESPACE + amount + WHITESPACE + schedule+"</form>";
}

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
					encodedTransactions = encodedTransactions + "<li>"+visualizeForEditing(element)+"</li>"
				})
				return encodedTransactions;
			});
		}
	});
}