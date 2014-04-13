package accountbook

import org.book.account.domain.AccountSystem;

import grails.transaction.Transactional

@Transactional
class LedgerService {

    def create(String name) {
	    if ( currentLedgers.containsKey(name)) {
			throw new IllegalArgumentException("Ledger with name "+name+" already exist");
		}
		currentLedgers.put(name,new AccountSystem(name));
		return retrieve(name);
    }
	
	def retrieve(String name) {
		return currentLedgers.get(name);
	}
	
	def delete(AccountSystem aSystem) {
		// nothing to do until hibernate
	}
	
	def list() {
		return currentLedgers.values();
	}
	
	private final Map<String,AccountSystem> currentLedgers = new HashMap<String,AccountSystem>();
}
