package kw_blog

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {

            }
        }

        name blogLink: "/blog/show/$id/$date/$title?" {
//            controller = "Show"
//            action = "show"
            controller = "blog"
            action = "show"
        }

        "/"(controller:"Blog")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
