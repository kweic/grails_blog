package manifestcorp

import kw_blog.com.manifestcorp.Blog

class BlogController {
    static scaffold = Blog
//    def index() {
//
//    }

//    def show() {
//        def blog = Blog.get(params.id)
//        log.error("{}", blog)
//        [blogInstance : blog]
//    }
//    def list() {
//        params.max = Math.min(params.max ? params.int('max') : 5, 100)
//
//        def blogList = Blog.createCriteria().list (params) {
//            if ( params.query ) {
//                ilike("description", "%${params.query}%")
//            }
//        }
//
//        [blogInstanceList: blogList, blogInstanceTotal: blogList.totalCount]
//    }
//
//    def remoteSearch = {
//        print "remote search clicked"
//        def blogs = Blog.findAllByNameLike("${params.value}%") render(template: 'results', model: [value: params.value, blogs: blogs])
//    }
    def search = {
        print "search clicked"
        def blogs = Blog.findAllByTitleLike("${params.value}%") render(view: 'search', model: [value: params.value, blogs: blogs])
    }


    def search() {
        //  params.max = Math.min(params.max ? params.int('max') : 5, 100)

        //  print "in list"

        def criteria = Blog.createCriteria()

        def blogList = criteria.list(params) {
            if (params.query) {
                ilike("title", "%${params.query}%")
            }
        }
        println "##### list: "+blogList
        println "##### list size: "+blogList.size()

        [blogInstanceList: blogList, blogInstanceTotal: blogList.totalCount]
//        [blogInstanceIndex: blogList, blogInstanceTotal: blogList.totalCount]
    }

}
