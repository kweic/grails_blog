package kw_blog;

import kw_blog.com.manifestcorp.Role;
import kw_blog.com.manifestcorp.User;
import kw_blog.com.manifestcorp.UserRole;

class BootStrap {
    //def springSecurityService

    def init = { servletContext ->

//        def userRole = new Role(authority: 'ROLE_USER').save()
//
//        def me = new User(username: 'user', password: 'password').save()
//
//        UserRole.create me, userRole
//
//        UserRole.withSession {
//            it.flush()
//            it.clear()
//        }

    }

    def destroy = {
    }
}
