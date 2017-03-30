package kw_blog

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {

            }
        }

        name blogLink: "/blog/show/$id/$title?" {
            controller = "blog"
            action = "show"
        }


        "/"(controller:"User")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/comment"(controller:'Blog')
        "/login"(view:'/login')
        "/comment/index"(controller:'Blog')
    }
}
