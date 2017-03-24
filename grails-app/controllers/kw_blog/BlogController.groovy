package kw_blog

import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.Comment
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional;
import static org.springframework.http.HttpStatus.*


class BlogController {
    //static scaffold = Blog
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
        respond blog, model: [comment: new Comment()]
    }

    @Secured('ROLE_USER')
    @Transactional
    def save(Blog blog) {
        if (blog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (blog.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond blog.errors, view:'create'
            return
        }

        blog.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message')
                redirect blog
                }
            '*' { respond blog, [status: CREATED] } //[status: 201]
        }
    }

    @Secured('ROLE_USER')
    def userComments() {
        println "in blog controller, in userComments"
        println "id: "+params.blog.id
        def blogComments = Comment.list(); // what ever your fetch logic is
        render(template:'results', model:[comments: blogComments])
    }


    @Secured('ROLE_USER')
    @Transactional
    def delete(Blog blog){
        if (blog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        blog.delete(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'blog.label', default: 'Blog'), blog.id])
                redirect action:"index", method:"GET"
                }
            '*'{ render status: NO_CONTENT }
            }
    }

    @Secured('ROLE_USER')
    def edit(Blog blog){
        //render(view: "edit", model: [blog: blog, newComment: new Comment()]);
        respond blog
    }

    @Secured('ROLE_USER')
    @Transactional
    def update(Blog blog) {
        if (blog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (blog.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond blog.errors, view: 'edit'
            return
        }

        blog.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message')
                redirect blog
            }
            '*' { respond blog, [status: OK] }
        }
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
