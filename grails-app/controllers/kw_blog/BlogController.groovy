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


    @Secured("permitAll")
    index(Integer max) {
        println "index of blog controller"
        params.max = Math.min(max ?: 10, 100)

        def blogs = getBlogs()
        respond Blog.list(params), model: [blogsFound: blogs, blogCount: Blog.count(), query: query, filterParams: params]
    }

    def getUsername(){
        return springSecurityService.principal.username;
    }

    def getUser() {
        println "getting user"

        return (User) User.findByIdLike(springSecurityService.principal.id, params)
    }

    @Secured('ROLE_USER')
    create(){
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

    @Secured('permitAll')
    show(Blog blog){
        if(springSecurityService != null && springSecurityService.principal.enabled) {
            respond blog, model: [comment: new Comment(), userId: springSecurityService.principal.id]
        }else{
            respond blog, model: [comment: new Comment()]
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    save(Blog blog) {
        if (blog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (blog.user == null) {
            println "blog user null, getting user from currently logged in"
            blog.user = getUser()
        }

        blog.validate()

        if (blog.hasErrors()) {
            println "blog has errors"
            blog.errors.allErrors.each {
                println it
            }
            println blog
            transactionStatus.setRollbackOnly()
            respond blog.errors, view:'create'
            return
        }

        blog.save flush: true

        println "about to do redirect"
        println "blog id: "+blog.id
        println "blog title"+blog.title
        println "blog entry"+blog.blogEntry
        println "blog postBy "+blog.postBy

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
                blog.comments.first().comment != params.comment)) {

            Comment comment = new Comment()
            comment.blog = blog
            comment.comment = params.comment.trim()
            comment.user = params.user
            comment.dateCreated = new Date()

            blog.comments.add(comment)
            blog.save flush: true
        }
        render(template:'results', model:[comments: blog.comments])
    }


    @Secured('ROLE_USER')
    @Transactional
    delete(Blog blog) {
        if (blog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userIsPoster(blog)) {
            blog.delete(flush: true)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'blog.label', default: 'Blog'), blog.id])
                redirect action:"index", method:"GET"
                }
            '*'{ render status: NO_CONTENT }
            }
    }

    def userIsPoster(blog){
        if(blog != null){
            return blog.postBy == getUsername()
        }
        return false
    }


    @Secured('ROLE_USER')
    edit(Blog blog){
        if (userIsPoster(blog) || blog == null) {
            respond blog
        }else{
            ""
        }
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

        if (userIsPoster(blog)) {
            blog.save flush: true
        }

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
        println "search called, id of: "+params.id
        println "looking for: "+params.query
        def blogs
        if(params.id != null){
            blogs = Blog.findAllByUserAndTitleLike(User.findById(params.id), "%${params.query}%", [max: params.max, offset: params.offset])
        }else {
            println "id null, finding all"
            blogs = Blog.findAllByTitleLike("%${params.query}%", [max: params.max, offset: params.offset])
        }
        flash.message = "Found "+blogs.size+" results."

        println "found blogs, size: "+blogs.size

        render view: "/user/blogs", model: [id: params.id, blogsFound: blogs, blogCount: blogs.size, query: params.query, filterParams: params]
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'blog.label', default: 'Blog'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }


}
