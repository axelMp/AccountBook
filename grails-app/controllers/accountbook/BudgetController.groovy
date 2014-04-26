package accountbook

import java.util.Date;

import org.codehaus.groovy.grails.web.json.*;
import grails.rest.*

import org.book.account.domain.*

@Resource(uri='/budget')
class BudgetController {
	def ledgerService;
	def accountService;
	def utilitiesService;

	
	def update() {
		def id = request.JSON.id.toString();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		for( aPlannedTransaction in ledger.budget.getPlannedTransactions() ) { 
			if ( aPlannedTransaction.id.toString().equals(id) ) {
				
				if (request.JSON.narration) {
					aPlannedTransaction.narration = request.JSON.narration;
				}
				
				if (request.JSON.startsOn) {
					Period newPeriod = new Period(utilitiesService.parseDate(request.JSON.startsOn),aPlannedTransaction.schedule.period.endsOn);
					aPlannedTransaction.schedule = new Schedule(newPeriod,aPlannedTransaction.schedule.executionPolicy);
				}
				
				if (request.JSON.endsOn) {
					Period newPeriod = new Period(aPlannedTransaction.schedule.period.endsOn,utilitiesService.parseDate(request.JSON.endsOn));
					aPlannedTransaction.schedule = new Schedule(newPeriod,aPlannedTransaction.schedule.executionPolicy);
				}
				
				if (request.JSON.executionPolicy) {
					aPlannedTransaction.schedule = new Schedule(aPlannedTransaction.schedule.period,ExecutionPolicy.valueOf(request.JSON.executionPolicy));
				}
				
				if (request.JSON.cents) {
					int cents = Math.round(100*Float.parseFloat(request.JSON.cents));
					aPlannedTransaction.amount = new Amount(cents,aPlannedTransaction.amount.currency);
				}
				
				aPlannedTransaction.save(true);
				respond status: 204;
				return;
			}
		}
		respond status: 404
	}
	
	
    def listExecutionPolicies() {
		render(contentType: "text/json") {
			array {
				for(p in ExecutionPolicy.values()){
					policy executionPolicy: p.toString()
				}
			}
		}	
	}
	
	
    def index() {
		def ledger = ledgerService.retrieve(session["Ledger"]);

		if (params.selected) {
			def name = utilitiesService.replaceHtmlEncodings(params.selected);
		
			def account = accountService.retrieve(ledger,name)
			
			withFormat {
			html {
				render(view:'forSingleAccount', model:[selectedAccount:account,accounts:accountService.list(ledger)]);
			}
			json {
				render(contentType: "text/json") {
					array {
						for(p in account.getPlannedTransactions()){
							plannedTransaction narration: p.narration, executionPolicy:p.schedule.executionPolicy.toString(), creditor:p.creditor.name, debitor:p.debitor.name, startsOn:utilitiesService.encodeDate(p.schedule.period.startsOn), endsOn:utilitiesService.encodeDate(p.schedule.period.endsOn), cents:p.amount.cents,id:p.id, currency:p.amount.currency.toString()
							}
						}
					}
				}
			}
		}
		
		def plan = ledger.getBudget().getPlannedTransactions();
		
		withFormat {
			html {
				respond plan, model:[plan: plan,accounts:accountService.list(ledger)]
			}
			json {
				render(contentType: "text/json") {
					array {
						for(p in plan){
							plannedTransaction narration: p.narration, executionPolicy:p.schedule.executionPolicy.toString(), creditor:p.creditor.name, debitor:p.debitor.name, startsOn:utilitiesService.encodeDate(p.schedule.period.startsOn), endsOn:utilitiesService.encodeDate(p.schedule.period.endsOn), id:p.id , cents:p.amount.cents,currency:p.amount.currency.toString()
						}
					}
				}
			}
		}
	}
	
	def create() {
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		if ( request.format == "json" ) {
			for ( int nrElement = 0 ; nrElement < request.JSON.size() ; ++nrElement ) {
				JSONObject model = request.JSON.getJSONObject(nrElement);
				addToPlan(ledger, model.getString("narration"),model.getString("startsOn"),model.getString("endsOn"),model.getInt("cents"),model.getString("currency"),model.getString("debitor"),model.getString("creditor"),model.getString("executionPolicy"));
			}
			redirect(action: "index");
		} else if (params.narration && params.startsOn && params.endsOn && params.cents && params.currency && params.debitor && params.creditor && params.executionPolicy) {
			addToPlan(ledger, params.narration,params.startsOn,params.endsOn,Integer.parseInt(params.cents),params.currency,params.debitor,params.creditor,params.executionPolicy);
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.list(ledger),executionPolicies:ExecutionPolicy.values()]
	}
	
	private void addToPlan(ILedger ledger, String narration, String startsOnEncoded, String endsOnEncoded,Integer cents,String currency,String debitorName,String creditorName,String executionType) {
	    def amount = new Amount(cents,Amount.Currency.valueOf(currency));
		Date startsOn = utilitiesService.parseDate(startsOnEncoded);
		Date endsOn = utilitiesService.parseDate(endsOnEncoded);
		ExecutionPolicy execution = ExecutionPolicy.valueOf(executionType);
		IAccount debitor = accountService.retrieve(ledger,debitorName);
		IAccount creditor = accountService.retrieve(ledger,creditorName);
		ledgerService.plan(ledger,narration, startsOn, endsOn, amount, debitor, creditor, execution);
	}
}
