package kw_blog

import kw_blog.com.manifestcorp.Blog
import grails.plugin.springsecurity.annotation.Secured

class BlogController {
    static scaffold = Blog

    @Secured('ROLE_USER')
    def index() {
        def blogs = getBlogs()

        println "### returning index, blog count: "+Blog.count()
        respond Blog.list(params), model: [blog: blogs, blogCount: Blog.count()] //this is the working one on larry's
        //respond model: [blog: blogs, blogCount: Blog.count()]
        //render(view: "index", model: [blog: blogs, blogCount: Blog.count()])
    }

    @Secured('ROLE_USER')
    def create(){
        render(view: "create", model: [blog: new Blog()])
        //respond view: "create", model: [blog: new Blog()]
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
    def show(Blog blog){
        respond blog
    }

    @Secured('ROLE_USER')
    def save(Blog blog){
        blog.save();
        def note = "New post added."

        flash.message = "New post added."
        renderView("index");
    }

    @Secured('ROLE_USER')
    def search() {
      //  [blogInstanceList: blogs, blogInstanceTotal: blogs.totalCount]

        renderView("search")
    }

    private renderView(String view){
        def blogs = getBlogs();
        render(view: view, model: [blog: blogs])
    }

}
