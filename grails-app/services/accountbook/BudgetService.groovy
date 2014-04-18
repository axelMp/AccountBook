package accountbook

import grails.transaction.Transactional

import org.book.account.domain.*;

@Transactional
class BudgetService {
	
	def create(Ledger aLedger) {
		def queryResult = Budget.findByLedger(aLedger);
	    if ( null != queryResult && !queryResult.isEmpty()) {
			throw new IllegalArgumentException("Ledger with name "+aLedger.getName()+" already has a budget associated with it");
		}
		Budget aBudget = new Budget(aLedger);
		aBudget.save(true);
		return aBudget;
	}
	
	def retrieve(Ledger aLedger) {
		def queryResult = Budget.findByLedger(aLedger);
	    if ( null == queryResult) {
			throw new IllegalArgumentException("Ledger with name "+aLedger.getName()+" has no budget associated with it");
		}
		
		return queryResult;
	}
}
