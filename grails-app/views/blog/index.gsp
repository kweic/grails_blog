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
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="search" action="search"><g:img dir="images" file="search-icon.png" width="20" height="20"/>Search</g:link></li>
            </ul>
        </div>

        <div id="list-blog" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>


              <g:each var="item" in="${blogList}" status="i">

                <div class="col-sm-12 posts-1">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        <g:link  mapping="blogLink" params="[id: item.id,title: seo.convert(value:item.title)]">
                          <div id="blog-${i}">${item.title}</div>
                        </g:link>

                            <div>author: ${item.postBy}</div>
                            <div class="text-muted"><small>${item.dateCreated}</small></div>
                            <div class="text-info"><small> ${item.mood}</small></div>
                        </div>
                        <div class="panel-body">${item.blogEntry}</div>
                    </div>
                </div>
              </g:each>


            <div class="pagination">
                <g:paginate total="${blogCount ?: 0}" />
            </div>
        </div>
    </body>
</html>