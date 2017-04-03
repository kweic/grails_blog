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
                        <g:textField name="query" value="${params.query}" />
                        <button class="btn btn-default" type="submit"><img src="http://localhost:8080/assets/search-icon.png" width="18"></button>
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

                <div class="title-line">
                <h1>
                <span class="blogs-icon">
                    <img src="http://localhost:8080/assets/users_icon.png" width="50" alt="users icon">
                </span>
                Users</h1>
                </div>
                 <br>


                <g:each var="user" in="${usersFound}" status="i">

                  <div class="blogPost col-sm-12">
                      <div class="panel panel-default mouse-highlight">
                          <div class="panel-heading">
                          <g:link action="blogs" id="${user.id}">
                            <div class = "inline" id="${user.username}"><h2><strong>${user.username}</strong>
                            <span>
                               <g:if test="${user.blogs[0] && user.blogs[0].mood != null}">
                                     <small> <i class="text-info">${user.blogs[0].mood}</i></small>
                                </g:if>
                                </span>
                                </h2>
                            </div>
                            <g:if test="${user.blogs.size() > 0}">
                            <div><small><strong>${user.blogs.size()}</strong> post<g:if test="${user.blogs.size() > 1}">s</g:if>
                            , Latest: <span id="post-date-${i}" class="text-muted">${user.blogs[0].dateCreated}</span></small>
                            </div>
                            </g:if>
                            <g:else>
                                <small>No posts</small>
                            </g:else>
                          </g:link>
                          </div>
                          <g:if test="${user.blogs[0] != null}">
                              <g:link action="blogs" id="${user.id}">
                                  <div class="panel-body">
                                      <h3>
                                      <div class="main-page-titles">${user.blogs[0].title}</div>
                                      </h3>
                              </g:link>


                            </div>
                          </g:if>

                      </div>
                  </div>
                </g:each>


            <div class="pagination">
                <g:paginate total="${userCount ?: 0}" params="${filterParams}"/>
            </div>
        </div>
    </body>
</html>