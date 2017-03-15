<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blog.label', default: 'Blog')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
         <jq:javascript library="jquery" />
    </head>
    <body>
        <a href="#list-blog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-blog" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <h2>Ajax Search</h2>
            <jq:formRemote name="remoteSearch"
                url="[controller:'blog',action:'remoteSearch']"
                update="[success:'results', failure:'error']" >
            <input type='text' name='value' value="${value}" />
            <jq:submitButton name="search" value="Search" />
            </jq:formRemote>

            <div id="error"></div>

            Results:
            <div id="results">
            </div>

            <f:table collection="${blogList}" />

            <div class="pagination">
                <g:paginate total="${blogCount ?: 0}" />
            </div>
        </div>
    </body>
</html>