package kw_blog

import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.Comment
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import kw_blog.com.manifestcorp.User


class BlogController {
    def springSecurityService

    def query = ""



    //@Secured('ROLE_USER')
    @Secured("permitAll")
    index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def blogs = getBlogs()
        respond Blog.list(params), model: [blogsFound: blogs, blogCount: Blog.count(), query: query, filterParams: params, user: getUser()]
    }

    def getUser(){
        return springSecurityService.principal.username
    }

    @Secured('ROLE_USER')
    create(){
        render(view: "create", model: [blog: new Blog(), user: getUser()])
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

    @Secured('permitAll')
    show(Blog blog){
        respond blog, model: [comment: new Comment(), user: getUser()]
    }

    @Secured('ROLE_USER')
    @Transactional
    save(Blog blog) {
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
            '*' { respond blog, [status: CREATED] }
        }

    }


    @Secured('ROLE_USER')
    userComments() {
        Blog blog = (Blog) Blog.findByIdLike(Integer.parseInt(params.blogId), params)

        if (blog.comments.size() == 0 ||
                (!params.user.isEmpty() &&
                        !params.comment.trim().isEmpty() &&
                blog.comments.last().comment != params.comment)) {

            Comment comment = new Comment()
            comment.blog = blog
            comment.comment = params.comment
            comment.user = params.user
            comment.dateCreated = new Date()

            blog.comments.add(comment)
            blog.save flush: true
        }

        render(template:'results', model:[comments: blog.comments, user: getUser()])
    }


    @Secured('ROLE_USER')
    @Transactional
    delete(Blog blog){
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

    @Secured("permitAll")
    createUser(){
        def userName = params.user
        println "count of users: "
        println User.size()
        if (User.findByUsernameLike(userName) == null){
            println "user does not exist"
        }else{
            println "user exists"
        }

    }

    @Secured('ROLE_USER')
    edit(Blog blog){
        respond blog,  model:[user: getUser()]
    }

    @Secured('ROLE_USER')
    @Transactional
    update(Blog blog) {
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

    @Secured('permitAll')
    search() {
        def blogs = Blog.findAllByTitleLike("%${params.query}%", [max: params.max, offset: params.offset])
        flash.message = "Found "+blogs.size+" results."

        render view: "index", model: [blogsFound: blogs, blogCount: Blog.count(), query: params.query, filterParams: params, user: getUser()]
    }


}
