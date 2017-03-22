package kw_blog.com.manifestcorp

import kw_blog.com.manifestcorp.Blog;

class Comment {
    String user;
    String comment;
    Date dateCreated;

    static belongsTo = [blog: Blog]

    static mapping = {
        blog lazy: false
    }

    static constraints = {
        user(blank: false)
        comment(maxSize: 1000, widget: 'textarea', nullable: true)
    }
}
