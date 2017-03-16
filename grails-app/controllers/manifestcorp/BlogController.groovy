package manifestcorp

import kw_blog.com.manifestcorp.Blog

class BlogController {
    static scaffold = Blog
//    def index() {
//
//    }

//    def search = {
//        println "search clicked"
//       // def blogs = Blog.findAllByTitleLike("${params.value}%") render(view: 'search', model: [value: params.value, blogs: blogs])
//        println "### params.value: ${params.value}"
//
//      //  def blogs = Blog.findAllByTitleLike("${params.value}%")
//        def blogs = Blog.findAll();
//        render(view: 'search', model: [value: params.value, blogs: blogs])
//    }

    def remoteSearch = {
        println "remote search clicked"
        def blogs = Blog.findAllByTitleLike("${params.value}%")
        render(template: 'results', model: [value: params.value, blogs: blogs])
    }


    def search() {
        //  params.max = Math.min(params.max ? params.int('max') : 5, 100)

        def criteria = Blog.createCriteria()

        def blogList = criteria.list(params) {
            if (params.query) {
                ilike("title", "%${params.query}%")
            }
        }
        println "##### list: "+blogList
        println "##### list size: "+blogList.size()

        [blogInstanceList: blogList, blogInstanceTotal: blogList.totalCount]

        render(view: "search", model: [blog: blogList])

    }

}
