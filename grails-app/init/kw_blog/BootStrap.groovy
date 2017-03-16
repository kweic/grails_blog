
import kw_blog.com.manifestcorp.*;

class BootStrap {
    def springSecurityService

    def init = {
        //servletContext ->
        def userRole = new Role('ROLE_ADMIN').save()

        def me = new User('user', 'password').save()


        UserRole.create me, userRole

        User.withSession {
            it.flush()
        }
    }
    def destroy = {
    }
}
