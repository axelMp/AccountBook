package accountbook

import org.book.account.domain.*;

import grails.transaction.Transactional

@Transactional
class LedgerService {
	def budgetService;

    def create(String name) {
		def queryResult = Ledger.findByName(name);
	    if ( null != queryResult && !queryResult.isEmpty()) {
			throw new IllegalArgumentException("Ledger with name "+name+" already exists");
		}
		Ledger aLedger = new Ledger(name);
		aLedger.save(true);
		budgetService.create(aLedger);
		return aLedger;
    }
	
	def retrieve(String name) {
		for ( Ledger aLedger:Ledger.list() ) {
			if ( aLedger.getName().equals(name)) {
				return aLedger;
			}
		}
		throw new IllegalArgumentException("Unknown ledger with name "+name);
	}
}
