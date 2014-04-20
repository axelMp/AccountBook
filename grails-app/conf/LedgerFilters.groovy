class LedgerFilters {
   def filters = {
        ledgerCheck(controller: '*', action: '*') {
           before = {
              if (!controllerName.equals('ledger') &&!session.getAttribute('Ledger')) {
                  redirect(url: "/ledger/")
                  return true
               }
           }
       }
   }
}