package accountbook

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.Ledger

@Transactional
class LedgerController {
	def ledgerService;
	def budgetService;
	
    def index() { 
	    if ( null == session["Ledger"] ) {
			def ledgers = Ledger.list();
			if ( ledgers.isEmpty() ) {
				redirect(action:"create");
			}
			respond ledgers, model:[ledgers: ledgers];
		} else {
			redirect(url: "/account");
		}
	}
	
	def create() {
	    if (params.name) {
			def aLedger = ledgerService.create(params.name);
			redirect(action: "index");
		}
	}
	
	def select() {
		if (params.name) {
			session["Ledger"]=params.name;
		}
		
		redirect(action: "index");
	}
}
