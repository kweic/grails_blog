package kw_blog.com.manifestcorp

/**
 * Created by Manifest on 3/21/2017.
 */
class Comment {
    String user;
    String comment;
    String blogId;

    static constraints = {
        user(blank: false)
        comment(maxSize: 1000, widget: 'textarea', nullable: true)
    }
}
