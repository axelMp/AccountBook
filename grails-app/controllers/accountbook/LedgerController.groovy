package accountbook

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.Ledger

@Transactional(readOnly = true)
class LedgerController {
	def ledgerService;
	
    def index() { 
	    if ( null == session["Ledger"] ) {
			def ledgers = ledgerService.list();
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
			ledgerService.create(params.name);
			redirect(action: "index");
		}
	}
	
	def delete(Ledger anLedger) {
		ledgerService.delete(anLedger);
		redirect(action: "index");
	}
	
	def select() {
		if (params.name) {
			session["Ledger"]=params.name;
		}
		
		redirect(action: "index");
	}
}
