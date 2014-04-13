package accountbook

import org.book.account.domain.Ledger;

import grails.transaction.Transactional

@Transactional
class LedgerService {

    def create(String name) {
		def queryResult = Ledger.findByName(name);
	    if ( null != queryResult && !queryResult.isEmpty()) {
			throw new IllegalArgumentException("Ledger with name "+name+" already exists");
		}
		Ledger aLedger = new Ledger(name);
		aLedger.save(true);
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
