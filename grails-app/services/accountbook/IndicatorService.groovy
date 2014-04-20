package accountbook

import grails.transaction.Transactional

import org.book.account.domain.*;

@Transactional
class IndicatorService {
        
	def create(ILedger ledger, String name,IAccount account,Amount threshold,Date validUntil,boolean reachThresholdEveryDay) {
		//AccountClosureThresholdPerformanceIndicator newIndicator = new AccountClosureThresholdPerformanceIndicator(ledger, name, threshold, account, validUntil, reachThresholdEveryDay);  
		//ledger.save(true);
		//return newIndicator;
    }
	
	def list(ILedger ledger) {
		return null; 
		/*def result = AccountClosureThresholdPerformanceIndicator.findByLedger(ledger);
		if ( result == null ) {
			return new LinkedList<AccountClosureThresholdPerformanceIndicator>();
		} else {
			return result;
		}*/
	}
}
