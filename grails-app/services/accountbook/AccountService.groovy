package accountbook

import grails.transaction.Transactional

import org.book.account.domain.Account;
import org.book.account.domain.PhysicalAccount;
import org.book.account.domain.AccountSystem;

@Transactional
class AccountService {

	def createAccount(AccountSystem book, String name,String typeOfAccount) {
		Account.AccountType accountType = Account.AccountType.valueOf(typeOfAccount);
		accounts.put(name,book.generateAccount(name, accountType));
		return retrieveAccount(book,name);
    }
	
	def createPhysicalAccount(AccountSystem book, String name) {
		physicalAccounts.put(name,book.generatePhysicalAccount(name));
		return retrievePhysicalAccount(book,name);    
	}
	
	
	def retrieveAccount(AccountSystem book,String name) {
		return accounts.get(name);
	}
	
	def retrievePhysicalAccount(AccountSystem book,String name) {
		return physicalAccounts.get(name);
	}
	
	
	def delete(AccountSystem aSystem,Account account) {
		aSystem.remove(account);
		accounts.remove(account);
	}
	
	def delete(AccountSystem aSystem,PhysicalAccount account) {
		aSystem.remove(account);
		physicalAccounts.remove(account);
	}
	
	def listAccounts(AccountSystem book) {
		return accounts.values();
	}
	
	def listPhysicalAccounts(AccountSystem book) {
		return physicalAccounts.values();
	}
	
	private final Map<String,Account> accounts = new HashMap<String,Account>();
	private final Map<String,PhysicalAccount> physicalAccounts = new HashMap<String,PhysicalAccount>();
}
