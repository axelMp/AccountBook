package accountbook

import grails.transaction.Transactional

import org.book.account.domain.*;
import org.book.account.core.Ledger;
import org.book.account.core.Factory;

@Transactional
class LedgerService {;
	IFactory domainFactory = new Factory();
	
    def create(String name) {
		
		def queryResult = Ledger.findByName(name);
	    if ( null != queryResult && !queryResult.isEmpty()) {
			throw new IllegalArgumentException("Ledger with name "+name+" already exists");
		}
		Ledger aLedger = domainFactory.createLedger(name);
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
	
	def list() {
		return Ledger.list();
	}
}
