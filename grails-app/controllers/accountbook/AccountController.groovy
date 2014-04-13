package accountbook

import org.book.account.domain.AccountSystem
import org.book.account.domain.Account
import org.book.account.domain.PhysicalAccount

class AccountController {
	def accountService
	def bookService
	
    def index() {
		checkSession();
		def book = bookService.retrieve(session["accountSystem"]);
		
		def accounts = accountService.listAccounts(book);
		respond accounts, model:[accounts: accounts];
	}
	
	def create() {
		checkSession();
		def book = bookService.retrieve(session["accountSystem"]);
		
	    if (params.name && params.type) {
			accountService.createAccount(book,params.name,params.type);
			redirect(action: "index");
		}
	}
	
	def delete(Account anAccount) {
		checkSession();
		def book = bookService.retrieve(session["accountSystem"]);
		
		accountService.delete(book,anAccount);
		redirect(action: "index");
	}
	
	private def checkSession() {
		if ( null == session["accountSystem"] ) {
			redirect(url: "/book");
		}
	}
}
