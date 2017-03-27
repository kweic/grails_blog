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
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </sec:ifLoggedIn>
                <li>
                <fieldset class="form">
                <g:form action="search" method="GET">
                    <div class="fieldcontain">
                        <label class="search-label" for="query">Search:</label>
                        <g:textField name="query" value="${params.query}"/>
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


              <g:each var="item" in="${blogsFound}" status="i">

                <div class="blogPost col-sm-12 posts-1">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        <g:link  mapping="blogLink" params="[id: item.id,title: seo.convert(value:item.title)]">
                          <div id="blog-${i}"><h2><strong>${item.title}</strong></h2></div>
                        </g:link>
                            <div><small>Created: <span id="post-date-${i}" class="text-muted">${item.dateCreated}</span> by: </small><span>${item.postBy}</span></div>


                            <div><small> feeling: <i class="text-info">${item.mood}</i></small></div>
                        </div>

                        <div class="panel-body">
                            <div class="truncate">
                                ${item.blogEntry}
                            </div>
                        </div>

                    </div>
                </div>
              </g:each>


            <div class="pagination">
                <g:paginate total="${blogCount ?: 0}" params="${filterParams}"/>
            </div>
        </div>
    </body>
</html>

    <script>
    hideShowLoginName(true)
    </script>