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
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/#">
                    <i class="my-blog-logo">
                        <asset:image src="blog_logo_invert.png" class="blog-logo"/>
                    </i>
                </a>
            </div>


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
                <input class="logout-button" type="submit" value="Logout">
    </form>
    </div>

    <div id="user-name" class="right">
    <small>Logged in: </small><strong>${sec.username()}</strong>
    </div>
    </sec:ifLoggedIn>


    <sec:ifNotLoggedIn>
    <div class="login-text right">
         <a href="/login">Login</a>
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


