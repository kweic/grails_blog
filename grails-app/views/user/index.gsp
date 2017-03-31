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
                </sec:ifLoggedIn>

                <li><g:link class="create" controller="blog" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>

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

                <g:each var="user" in="${usersFound}" status="i">

                  <div class="blogPost col-sm-12">
                      <div class="panel panel-default">
                          <div class="panel-heading">
                          <g:link action="blogs" id="${user.id}">
                            <div id="${user.username}"><h2><strong>${user.username}</strong></h2></div>
                            <div><small><strong>${user.blogs.size()}</strong> posts</small></div>
                          </g:link>
                          </div>
                          <g:if test="${user.blogs[0] != null}">
                              <g:link action="blogs" id="${user.id}">
                                  <div class="panel-body">

                                      <h3>
                                      <div class="main-page-titles">${user.blogs[0].title}</div>
                                      </h3>
                                      <div class="truncate">
                                      ${user.blogs[0].blogEntry}
                                      </div>

                              </g:link>
                            <g:if test="${user.blogs[0].mood != null}">
                                 <div><small> feeling: <i class="text-info">${user.blogs[0].mood}</i></small></div>
                            </g:if>

                            <div><small>Last post: <span id="post-date-${i}" class="text-muted">${user.blogs[0].dateCreated}</span></small></div>
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