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
                <li>|</li>
                <li><g:link class="create" controller="blog" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </sec:ifLoggedIn>
                <li>
                <li>|</li>
                <fieldset class="form">
                <g:form action="search" controller="blog" method="GET">
                    <div class="fieldcontain">
                        <g:textField name="query" value="${params.query}" />
                        <g:hiddenField name="id" value="${id}"/>
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
                    <img src="http://localhost:8080/assets/book_icon.png" width="50" alt="book icon">
                </span>
                Posts by ${user.username}</h1>
                </div>

                <br>

                <g:if test="${user.blogs.size() == 0}">
                    <div class="pad-left">This user has no posts.</div>
                </g:if>

              <g:each var="item" in="${blogsFound}" status="i">

                <div class="blogPost col-sm-12 posts-1">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        <g:link  mapping="blogLink" params="[id: item.id,title: seo.convert(value:item.title)]">
                          <div class="main-page-titles" id="blog-${i}"><h2><strong>${item.title}</strong></h2></div>
                        </g:link>
                            <div><small>Created: <span id="post-date-${i}" class="text-muted">${item.dateCreated}</span> by: </small><span>${item.postBy}</span></div>

                            <g:if test="${item.mood != null}">
                            <div><small> feeling: <i class="text-info">${item.mood}</i></small></div>
                            </g:if>
                        </div>

                        <g:link  mapping="blogLink" params="[id: item.id,title: seo.convert(value:item.title)]">
                        <div class="panel-body">
                            <div class="truncate">
                                ${item.blogEntry}
                            </div>

                            <g:if test="${item.comments.size() != 0}">
                            <small><strong>${item.comments.size()}</strong> comments</small>
                            </g:if>
                            <g:else>
                            <small>no comments</small>
                            </g:else>
                        </div>
                        </g:link>

                    </div>
                </div>
              </g:each>

            <g:if test="${user.blogs.size() > 10}">
            <div class="pagination">
                <g:paginate total="${blogCount ?: 0}" params="${filterParams}"/>
            </div>
            </g:if>
        </div>
    </body>
</html>