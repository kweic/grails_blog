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

                <sec:ifLoggedIn>
                    <li><g:link class="blogs" action="blogs" id="${userId}" controller="user">My Blog</g:link></li>
                    <li>|</li>
                </sec:ifLoggedIn>


                <li><g:link class="create" controller="blog" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li>|</li>
                <li>
                <fieldset class="form">
                    <g:form action="search" method="GET">
                        <div class="fieldcontain">
                            <g:textField class="search" id="search-input" name="query" value="${params.query}" />
                            <button class="btn btn-default" type="submit"><img src="http://localhost:8080/assets/search-icon.png" width="18"></button>
                        </div>
                    </g:form>
                </fieldset>
                </li>

                <li>
                Sort:
                </li>

                <li>
                 <div class="sort-options form-group right">
                   <select class="form-control">
                     <option id="sort-name">Name</option>
                     <option id="sort-date">Last Post</option>
                     <option id="sort-by-posts">Total Posts</option>
                   </select>
                 </div>
                </li>

            </ul>
        </div>

        <div id="list-blog" class="content scaffold-list" role="main">

            <br>

            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

                <div class="title-line">
                <h1>
                <span class="blogs-icon">
                    <img src="http://localhost:8080/assets/users_icon.png" width="50" alt="users icon">
                </span>
                Users</h1>
                </div>
                <br>

                ajax results below here

                <div id="user-results">
                    <g:render template="user_results"  model="['usersFound':usersFound]"/>
                </div>


            <div class="pagination">
                <g:paginate total="${userCount ?: 0}" params="${filterParams}"/>
            </div>
        </div>
    </body>

</html>