package accountbook

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.*

class BudgetController {
	def ledgerService;
	def budgetService;
	def accountService;
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		def budget = budgetService.retrieve(ledger);
		
		def plan = budget.getPlannedTransactions();
		respond plan, model:[plan: plan];
	}
	
	
	def create() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		def budget = budgetService.retrieve(ledger);
	
	    if (params.narration && params.startsOn && params.endsOn && params.cents && params.currency && params.debitor && params.creditor && params.execution) {
			def amount = new Amount(Integer.parseInt(params.cents),Amount.Currency.valueOf(params.currency));
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date startsOn = formatter.parse(params.startsOn);
			Date endsOn = formatter.parse(params.endsOn);
			PlannedTransaction.Execution execution = PlannedTransaction.Execution.valueOf(params.execution);
			
			Account debitor = accountService.retrieve(ledger,params.debitor);
			Account creditor = accountService.retrieve(ledger,params.creditor);
			def plannedTransaction = budget.plan(params.narration, startsOn, endsOn, amount, debitor, creditor, execution);
			plannedTransaction.save();
			budget.save(true);
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.list(ledger),executions:PlannedTransaction.Execution.values()]
	}
	
	
	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger");
		}
	}
}
