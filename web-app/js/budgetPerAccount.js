function updatePlannedTransaction(id,attribute,value) {
	//console.log("updating attribute "+attribute+" to "+ value+" for id "+id); 
	var data = {};
	data.id = id;
	data[attribute] = value;
	$.ajax( {
		url: i,
		type: 'PUT',
		data: JSON.stringify(data),
		dataType: 'json'
	});
}


function updatePlannedTransactionTextField(i,inputNode) {
	updatePlannedTransaction(i,inputNode.name,inputNode.value);
}


function updatePlannedTransactionDropDown(i,select) {
	var selectedValue = select.options[select.options.selectedIndex].value;
	updatePlannedTransaction(i,select.name,selectedValue);
}


function visualizeAsTextField(nameOfField, text,id) {
   return "<input type=\"text\" onChange=\"updatePlannedTransactionTextField("+id+",this)\" name=\""+nameOfField+"\" value=\""+text+"\" size=\"10\">";
}


function visualizeAsDropDown(nameOfField,text,id,validValues) {
	var header = "<select name=\""+nameOfField+"\" onchange=\"updatePlannedTransactionDropDown("+id+",this)\" size=\"1\">";
	var options = "";
	for(i=0,x=validValues.length;i<x;i++){
		var optionName = validValues[i];
		options = options + "<option value=\""+optionName+"\"";
		if ( optionName == text ) {
			options = options + " selected=\"selected\"";
		}
		options = options + ">"+optionName+"</option>";
	}
	var footer = "</select>";
	return header + options + footer;
}

function visualizeScheduleForEditing(aPlannedTransaction,policies) {
	return "from "+visualizeAsTextField("startsOn",aPlannedTransaction.startsOn,aPlannedTransaction.id) + " until " + visualizeAsTextField("endsOn",aPlannedTransaction.endsOn,aPlannedTransaction.id) + " with policy "+visualizeAsDropDown("executionPolicy",aPlannedTransaction.executionPolicy,aPlannedTransaction.id,policies);
}

function visualizeAmountForEditing(aPlannedTransaction) {
	var amount = visualizeAsTextField("cents",aPlannedTransaction.cents/100.0,aPlannedTransaction.id);
	if ( aPlannedTransaction.currency == "EUR" ) {
		amount = amount + "€";
	}
	return amount;
}

function visualizeForEditing(aPlannedTransaction,policies) {
	var WHITESPACE = "&nbsp;"
	var title = visualizeAsTextField("narration",aPlannedTransaction.narration,aPlannedTransaction.id);
	var amount = visualizeAmountForEditing(aPlannedTransaction);
	var schedule = visualizeScheduleForEditing(aPlannedTransaction,policies);
	return "<form>"+title + WHITESPACE + amount + WHITESPACE + schedule+"</form>";
}

function getAvailableExecutionPolicies() {
	var response = $.ajax({
        type: "GET",
        url: "listExecutionPolicies",
        async: false
    }).responseText;
	
	return response;
}

function distributeJSon(data,selectedAccountEncoded) {
	var selectedAccount = replaceHtmlUmlaute(selectedAccountEncoded);
	var policies = new Array();
	var availablePolicies = JSON.parse(getAvailableExecutionPolicies());
	for(i=0,x=availablePolicies.length;i<x;i++){
		var aString = availablePolicies[i].executionPolicy;
		policies.push(aString);
	}
	
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
					encodedTransactions = encodedTransactions + "<li>"+visualizeForEditing(element,policies)+"</li>"
				})
				return encodedTransactions;
			});
		}
	});
}