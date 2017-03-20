package kw_blog

import kw_blog.com.manifestcorp.Blog
import grails.plugin.springsecurity.annotation.Secured

class BlogController {
    static scaffold = Blog

    @Secured('ROLE_USER')
    def index(Integer max) {
//        def blogs = getBlogs()
//
//        println "### returning index, blog count: "+Blog.count()

        params.max = Math.min(max ?: 10, 100)
        respond Blog.list(params), model: [blogCount: Blog.count()]
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

        flash.message = "New post added."
        renderView("index");
    }

    @Secured('ROLE_USER')
    def delete(Blog blog){
        blog.delete(flush: true);
        renderView("index");
    }

    @Secured('ROLE_USER')
    def edit(Blog blog){
        render(view: "edit", model: [blog: blog]);
    }

    @Secured('ROLE_USER')
    def update(Blog blog){
        blog.save();

        flash.message = "Post updated."
        renderView("index");
    }

    @Secured('ROLE_USER')
    def search() {
        def blogs = Blog.findAllByTitleLike("%${params.query}%")

        render(view:"search", model: [value: params.value, blog: blogs])
    }

    private renderView(String view){
        def blogs = getBlogs();
        render(view: view, model: [blog: blogs])
    }

}
