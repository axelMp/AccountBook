package accountbook

import org.book.account.domain.AccountSystem;

import grails.transaction.Transactional

@Transactional
class BookService {

    def create(String name) {
	    if ( currentBooks.containsKey(name)) {
			throw new IllegalArgumentException("Book with name "+name+" already exist");
		}
		currentBooks.put(name,new AccountSystem(name));
		return retrieve(name);
    }
	
	def retrieve(String name) {
		return currentBooks.get(name);
	}
	
	def delete(AccountSystem aSystem) {
		// nothing to do until hibernate
	}
	
	def list() {
		return currentBooks.values();
	}
	
	private final Map<String,AccountSystem> currentBooks = new HashMap<String,AccountSystem>();
}
