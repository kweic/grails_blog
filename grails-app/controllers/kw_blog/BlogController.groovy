package kw_blog

import kw_blog.com.manifestcorp.Blog
import grails.plugin.springsecurity.annotation.Secured

class BlogController {
    static scaffold = Blog
    def query = ""

    @Secured('ROLE_USER')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def blogs = getBlogs()
        respond Blog.list(params), model: [blogsFound: blogs, blogCount: Blog.count(), query: query]
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
        render(view: "show", model: [blog: blog]);
    }

    @Secured('ROLE_USER')
    def delete(Blog blog){
        blog.delete(flush: true);
        def blogs = getBlogs();
        flash.message = "Your post was deleted."
        render(view: "index", model: [blog: blogs])
    }

    @Secured('ROLE_USER')
    def edit(Blog blog){
        render(view: "edit", model: [blog: blog]);
    }

    @Secured('ROLE_USER')
    def update(Blog blog){
        blog.save(flush: true);

        flash.message = "Post updated."
        render(view: "show", model: [blog: blog]);
    }

    @Secured('ROLE_USER')
    def search() {
        def blogs = Blog.findAllByTitleLike("%${params.query}%", params)
        flash.message = "Found "+blogs.size+" results."
        query = params.query
        //return index(10);
        //render view:"index", model: [value: params.value, blogList: blogs, blogCount: Blog.count()]

        render view: "index", model: [blogsFound: blogs, blogCount: Blog.count(), query: query]
    }


}
