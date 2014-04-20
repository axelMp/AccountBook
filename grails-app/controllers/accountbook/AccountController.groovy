package accountbook

import org.book.account.domain.*

class AccountController {
	def accountService
	def ledgerService
	
    def index() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		def accounts = ledger.getAccounts();
		respond accounts, model:[accounts: accounts];
	}
	
	def create() {
		checkSession();
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
	    if (params.name && params.type) {
			accountService.create(ledger,params.name,params.type);
			redirect(action: "index");
		}
	}
	
	private def checkSession() {
		if ( null == session["Ledger"] ) {
			redirect(url: "/ledger");
		}
	}
}
