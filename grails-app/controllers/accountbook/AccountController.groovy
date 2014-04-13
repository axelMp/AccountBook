package accountbook

import org.book.account.domain.Ledger
import org.book.account.domain.Account

class AccountController {
	def accountService
	def ledgerService
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		def accounts = accountService.listAccounts(ledger);
		respond accounts, model:[accounts: accounts];
	}
	
	def create() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
	    if (params.name && params.type) {
			accountService.createAccount(ledger,params.name,params.type);
			redirect(action: "index");
		}
	}
	
	def delete(Account anAccount) {
		checkSession();
		def ledger = bookService.retrieve(session["Ledger"]);
		
		accountService.delete(ledger,anAccount);
		redirect(action: "index");
	}
	
	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger");
		}
	}
}
