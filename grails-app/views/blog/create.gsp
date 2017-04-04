<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blog.label', default: 'Blog')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-blog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <sec:ifLoggedIn>
                    <li><g:link class="blogs" action="blogs" id="${userId}" controller="user">My Blog</g:link></li>
                </sec:ifLoggedIn>
            </ul>
        </div>
        <div id="create-blog" class="content scaffold-create" role="main">

            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.blog}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.blog}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>

            <br>
            <div class="title-line">
            <h1>
            <span class="blogs-icon">
                <img src="http://localhost:8080/assets/quill-pen-512.png" width="50" alt="new blog icon">
            </span>
             New Blog</h1>
            </div>



            <div class="create-form">
            <g:form action="save">

            <div class="pad-left">
                <f:with bean="blog">
                    <f:field property="title"/>
                    <f:field property="blogEntry"/>
                    <f:field property="mood"/>
                    <f:field property="postBy" value="${sec.username()}" widget-hidden="true" label-hidden="true"/><label>${sec.username()}</label>
                </f:with>
            </div>
                </br>


                <div class="pad-left">
                    <g:submitButton name="create" class="save btn btn-default" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>

            </g:form>
            </div>


        </div>
    </body>
</html>
