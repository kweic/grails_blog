package kw_blog

import kw_blog.com.manifestcorp.Blog
import grails.plugin.springsecurity.annotation.Secured

class BlogController {
    static scaffold = Blog

    @Secured('ROLE_USER')
    def index() {
        def blogs = getBlogs()
       // render(view: "index", model: [blog: blogs, blogCount: Blog.count()])
        respond Blog.list(params), model: [blog: blogs, blogCount: Blog.count()] //this is the working one on larry's
    }

    @Secured('ROLE_USER')
    def create(){
        render(view: "create", model: [blog: new Blog()])
    }

    def getBlogs(){
        def criteria = Blog.createCriteria()

        def blogs = criteria.list(params) {
            if (params.query) {
                ilike("title", "%${params.query}%")
            }
        }
        blogs
    }

    @Secured('ROLE_USER')
    def show(Blog blog, String message) {
        render(view: "show", model: [blog: blog])
    }

    def list() {
        [blogs: Blog.list(params), blogCount: Blog.count()]
    }

    @Secured('ROLE_USER')
    def save(Blog blog){
        blog.save();
        println "##### blog save "+blog.title;
        def note = "New post added."
       // render(view: "index", model: [blog: getBlogs(), notification: note])
        flash.message = "New post added."
        render(view: "index", model: [blog: getBlogs()])
    }

    @Secured('ROLE_USER')
    def search() {

        def blogs = getBlogs()

      //  [blogInstanceList: blogs, blogInstanceTotal: blogs.totalCount]

        render(view: "search", model: [blog: blogs])

    }

}
