package accountbook

import java.text.SimpleDateFormat;
import java.util.Date;

import org.book.account.domain.Ledger
import org.book.account.domain.Account
import org.book.account.domain.Amount

class TransactionController {
	def ledgerService
	def accountService
	def transactionService
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		def transactions = transactionService.list(ledger);
		respond transactions, model:[transactions: transactions];
	}
	
	
	def create() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
	    if (params.narration && params.occurredOn && params.cents && params.currency && params.debitor && params.creditor) {
			def amount = new Amount(Integer.parseInt(params.cents),Amount.Currency.valueOf(params.currency));
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date dateStr = formatter.parse(params.occurredOn);
        
			Account debitor = accountService.retrieve(ledger,params.debitor);
			Account creditor = accountService.retrieve(ledger,params.creditor);
			transactionService.create(ledger,params.narration, dateStr, amount , debitor, creditor);
			
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.list(ledger)]
	}

	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger/");
		}
	}
}
