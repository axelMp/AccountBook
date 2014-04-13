package accountbook

import grails.transaction.Transactional

import org.book.account.domain.AccountSystem;
import org.book.account.domain.Account;
import org.book.account.domain.Amount;
import org.book.account.domain.Transaction;

@Transactional
class TransactionService {
    
    def create(AccountSystem book,String text,Date occurredOn,Integer cents,String currency,Account from, Account to) {
	    if ( ! transactions.containsKey(book)) {
			transactions.put(book,new LinkedList<Transaction>());
		}
		
		Amount amount = new Amount(cents,Amount.Currency.valueOf(currency));
		
		Transaction newTransaction = book.book(text,occurredOn,amount,from,to);
		transactions.get(book).put(newTransaction);
		return newTransaction;
    }
	
	def list(AccountSystem book) {
		return transactions.get(book);
	}
	
	private final Map<AccountSystem,List<Transaction>> transactions = new HashMap<AccountSystem,List<Transaction> >();
}
