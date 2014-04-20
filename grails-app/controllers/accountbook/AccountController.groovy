package accountbook

import org.codehaus.groovy.grails.web.json.*;
import org.book.account.domain.*

class AccountController {
	def accountService
	def ledgerService
	
    def index() {
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		def accounts = ledger.getAccounts();
		withFormat {
			html {
				respond accounts, model:[accounts: accounts]
			}
			json {
				render(contentType: "text/json") {
					array {
						for(a in accounts){
							account name: a.name, type:a.accountType.toString()
						}
					}
				}
			}
		}
	}
	
	def create() {
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		if ( request.format == "json" ) {
			for ( int nrElement = 0 ; nrElement < request.JSON.size() ; ++nrElement ) {
				JSONObject model = request.JSON.getJSONObject(nrElement);
				createAccount(ledger, model.getString("name"),model.getString("type"));
			}
			
		} else if (params.name && params.type) {
			createAccount(ledger,params.name,params.type);
		}
		redirect(action: "index");
	}
	
	private void createAccount(ILedger ledger, String name,String accountType) {
		accountService.create(ledger,name,accountType);
	}
}
