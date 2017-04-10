package kw_blog

import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.Comment
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import kw_blog.com.manifestcorp.User
import kw_blog.com.manifestcorp.Pagination;


class BlogController {
    def springSecurityService
    def query = ""
    Pagination paginator = new Pagination();


    @Secured("permitAll")
    index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def blogs = getBlogs()
        respond Blog.list(params), model: [blogsFound: blogs, blogCount: Blog.count(), query: query, filterParams: params]
    }

    def getUsername(){
        return springSecurityService.principal.username;
    }

    def getLoggedInUser() {
        println "about to try to find: "+springSecurityService.principal.id
        return User.findById(springSecurityService.principal.id, params)
    }

    @Secured('ROLE_USER')
    create(){
        render(view: "create", model: [blog: new Blog()])
    }

    def getBlogs(){
        def criteria = Blog.createCriteria()

        print "criteria: "+criteria

        def blogs = criteria.list(params) {
            if (params.query) {
                ilike("title", "%${params.query}%")
            }
        }
        blogs
    }

    @Secured('permitAll')
    show(Blog blog){
        println "show called, blog: "+blog
        if(springSecurityService != null && springSecurityService.principal.enabled) {
            println "part 1"
            respond blog, model: [comment: new Comment(), userId: springSecurityService.principal.id]
        }else{
            println "part 2"
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
            blog.user = getLoggedInUser()
        }

        getLoggedInUser().updateLastActive();

        blog.validate()

        if (blog.hasErrors()) {
            println "blog has errors"
            blog.errors.allErrors.each {
                println it
            }
            transactionStatus.setRollbackOnly()
            respond blog.errors, view:'create'
            return
        }

        blog.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message')

                redirect blog
                }
            '*' { respond blog, [status: CREATED] }
        }
    }

    def isDuplicateComment(comment, blog){
        return blog.comments.size() > 0 && blog.comments.first().comment == comment.trim()
    }

    def validCommentForBlog(comment, blog){
        if(comment.trim().isEmpty() || isDuplicateComment(comment, blog) || getLoggedInUser() == null)return false
        return true;
    }

    @Secured('ROLE_USER')
    userComments() {
        Blog blog = (Blog) Blog.findById(Integer.parseInt(params.blogId), params)

        if (validCommentForBlog(params.comment, blog)) {

            Comment comment = new Comment()
            comment.blog = blog
            comment.comment = params.comment.trim()
            comment.user = getLoggedInUser().username
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

        blog.dateUpdated = new Date()

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
        def user = findUserById(params.id)

        def blogs
        if(params.id != null){
            blogs = Blog.findAllByUserAndTitleLike(user, "%${params.query}%")
        }else {
            blogs = Blog.findAllByTitleLike("%${params.query}%")
        }
        flash.message = "Found "+blogs.size+" results."

        def resultSize = blogs.size
        blogs = paginator.paginateResults(blogs, params)



        render view: "/user/blogs", model: [user: user, id: params.id, blogsFound: blogs, blogCount: resultSize, query: params.query, filterParams: params]
    }

    def findUserById(id){
        println "search test, search id is: "+id
        return User.findById(id)
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
