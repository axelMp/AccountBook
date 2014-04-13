package accountbook

import java.text.SimpleDateFormat;
import java.util.Date;

import org.book.account.domain.AccountSystem
import org.book.account.domain.Account
import org.book.account.domain.Amount

class TransactionController {
	def bookService
	def accountService
	def transactionService
	
    def index() {
		checkSession();
		def book = bookService.retrieve(session["accountSystem"]);
		
		def transactions = transactionService.list(book);
		respond transactions, model:[transactions: transactions];
	}
	
	
	def create() {
		checkSession();
		def book = bookService.retrieve(session["accountSystem"]);
		
	    if (params.description && params.occurredOn && params.cents && params.currency && params.from && params.to) {
			def amount = new Amount(Integer.parseInt(params.cents),Amount.Currency.valueOf(params.currency));
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date dateStr = formatter.parse(params.occurredOn);
        
			Account fromAccount = accountService.retrieveAccount(book,params.from);
			Account toAccount = accountService.retrieveAccount(book,params.to);
			book.book(params.description, dateStr, amount , fromAccount, toAccount);
			
			redirect(action: "index");
		}
		[currencies: Amount.Currency.values(),accounts: accountService.listAccounts(book)]
	}

	private def checkSession() {
		if ( null == session["accountSystem"] ) {
			redirect(url: "/book/");
		}
	}
}
