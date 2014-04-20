package accountbook

import org.book.account.domain.*

class CashflowController {
	def ledgerService
	def accountService
	def transactionService
	
    def index() {
		redirect(action: "kiyosaki")	
	}
	
	def kiyosaki() {
		def ledger = ledgerService.retrieve(session["Ledger"]);
		
		// TODO: that's a CashflowService, which should be part of the domain model 
		// 1. Aggregate data
		Map<String,Amount> cashflowIncome = new HashMap<String,Amount>();
		Map<String,Amount> cashflowExpense = new HashMap<String,Amount>();
		
		for (AccountType aType: AccountType.values() ) {
			cashflowIncome.put(aType.toString(),Amount.noAmount());
			cashflowExpense.put(aType.toString(),Amount.noAmount());
		}
		
		Amount passiveIncome = Amount.noAmount();
		for( ITransaction aTransaction:ledger.getTransactions()) {
			AccountType debitorType = aTransaction.getDebitor().getAccountType();
			AccountType creditorType = aTransaction.getCreditor().getAccountType();
			Amount aValue = aTransaction.getAmount();
			if ( debitorType.equals(AccountType.INCOME) ) {
				cashflowIncome.put(creditorType.toString(),Amount.add(cashflowIncome.get(creditorType.toString()),aValue));
			}
			if ( debitorType.equals(AccountType.EXPENSE) ) {
				cashflowExpense.put(creditorType.toString(),Amount.add(cashflowExpense.get(creditorType.toString()),aValue));
			}
			if ( creditorType.equals(AccountType.INCOME) ) {
				cashflowIncome.put(debitorType.toString(),Amount.subtract(cashflowIncome.get(debitorType.toString()),aValue));
				if ( debitorType.equals(AccountType.ASSET) ) {
					passiveIncome = Amount.add(passiveIncome,aValue);
				}
			}
			if ( creditorType.equals(AccountType.EXPENSE) ) {
				cashflowExpense.put(debitorType.toString(),Amount.subtract(cashflowExpense.get(debitorType.toString()),aValue));
			}
		}
		
		render(view:"kiyosaki", model:[income: cashflowIncome,expense:cashflowExpense,passiveIncome:passiveIncome.getCents()])
	}
}
