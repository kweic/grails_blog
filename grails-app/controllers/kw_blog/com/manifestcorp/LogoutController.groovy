package kw_blog.com.manifestcorp

import org.springframework.security.access.annotation.Secured
import org.springframework.security.web.RedirectStrategy

import javax.servlet.http.HttpServletResponse

@Secured('permitAll')
class LogoutController {

    /** Dependency injection for RedirectStrategy. */
    RedirectStrategy redirectStrategy

    def index() {
        println "logout index in logoutcontroller called"

        if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
            response.sendError HttpServletResponse.SC_METHOD_NOT_ALLOWED // 405
            return
        }

        // TODO put any pre-logout code here
        redirectStrategy.sendRedirect request, response, SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/logoff'
        response.flushBuffer()

    }
}
