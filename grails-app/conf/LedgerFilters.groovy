class LedgerFilters {
   def filters = {
	   loginCheck(controller: '*') {
           before = {
            if (controllerName != null && !controllerName.equals('ledger') && !actionName.equals('select') && !session.Ledger) {			  
                  redirect(controller: 'ledger', action:'select')
                  return false
               }
           }
       }
   }
}