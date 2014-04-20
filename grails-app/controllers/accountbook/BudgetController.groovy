package accountbook

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.*

class BudgetController {
	def ledgerService;
	def accountService;
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		def budget = ledger.getBudget();
		
		def plan = budget.getPlannedTransactions();
		respond plan, model:[plan: plan];
	}
	
	
	def create() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		def budget = ledger.getBudget();
	
	    if (params.narration && params.startsOn && params.endsOn && params.cents && params.currency && params.debitor && params.creditor && params.execution) {
			def amount = new Amount(Integer.parseInt(params.cents),Amount.Currency.valueOf(params.currency));
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date startsOn = formatter.parse(params.startsOn);
			Date endsOn = formatter.parse(params.endsOn);
			ExecutionOfPlannedTransaction execution = ExecutionOfPlannedTransaction.valueOf(params.execution);
			
			IAccount debitor = accountService.retrieve(ledger,params.debitor);
			IAccount creditor = accountService.retrieve(ledger,params.creditor);
			def plannedTransaction = budget.plan(params.narration, startsOn, endsOn, amount, debitor, creditor, execution);
			ledger.save(true);
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.list(ledger),executions:ExecutionOfPlannedTransaction.values()]
	}
	
	
	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger");
		}
	}
}
