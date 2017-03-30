package com.manifestcorp.kw_blog

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.*
import spock.lang.*
import kw_blog.com.manifestcorp.*
import spock.lang.Specification
import kw_blog.*

@TestFor(BlogController)
@Mock(Blog)
class BlogControllerSpec extends Specification {


    def populateValidParams(params) {
        assert params != null
        params.title = "hey"
        params.postBy = "kevin"
        params.user = new User()
    }

    def injectTemporaryUser(username){
        def springSecurityService = Stub(SpringSecurityService)
        User currUser = new User(username: username, password: "password")
        currUser.id = 1
        springSecurityService.principal >> currUser

        controller.user = currUser;

        controller.springSecurityService = springSecurityService
    }

    void "Test the index action returns the correct model"() {
        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.blogList
            model.blogCount == 0
    }

    void "Test the create action returns the correct model"() {

        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.blog!= null
    }

    void "Test the save action correctly persists an instance"() {

        given: "My mock user is in place"
        injectTemporaryUser("kevin")

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            request.format = 'form'
            def blog = new Blog()
            blog.validate()
            controller.save(blog)

        then:"The create view is rendered again with the correct model"
            model.blog!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            blog = new Blog(params)

            controller.save(blog)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/blog/show/1'
            controller.flash.message != null
            Blog.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def blog = new Blog(params)
            controller.show(blog)

        then:"A model is populated containing the domain instance"
            model.blog == blog
    }

    void "Test that the edit action returns the correct model"() {

        given: "My mock user is in place"
            injectTemporaryUser("kevin")

        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def blog = new Blog(params)
            controller.edit(blog)

        then:"A model is populated containing the domain instance"
            model.blog == blog
    }

    void "Test the update action performs an update on a valid domain instance"() {
        given: "My mock user is in place"
        injectTemporaryUser("kevin")

        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            request.format = 'form'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/blog/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def blog = new Blog()
            blog.validate()
            controller.update(blog)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.blog == blog

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            blog = new Blog(params).save(flush: true)
            controller.update(blog)

        then:"A redirect is issued to the show action"
            blog != null
            response.redirectedUrl == "/blog/show/$blog.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        given: "My mock user is in place"
        injectTemporaryUser("kevin")

        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            request.format = 'form'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/blog/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def blog = new Blog(params).save(flush: true)

        then:"It exists"
            Blog.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(blog)

        then:"The instance is deleted"
            Blog.count() == 0
            response.redirectedUrl == '/blog/index'
            flash.message != null
    }

    void "Test search returns matching result"(){
        when: "A search is made"
            makePost("post1", "kevin")
            makePost("post2", "kevin")
            makePost("post3", "kevin")
            params.query = "post"
            controller.search()

        then: "Matching results are returned"
            view == '/blog/index'
            model.blogsFound.size == 3

        when: "A specific search is made"
            params.query = "post3"
            controller.search()
        then: "The exact match is returned"
            view == '/blog/index'
            model.blogsFound.size == 1

        when: "A blank search is made"
            params.query = ""
            controller.search()
        then: "All posts are returned"
            model.blogsFound.size == 3

        when: "A search matching nothing is made"
            params.query = "noMatchForThis"
            controller.search()
        then: "nothing is returned"
            model.blogsFound.isEmpty() == true
    }

    void "Test submission of comments on a blog post"(){
        when:"A comment is submitted"
            makePost("post", "bob")
            makeComment("user", "a comment")

        then:"The comment is saved"
            def savedBlog = Blog.findByIdLike("1")
            savedBlog != null
            savedBlog.comments.first().comment == "a comment"

        when:"A comment is a duplicate of the comment preceding it"
            makeComment("user", "a comment to duplicate")
            makeComment("user", "a comment to duplicate")

        then:"The comment is not saved"
            savedBlog.comments.size() == 2

        when:"A comment is submitted without a username"
            makeComment("", "a comment without a user")
        then:"The comment is not saved"
            savedBlog.comments.size() == 2

        when: "A comment is submitted with no comment"
            makeComment("Bob", "")
        then: "The comment is not saved"
            savedBlog.comments.size() == 2

        when: "A comment is submitted that is only whitespace"
            makeComment("Ted", "     ")
        then: "The comment is not saved"
            savedBlog.comments.size() == 2

        when: "A comment with leading and or trailing newlines is submitted"
            makeComment("user", "\n\n\n\n\nhey\nthere\nwords\nOne space\n")
        then: "The leading and or trailing newlines are removed"
            savedBlog.comments.first().comment == "hey\nthere\nwords\nOne space"
    }

    void "Test edit and delete of post by poster and non poster"(){

        given: "My mock user is in place"
            injectTemporaryUser("differentUser")

        when: "A user tries to delete a blog he didn't post"
            def blog = makePost("title", "kevin")
            println "is blog null after creating: "+(blog == null)
            controller.delete(blog)
        then: "The action is denied"
            Blog.count() == 1


        when: "A user tries to edit a blog he didn't post"
            injectTemporaryUser("different")
            params.title = "changed text"
            println "is blog null "+ (blog == null)
            controller.edit(blog)
        then: "The action is denied"
            Blog.count() == 1
            String found = Blog.findAllById(1).title
            removeBrackets(found) == "title"
    }

    def removeBrackets(bracketedString){
        return bracketedString.substring(1, bracketedString.length()-1)
    }

//    void "Test creating a new user"(){
//        when: "A new user is created"
//            params.user = "Kevin"
//            controller.createUser()
//        then: "That new user exists"
//            User.count() == 1;
//
//        when: "A username is already taken"
//        then: "The new username is not saved"
//    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize()
    }

    def makePost(title, user){
        populateValidParams(params)
        def blog = new Blog(params).save(flush: true)
        println "is blog null? "+(blog==null)
        blog.comments = new TreeSet()
        blog.title = title
        blog.postBy = user
        blog.save(flush: true)
        return blog
    }

    void makeComment(username, comment){
        params.blogId = "1"
        params.comment = comment
        params.user = username

        controller.userComments()
    }
}
