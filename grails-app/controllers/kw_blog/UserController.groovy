package kw_blog

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import kw_blog.com.manifestcorp.User
import kw_blog.com.manifestcorp.Role
import kw_blog.com.manifestcorp.UserRole
import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.Pagination;


@Transactional(readOnly = true)
@Secured("permitAll")
class UserController {
    def springSecurityService
    User currentUser;
    def query = ""
    Pagination paginator = new Pagination();
    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    @Secured("permitAll")
    index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def users = getUsers()

        respond User.list(params), model: [usersFound: users, userCount: User.count(), query: query, filterParams: params]
    }

    def matchingUserSize(query){
        println "searching for: "+query
        return User.findAllByUsernameLike(query).size()
    }

    def getUsers(){
        def criteria = User.createCriteria()
            def users = criteria.list(params) {
                if (params.query) {
                    ilike("username", "%${params.query}%")
                }
            }

        return users
    }

    def blogs(User user){
        if(user == null){
            user = User.findById(springSecurityService.principal.id)
        }
        def blogs = getBlogs(query, user)
        blogs = paginator.paginateResults(blogs, params)

        respond user, model:[blogsFound: blogs, id: user.id, blogCount: user.blogs.size(), query: query, filterParams: params]
    }

    def getBlogs(query, user){

        def blogs = user.blogs
        def blogsFiltered = []
        for(Blog blog:blogs){
            if(blog.title.contains(query)){
                blogsFiltered.add(blog)
            }
        }

        blogsFiltered
    }

    def getUser(){
        return springSecurityService.principal;
    }

    @Secured("permitAll")
    def show(User userInstance) {
        respond userInstance
    }

    def create() {
        render(view: "create", model: [user: new User()])
    }

    @Secured('permitAll')
    search() {
        def users = User.findAllByUsernameLike("%${params.query}%", [max: params.max, offset: params.offset])
        flash.message = "Found "+users.size+" results."

        render view: "index", model: [usersFound: users, userCount: matchingUserSize(params.query), query: params.query, filterParams: params]
    }

    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view: 'create', model: [error: "Please choose a different name."]
            return
        }

        userInstance.save flush: true
        saveNewUserWithRole(userInstance)

        println "saving new user: "+userInstance.username
        println "user id: "+userInstance.id
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.usercreated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
                redirect(controller: "user", action: "index")
            }
            '*' { respond userInstance, [status: CREATED] }
        }
    }

    private void saveNewUserWithRole(User user){
        def ROLE_USER = Role.findByAuthority("ROLE_USER");

        if(ROLE_USER == null){
            ROLE_USER = new Role(authority: 'ROLE_USER').save()
        }

        UserRole.create user, ROLE_USER

        UserRole.withSession {
            it.flush()
            it.clear()
        }
    }

    def edit(User userInstance) {
//        respond userInstance
    }

    @Transactional
    def update(User userInstance) {
//        if (userInstance == null) {
//            notFound()
//            return
//        }
//
//        if (userInstance.hasErrors()) {
//            respond userInstance.errors, view: 'edit'
//            return
//        }
//
//        userInstance.save flush: true
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
//                redirect userInstance
//            }
//            '*' { respond userInstance, [status: OK] }
//        }
    }

    @Transactional
    def delete(User userInstance) {

//        if (userInstance == null) {
//            notFound()
//            return
//        }
//
//        userInstance.delete flush: true
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
//                redirect action: "index", method: "GET"
//            }
//            '*' { render status: NO_CONTENT }
//        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}