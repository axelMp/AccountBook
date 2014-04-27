function formatAmount(cents,currency) {
	var amount = cents/100.0;
	if ( currency == "EUR" ) {
		amount = amount + "€";
	}
	return amount;
}
		
function formatForecastForAccount(domRepresentation,forecastData) {
	var data;
	try {
		data = JSON.parse(forecastData);
	} catch(e) {
		data = forecastData;
	}
	var aChild = domRepresentation.firstChild;
	while(aChild.id != "JAN") {
		aChild = aChild.nextSibling;
	}
	aChild.innerHTML = formatAmount(data[0].cents,data[0].currency);
	for ( var i = 1; i < 12 ; ++i ) {
		aChild = aChild.nextSibling;
		aChild = aChild.nextSibling;
		aChild.innerHTML = formatAmount(data[i].cents,data[i].currency);
	}
}
	
function fillAccountWithForecast(domRepresentation,relativeTo) {	
	var data = {};
	data.account = domRepresentation.title;
	data.relativeTo = relativeTo;
	data.format = "json";
	$.ajax( {
		url: "forecastClosure", 
		data: data, 
		success: function(forecastData) {		
						formatForecastForAccount(domRepresentation,forecastData);
					},
		type: "GET",
		datatype: "json"
	});
}


function updatePlannedTransaction(id,attribute,value,domNode,relativeTo) {
	var data = {};
	data.id = id;
	data[attribute] = value;
	$.ajax( {
		url: i,
		type: 'PUT',
		data: JSON.stringify(data),
		dataType: 'json',
		complete: function() {
			for ( var i = 0; i < 4;++i ) {
				domNode = domNode.parentNode;
			}
			fillAccountWithForecast(domNode,relativeTo);
		}
	});
}


function updatePlannedTransactionTextField(i,inputNode,relativeTo) {
	updatePlannedTransaction(i,inputNode.name,inputNode.value,inputNode,relativeTo);
}


function updatePlannedTransactionDropDown(i,select,relativeTo) {
	var selectedValue = select.options[select.options.selectedIndex].value;
	updatePlannedTransaction(i,select.name,selectedValue,select,relativeTo);
}


function visualizeAsTextField(nameOfField, text,id,relativeTo) {
   return "<input type=\"text\" onChange=\"updatePlannedTransactionTextField("+id+",this,'"+relativeTo+"')\" name=\""+nameOfField+"\" value=\""+text+"\" size=\"10\">";
}


function visualizeAsDropDown(nameOfField,text,id,validValues,relativeTo) {
	var header = "<select name=\""+nameOfField+"\" onchange=\"updatePlannedTransactionDropDown("+id+",this,'"+relativeTo+"')\" size=\"1\">";
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

function visualizeScheduleForEditing(aPlannedTransaction,policies,relativeTo) {
	return "from "+visualizeAsTextField("startsOn",aPlannedTransaction.startsOn,aPlannedTransaction.id,relativeTo) + " until " + visualizeAsTextField("endsOn",aPlannedTransaction.endsOn,aPlannedTransaction.id,relativeTo) + " with policy "+visualizeAsDropDown("executionPolicy",aPlannedTransaction.executionPolicy,aPlannedTransaction.id,policies,relativeTo);
}

function visualizeAmountForEditing(aPlannedTransaction,relativeTo) {
	var amount = visualizeAsTextField("cents",aPlannedTransaction.cents/100.0,aPlannedTransaction.id,relativeTo);
	if ( aPlannedTransaction.currency == "EUR" ) {
		amount = amount + "€";
	}
	return amount;
}

function visualizeForEditing(aPlannedTransaction,policies,relativeTo) {
	var title = visualizeAsTextField("narration",aPlannedTransaction.narration,aPlannedTransaction.id,relativeTo);
	var amount = visualizeAmountForEditing(aPlannedTransaction,relativeTo);
	
	var schedule = visualizeScheduleForEditing(aPlannedTransaction,policies,relativeTo);
	return "<form>"+title + " " + amount + " " + schedule+"</form>";
}

function getAvailableExecutionPolicies() {
	return $.ajax({
        type: "GET",
        url: "listExecutionPolicies",
        async: false
    }).responseText;
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
			$(this).children("ul").append(function(index,html) {
				var encodedTransactions = "";
				myMap.get(title).forEach( function(element, index, array) {
					encodedTransactions = encodedTransactions + "<li>"+visualizeForEditing(element,policies,selectedAccount)+"</li>"
				})
				return encodedTransactions;
			});
		}
	});
}