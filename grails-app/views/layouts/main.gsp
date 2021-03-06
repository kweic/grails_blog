<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="/#">
                    <i class="my-blog-logo">
                        <asset:image src="blog_logo_invert.png" class="blog-logo"/>
                    </i>
                </a>

            </div>

                            <sec:ifLoggedIn>
                                <div id="user-name">
                                <span class="right">
                                <small>Logged in as: </small><strong>${sec.username()}</strong>
                                </span>
                                </div>
                            </sec:ifLoggedIn>



            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav navbar-right">
                    <g:pageProperty name="page.nav" />
                </ul>

            </div>

        </div>
    </div>


    <sec:ifLoggedIn>
    <div id="logout-button" class="right">
    <form name="logout" method="POST" action="${createLink(controller:'logout') }">
                <input class="logout-button btn btn-default" type="submit" value="Logout">
    </form>
    </div>
    </sec:ifLoggedIn>


    <sec:ifNotLoggedIn>
    <div class="login-text right">
         <a href="/login" id="login-link">Login</a> | <a href="/user/create">Sign-up</a>
    </div>
    </sec:ifNotLoggedIn>




    <g:layoutBody/>


    <div class="footer" role="contentinfo"></div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>


