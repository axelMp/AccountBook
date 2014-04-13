package accountbook

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import org.book.account.domain.AccountSystem

@Transactional(readOnly = true)
class BookController {
	def bookService;
	
    def index() { 
	    if ( null == session["accountSystem"] ) {
			def books = bookService.list();
			if ( books.isEmpty() ) {
				redirect(action:"create");
			}
			respond books, model:[books: books];
		} else {
			redirect(url: "/account");
		}
	}
	
	def create() {
	    if (params.name) {
			bookService.create(params.name);
			redirect(action: "index");
		}
	}
	
	def delete(AccountSystem anAccountSystem) {
		bookService.delete(anAccountSystem);
		redirect(action: "index");
	}
	
	def select() {
		if (params.name) {
			session["accountSystem"]=params.name;
		}
		
		redirect(action: "index");
	}
}
