package kw_blog

import kw_blog.com.manifestcorp.Blog
import grails.plugin.springsecurity.annotation.Secured

class BlogController {
    static scaffold = Blog

    @Secured('ROLE_USER')
    def index() {
        def criteria = Blog.createCriteria()

        def blogs = criteria.list(params) {
            if (params.query) {
                ilike("title", "%%")
            }
        }
        println("##### blogs size: "+blogs.size())
        render(view: "index", model: [blog: blogs])
    }

    @Secured('ROLE_USER')
    def create(){
        render(view: "create", model: [blog: new Blog()])
    }

    @Secured('ROLE_USER')
    def save(Blog blog){
        blog.save();
        println "##### blog save "+blog.title;
        index();
    }

    @Secured('ROLE_USER')
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
