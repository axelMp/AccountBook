package accountbook


import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.*

class IndicatorController {
	def ledgerService;
	def indicatorService;
	def accountService;

    def index() {
		def ledger = ledgerService.retrieve(session["Ledger"]);
		def indicators = indicatorService.list(ledger);
		respond indicators, model:[indicators: indicators];
	}
	
	def create() {
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
	    if (params.name && params.account && params.cents && params.currency && params.validUntil && params.reachThresholdEveryDay) {
			def threshold = new Amount(Integer.parseInt(params.cents),Amount.Currency.valueOf(params.currency));
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date validUntil = formatter.parse(params.validUntil);
			boolean reachThresholdEveryDay = "true".equals(params.reachThresholdEveryDay);
			IAccount account = accountService.retrieve(ledger,params.account);
			def indicator = indicatorService.create(ledger,params.name,account,threshold,validUntil,reachThresholdEveryDay);
			redirect(action: "index");
		}
		[accounts: accountService.list(ledger),currencies:Amount.Currency.values()]
	}
}
