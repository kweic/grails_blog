package kw_blog

import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.Comment
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional;
import static org.springframework.http.HttpStatus.*


class BlogController {
    static scaffold = Blog
    def query = ""


    @Secured('ROLE_USER')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def blogs = getBlogs()
        respond Blog.list(params), model: [blogsFound: blogs, blogCount: Blog.count(), query: query, filterParams: params]
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

        println "blog saved"

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
        Blog blog = (Blog)Blog.findByIdLike(Integer.parseInt(params.blogId), params)

        Comment comment = new Comment();
        comment.blog = blog;
        comment.comment = params.comment;
        comment.user = params.user;
        comment.dateCreated = new Date()

        blog.comments.add(comment);
        blog.save flush:true

        render(template:'results', model:[comments: blog.comments])
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
        Integer max
        params.max = Math.min(max ?: 10, 100)
        def blogs = Blog.findAllByTitleLike("%${params.query}%", [max: params.max, offset: params.offset])
        flash.message = "Found "+blogs.size+" results."
        query = params.query
        //return index(10);
        //render view:"index", model: [value: params.value, blogList: blogs, blogCount: Blog.count()]

        render view: "index", model: [blogsFound: blogs, blogCount: Blog.count(), query: query, filterParams: params]
    }


}
