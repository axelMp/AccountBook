package accountbook

import grails.transaction.Transactional

import org.book.account.domain.*;

@Transactional
class TransactionService {
    
    def create(ILedger ledger,String text,Date occurredOn,Amount amount,IAccount debitor, IAccount creditor) {
		ITransaction newTransaction = ledger.book(text,occurredOn,amount,debitor,creditor);
		ledger.save(true);
		return newTransaction;
    }
}
