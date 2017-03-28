<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blog.label', default: 'Blog')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-blog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-blog" class="content scaffold-edit" role="main">

            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>

            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>


            <g:hasErrors bean="${this.blog}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.blog}" var="error">
                <li><g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>

            <div class="col-sm-12">
            <g:form resource="${this.blog}" method="PUT">
                <g:hiddenField name="version" value="${this.blog?.version}" />

            <div class="pad-left">
                <fieldset class="form">
                <f:with bean="blog">
                    <f:field property="title" value="${blog.title}"/>
                    <f:field property="blogEntry" value="${blog.blogEntry}"/>
                    <f:field property="mood" value="${blog.mood}"/>
                    <f:field property="postBy" value="${blog.postBy}"/>
                    <!--<f:all bean="blog"/>-->
                    </f:with>
                </fieldset>
            </div>

                </br>

                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
            </div>


        </div>
    </body>
</html>
