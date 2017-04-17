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
    def query = ""
    Pagination paginator = new Pagination();

    @Secured("permitAll")
    index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "Last Post"
        params.order = "asc"
        def users = findUsersWithPagination(params.sort)

        //respond User.list(params), model: [usersFound: users, userCount: User.count(), query: query, filterParams: params]
        respond users, model: [usersFound: users, userCount: User.count(), query: query, filterParams: params]
    }

    def matchingUserSize(query){
        return User.findAllByUsernameLike('%'+query+'%').size()
    }

    def findUsersWithPagination(sort){
        println "finding with pagination, params: "+params
        def query = params.query;
        if(query == null){
            query = "";
        }
        def users;
        println "sort type: "+sort
        if(sort == "Total Posts"){
            int offset = 0
            if(params.offset == null){
                offset = 0;
            }else{
                if(params.offset.getClass() == java.lang.String){
                    offset = Integer.parseInt(params.offset)
                }else{
                    offset = params.offset
                }
            }
            users = User.orderByBlogCount(params.max, offset);
            for(User u: users){
                println u.username+" "+u.blogs.size()
            }
            println "got users, size: "+users.size()
            println "first user: "+users.get(0).username+" blogs: "+users.get(0).blogs.size()
        }else if(sort == "Name"){
            users = User.findAllByUsernameLike("%"+query+"%", [max: params.max, offset: params.offset, sort: "username", order: params.order])
        }else{
            users = User.findAllByUsernameLike("%"+query+"%", [max: params.max, offset: params.offset, sort: "lastActiveDate", order: flipOrder(params.order)])
        }

        return users
    }

    def flipOrder(order){
        if(order == "asc")return "desc"
        return "asc"
    }

    def sort(){
        println "in sort()"
        params.max = 10
        params.offset = 0

        def users = findUsersWithPagination(params.sort)
        def foundSize = matchingUserSize(params.query)
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

    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.lastActiveDate = new Date(1);

        if (userInstance.hasErrors()) {
            println "errors" + userInstance.errors
            respond userInstance.errors, view: 'create', model: [error: "Please choose a different name."]
            return
        }

        userInstance.save flush: true
        saveNewUserWithRole(userInstance)

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

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
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

}