package kw_blog.com.manifestcorp

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured('ROLE_USER')
class CommentController {
    static scaffold = Comment
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Comment.list(params), model:[commentCount: Comment.count()]
    }

    @Secured('ROLE_USER')
    def show(Comment comment) {
        respond comment
    }

    def create() {
        respond new Comment(params)
    }


    @Secured('ROLE_USER')
    @Transactional
    def save(Comment comment) {
        if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'create'
            return
        }

        comment.save flush:true



        redirect controller: "blog", action: "show", id: comment.blog.id
    }

    def edit(Comment comment) {
        respond comment
    }

    @Transactional
    def update(Comment comment) {
        if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'edit'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*'{ respond comment, [status: OK] }
        }
    }

    @Transactional
    def delete(Comment comment) {

        if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        comment.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def test(){
        println "test called in comment controller"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def _results(){

        println "in results in comment controller"
        render template "results"
    }

    def submitComment() {
        println "remoteSearch clicked"
        println "params value: "+params.comment
        println "params id: "+params.blogId


//        String htmlContent = g.render([template: "results", model: [value: params.value, comments: comments]])
//
//        Map responseData = [htmlContent: htmlContent]
//
//        render(responseData as JSON)

        def comments = Comment.findAllByRefIdLike("${params.blogId}%")
        println "remote search comments size: "+comments.size;
        //render(template:'results', model: [value: params.value, comments: comments])
//        render view: "index"

        def model = comments.collect { [(it.user): it.comment] }
        Comment comment = new Comment(params);
//        comment.comment = params.comment;
//        comment.refId = params.blogId;
//        comment.user = params.user;

        save(comment);
        render template: "results", model as JSON
    }

    //        def comments = Comment.findAllByRefIdLike("${params.blogId}%")
//        println "remote search comments size: "+comments.size;
//        render(template:'results', model: [value: params.value, comments: comments])

}
