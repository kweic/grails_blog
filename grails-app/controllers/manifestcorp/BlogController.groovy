package manifestcorp

import kw_blog.com.manifestcorp.Blog

class BlogController {
    static scaffold = Blog

    def index() {

    }


    def search() {
        //  params.max = Math.min(params.max ? params.int('max') : 5, 100)

        def criteria = Blog.createCriteria()

        def blogs = criteria.list(params) {
            if (params.query) {
                ilike("title", "%${params.query}%")
            }
        }

        [blogInstanceList: blogs, blogInstanceTotal: blogs.totalCount]

        render(view: "search", model: [blog: blogs])

    }

}
