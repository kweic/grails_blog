package kw_blog

class BlogController {
    static scaffold = Blog
    def index() {

    }

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
//        def blogs = Blog.findAllByNameLike("${params.value}%") render(template: 'results', model: [value: params.value, blogs: blogs])
//    }
}
