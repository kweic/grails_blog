package kw_blog.com.manifestcorp

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CommentController {
    static scaffold = Comment
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static{
        println "comment controller created"
    }

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
        println "save in comment controller called"
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

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*' { respond comment, [status: CREATED] }
        }
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

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
