package kw_blog

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import kw_blog.com.manifestcorp.User
import kw_blog.com.manifestcorp.Role
import kw_blog.com.manifestcorp.UserRole
import kw_blog.com.manifestcorp.Blog

@Transactional(readOnly = true)
@Secured("permitAll")
class UserController {
    def springSecurityService
    User currentUser;
    def query = ""
    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

//    @Secured("permitAll")
//    def index(Integer max) {
//        println "user, index, list size: "+User.count()
//        params.max = Math.min(max ?: 10, 100)
//        respond User.list(params), model: [users: User.list(), userCount: User.count(), filterParams: params]
//    }
    @Secured("permitAll")
    index(Integer max) {
        println "visiting index from user controller"
        params.max = Math.min(max ?: 10, 100)
        def users = getUsers()
        respond User.list(params), model: [usersFound: users, userCount: User.count(), query: query, filterParams: params]
    }

    def getUsers(){
        def criteria = User.createCriteria()
        //println "user count: "+User.count()

            def users = criteria.list(params) {
                if (params.query) {
                    ilike("username", "%${params.query}%")
                }
            }

        return users
    }

    def blogs(User user){
        //params.max = Math.min(max ?: 10, 100)
        if(user == null){
            user = User.findById(springSecurityService.principal.id)
        }
        respond user, model:[blogsFound: user.blogs, id: user.id]
    }

//    def getBlogs(){
//        println "user parts: "+getUser()
//        def criteria = getUser().blogs.createCriteria()
//
//        def blogs = criteria.list(params) {
//            if (params.query) {
//                ilike("title", "%${params.query}%")
//            }
//        }
//        blogs
//    }

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

        render view: "index", model: [usersFound: users, userCount: User.count(), query: params.query, filterParams: params]
    }

    @Transactional
    def save(User userInstance) {
        println "in user save"
        if (userInstance == null) {
            notFound()
            println "userinstance is null"
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view: 'create', model: [error: "Please choose a different name."]
            return
        }

        userInstance.save flush: true
        saveNewUserWithRole(userInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
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