package accountbook

import java.util.Date;

import org.codehaus.groovy.grails.web.json.*;

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.*

class BudgetController {
	def ledgerService;
	def accountService;
	def utilitiesService;
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		def plan = ledger.getBudget().getPlannedTransactions();
		
		withFormat {
			html {
				respond plan, model:[plan: plan]
			}
			json {
				render(contentType: "text/json") {
					array {
						for(p in plan){
							plannedTransaction narration: p.narration, execution:p.executionOfPlannedTransaction.toString(), creditor:p.creditor.name, debitor:p.debitor.name, startsOn:utilitiesService.encodeDate(p.startsOn), endsOn:utilitiesService.encodeDate(p.endsOn), cents:p.amount.cents,currency:p.amount.currency.toString()
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
				addToPlan(ledger, model.getString("narration"),model.getString("startsOn"),model.getString("endsOn"),model.getInt("cents"),model.getString("currency"),model.getString("debitor"),model.getString("creditor"),model.getString("execution"));
			}
			redirect(action: "index");
		} else if (params.narration && params.startsOn && params.endsOn && params.cents && params.currency && params.debitor && params.creditor && params.execution) {
			addToPlan(ledger, params.narration,params.startsOn,params.endsOn,Integer.parseInt(params.cents),params.currency,params.debitor,params.creditor,params.execution);
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.list(ledger),executions:ExecutionOfPlannedTransaction.values()]
	}
	
	private void addToPlan(ILedger ledger, String narration, String startsOnEncoded, String endsOnEncoded,Integer cents,String currency,String debitorName,String creditorName,String executionType) {
	    def amount = new Amount(cents,Amount.Currency.valueOf(currency));
		Date startsOn = utilitiesService.parseDate(startsOnEncoded);
		Date endsOn = utilitiesService.parseDate(endsOnEncoded);
		ExecutionOfPlannedTransaction execution = ExecutionOfPlannedTransaction.valueOf(executionType);
		
		IAccount debitor = accountService.retrieve(ledger,debitorName);
		IAccount creditor = accountService.retrieve(ledger,creditorName);
		ledgerService.plan(ledger,narration, startsOn, endsOn, amount, debitor, creditor, execution);
	}
	
	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger");
		}
	}
}
