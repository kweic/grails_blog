package kw_blog.com.manifestcorp

import kw_blog.com.manifestcorp.Blog

class Comment implements Comparable<Comment> {
    String user
    String comment
    Date dateCreated
    Blog blog

    static belongsTo = [blog: Blog]

    static mapping = {
        blog lazy: false
    }

    static constraints = {
        user(blank: false)
        comment(maxSize: 1000, widget: 'textarea', nullable: true)

    }

    @Override
    int compareTo(Comment o) {
        return o.dateCreated.compareTo(dateCreated)
    }
}
