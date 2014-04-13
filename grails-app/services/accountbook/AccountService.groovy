package accountbook

import grails.transaction.Transactional

import org.book.account.domain.Account;
import org.book.account.domain.Ledger;

@Transactional
class AccountService {

	def create(Ledger ledger, String name,String typeOfAccount) {
		Account.AccountType accountType = Account.AccountType.valueOf(typeOfAccount);
		accounts.put(name,ledger.generateAccount(name, accountType));
		return retrieveAccount(ledger,name);
    }
	
	def retrieve(Ledger ledger,String name) {
		return accounts.get(name);
	}
	
	def delete(Ledger aSystem,Account account) {
		aSystem.remove(account);
		accounts.remove(account);
	}
	
	def list(Ledger ledger) {
		return accounts.values();
	}
	
	
	private final Map<String,Account> accounts = new HashMap<String,Account>();
}
