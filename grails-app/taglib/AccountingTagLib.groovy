class AccountingTagLib {
	def dateFormat = { attrs, body ->
		out << new java.text.SimpleDateFormat("dd-MM-yyyy").format(attrs.date)
	}
	
	def amountFormat = { attrs, body ->
		out << String.format("%.2f", attrs.amount.cents / 100.0f)
		if ( attrs.amount.currency.toString().equals("EUR") ) {
			out << '&euro;'
		} 
	}
}