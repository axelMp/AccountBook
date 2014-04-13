package accountbook

import grails.transaction.Transactional

import org.book.account.domain.Ledger;
import org.book.account.domain.Account;
import org.book.account.domain.Amount;
import org.book.account.domain.Transaction;

@Transactional
class TransactionService {
    
    def create(Ledger ledger,String text,Date occurredOn,Amount amount,Account debitor, Account creditor) {
		Transaction newTransaction = ledger.book(text,occurredOn,amount,debitor,creditor);
		ledger.save();
		newTransaction.save(true);
		return newTransaction;
    }
	
	def list(Ledger ledger) {
		ledger.getTransactions();
	}
}
