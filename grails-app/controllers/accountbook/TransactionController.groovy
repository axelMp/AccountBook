package accountbook

import java.util.Date;

import org.codehaus.groovy.grails.web.json.*;
import org.book.account.domain.*

class TransactionController {
	def ledgerService
	def accountService
	def transactionService
	def utilitiesService
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		def transactions = ledger.getTransactions();
		
		
		withFormat {
			html {
				respond transactions, model:[transactions: transactions];
			}
			json {
				render(contentType: "text/json") {
					array {
						for(t in transactions){
							transaction narration: t.narration, creditor:t.creditor.name, debitor:t.debitor.name, occurredOn:utilitiesService.encodeDate(t.occurredOn), cents:t.amount.cents,currency:t.amount.currency.toString()
						}
					}
				}
			}
		}
	}

	def create() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
			
	    if ( request.format == "json" ) {
			for ( int nrElement = 0 ; nrElement < request.JSON.size() ; ++nrElement ) {
				JSONObject model = request.JSON.getJSONObject(nrElement);
				createTransaction(ledger, model.getString("narration"),model.getString("occurredOn"),model.getInt("cents"),model.getString("currency"),model.getString("debitor"),model.getString("creditor"));
			}
			redirect(action: "index");
		} else if (params.narration && params.occurredOn && params.cents && params.currency && params.debitor && params.creditor) {
			createTransaction(ledger,params.narration, params.occurredOn, Integer.parseInt(params.cents), params.currency, params.debitor, params.creditor);
			
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.list(ledger)]
	}
	
	private void createTransaction(ILedger ledger, String narration,String occurredOn,Integer cents,String currency,String debitorName,String creditorName) {
		def amount = new Amount(cents,Amount.Currency.valueOf(currency));
		Date dateStr = utilitiesService.parseDate(occurredOn);
	
		IAccount debitor = accountService.retrieve(ledger,debitorName);
		IAccount creditor = accountService.retrieve(ledger,creditorName);
		transactionService.create(ledger,narration, dateStr, amount , debitor, creditor);
	}

	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger/");
		}
	}
}
