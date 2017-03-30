<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blog.label', default: 'Blog')}" />

        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-blog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <sec:ifLoggedIn>
                <li><g:link class="create" controller="user" action="submit"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </sec:ifLoggedIn>
                <li>
                <fieldset class="form">
                <g:form action="search" method="GET">
                    <div class="fieldcontain">
                        <label class="search-label" for="query">Search:</label>
                        <g:textField name="query" value="${params.query}" />
                    </div>
                </g:form>
                </fieldset>

                </li>

            </ul>
        </div>

        <div id="list-blog" class="content scaffold-list" role="main">

            <br>

            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
                On user index.gsp

                <br/>
              <g:each var="user" in="${usersFound}" status="i">
                ${user.username}
                ${user.blogs}
                <br/>
              </g:each>


            <div class="pagination">
                <g:paginate total="${userCount ?: 0}" params="${filterParams}"/>
            </div>
        </div>
    </body>
</html>