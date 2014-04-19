package accountbook

import grails.transaction.Transactional

import org.book.account.domain.*;

@Transactional
class IndicatorService {
        
	def create(Ledger ledger, String name,Account account,Amount threshold,Date validUntil,boolean reachThresholdEveryDay) {
		AccountClosureThresholdPerformanceIndicator newIndicator = new AccountClosureThresholdPerformanceIndicator(ledger, name, threshold, account, validUntil, reachThresholdEveryDay);  
		newIndicator.save(true);
		return newIndicator;
    }
	
	def list(Ledger ledger) {
		def result = AccountClosureThresholdPerformanceIndicator.findByLedger(ledger);
		if ( result == null ) {
			return new LinkedList<AccountClosureThresholdPerformanceIndicator>();
		} else {
			return result;
		}
	}
}
