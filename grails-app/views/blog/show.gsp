<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blog.label', default: 'Blog')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>

    </head>


    <body>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

        <a href="#list-blog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>

        <br>
        <div id="show-blog" class="content scaffold-show" role="main">
            <!--<h1><g:message code="default.show.label" args="[entityName]" /></h1>-->
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div class="well well-lg">
                <div class="blog-title"><h1><strong>${this.blog.title}</strong></h1></div>
                <div class="blog-mood text-info">Mood: ${this.blog.mood}</div>
                <div class="blog-blogEntry"><pre>${this.blog.blogEntry}</pre></div>
            <div class="blog-date text-muted"><small>${this.blog.dateCreated}</small></div>
            </div>


            <g:form resource="${this.blog}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.blog}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>


            <div>
                <form onsubmit="jQuery.ajax({type:'POST',data:jQuery(this).serialize(),
                                            url:'/blog/userComments',success:function(data,textStatus){

                jQuery('#comments-area').html(data);
                                            },error:function(XMLHttpRequest,textStatus,errorThrown){}});return false"
                                            method="post">

                                            <div class="form-group pad-left">

                    <g:hiddenField name="user" value="${user}"/>

                    <div class="col-sm-12">
                        <label>Comment:</label>
                        <g:textArea name="comment" />
                    </div>
                    <div class="col-sm-12">
                        <small>commenting as </small>${user}
                    </div>
                    <g:hiddenField name="blogId" value="${blog.id}" />                  
                    <div class="col-sm-12">
                        <g:submitButton name="create" class="save" value="Post Comment" />
                    </div>
                    </div>
                                        </form>
            </div>
            
            <div id="comments-area">
                <g:render template="results"  model="['comments':blog.comments]"/>
            </div>
    </body>
</html>



