class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

		"/budget"(resources:'budget')
		
		"/executionPolicy"(controller:'budget',action:'listExecutionPolicies')
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
