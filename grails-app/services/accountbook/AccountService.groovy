package accountbook

import grails.transaction.Transactional

import org.book.account.domain.*;

class AccountService {

	def create(ILedger ledger, String name,String typeOfAccount) {
		AccountType accountType = AccountType.valueOf(typeOfAccount);
		def account = ledger.createAccount(name, accountType);
		ledger.save();
		return account;
    }
	
	def retrieve(ILedger ledger,String name) {
		for (IAccount account: ledger.getAccounts()) {
			if(account.getName().equals(name)) {
				return account;
			}
		}
		throw new IllegalArgumentException("no account with name "+name+" configured for ledger \""+ledger.getName()+"\"");
	}
	
	def list(ILedger ledger) {
		return ledger.getAccounts();
	}
}
