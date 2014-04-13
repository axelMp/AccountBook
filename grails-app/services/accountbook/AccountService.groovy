package accountbook

import grails.transaction.Transactional

import org.book.account.domain.Account;
import org.book.account.domain.Ledger;

@Transactional
class AccountService {

	def create(Ledger ledger, String name,String typeOfAccount) {
		Account.AccountType accountType = Account.AccountType.valueOf(typeOfAccount);
		def account = ledger.createAccount(name, accountType);
		ledger.save();
		account.save(true);
		return account;
    }
	
	def retrieve(Ledger ledger,String name) {
		for (Account account: ledger.getAccounts()) {
			if(account.getName().equals(name)) {
				return account;
			}
		}
		throw new IllegalArgumentException("no account with name "+name+" configured for ledger \""+ledger.getName()+"\"");
	}
	
	def list(Ledger ledger) {
		return ledger.getAccounts();
	}
}
