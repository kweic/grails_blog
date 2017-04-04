package kw_blog

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import kw_blog.com.manifestcorp.User
import kw_blog.com.manifestcorp.Role
import kw_blog.com.manifestcorp.UserRole
import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.Pagination


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
        println "user index called"
        println "user index params: "+params
        println "sort type: "+params.sort
        params.max = Math.min(max ?: 10, 100)
        def users = findUsersWithPagination()

        println "in index, users being returned: "+users.size()

        respond User.list(params), model: [usersFound: users, userCount: User.count(), query: query, filterParams: params]
    }

    def usersTemplate(){
        println "in user template"
        def users = findUsersWithPagination()

        println "in userTemplate, users being returned: "+users.size()

        render template:"user_results", model: [usersFound: users, userCount: User.count(), query: query, filterParams: params]
    }

    def matchingUserSize(query){
        return User.findAllByUsernameLike('%'+query+'%').size()
    }

    def getUsers(){
        def criteria = User.createCriteria()
            def users = criteria.list(params) {
                if (params.query) {
                    ilike("username", "%${params.query}%")
                }
            }
        Collections.sort(users);

        return users
    }

    def findUsersWithPagination(){
        println "find with page called, max: "+params.max+" offset: "+params.offset+" query: "+params.query
        def query = params.query;
        if(query == null){
            query = "";
        }

        return User.findAllByUsernameLike("%"+query+"%", [max: params.max, offset: params.offset])
    }

    def test(){
        println "test method called"
    }

    def sort(){
        println "sort called"

        params.max = 10
        params.offset = 0
        println "SORT doing sort: "+params.sort
        println "SORT with query: "+params.query

        println "SORT, params: "+params

        def users = findUsersWithPagination()
        def foundSize = matchingUserSize(params.query)
        println "users page found size: "+users.size()
        println "found: "+foundSize
        flash.message = "Found "+foundSize+" results."


        render template:"user_results", model: [usersFound: users, userCount: foundSize,query: query, filterParams: params]
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
        println "search called"
        params.max = 10;
        params.offset = 0;
        def users = findUsersWithPagination()
        def resultSize = matchingUserSize(params.query)
        flash.message = "Found "+resultSize+" results."

        render view: "index", model: [usersFound: users, userCount: resultSize, query: params.query, filterParams: params]
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