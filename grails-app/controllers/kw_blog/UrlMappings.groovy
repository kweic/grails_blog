package kw_blog

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {

            }
        }

        "/"(controller:"Blog")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
