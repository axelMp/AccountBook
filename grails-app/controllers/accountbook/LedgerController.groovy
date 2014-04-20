package accountbook

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.*

@Transactional
class LedgerController {
	def ledgerService;
	
    def index() { 
		def ledgers = ledgerService.list();
	    if ( null == session["Ledger"] ) {
			if ( ledgers.isEmpty() ) {
				redirect(action:"create");
			}
			respond ledgers, model:[ledgers: ledgers];
		} else {
			withFormat {
				html {
					redirect(url: "/account");
				}
				json {
					render(contentType: "text/json") {
						array {
							for(l in ledgers){
								ledger name: l.name
							}
						}
					}
				}
			}
		}
	}
	
	def create() {
	    if (params.name) {
			ledgerService.create(params.name);
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
