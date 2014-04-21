function replaceHtmlUmlaute(aString) {
	return aString.replace(/&uuml;/g, "ü").replace(/&ouml;/g, "ö").replace(/&auml;/g, "ä").replace(/&amp;/g, "&");
}

function replaceHtmlUmlauteInOption() {
	if( $(this).attr('value') != undefined ) {
		var value = replaceHtmlUmlaute($(this).attr('value'));
		$(this).attr('value',value);
	}
} 