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
                <div class="blog-date text-muted"><small>${this.blog.dateCreated}</small> - post by: ${this.blog.postBy}</div>
            </div>

        </div>
            <sec:ifLoggedIn>
            <g:if test="${sec.username() == this.blog.postBy}">
                <div class="show-blog-buttons">
                <span class="right">
                    <g:form resource="${this.blog}" method="DELETE">
                        <button type="submit" class="delete btn btn-default" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete</button>
                    </g:form>
                </span>
                <span class="right">
                    <g:link class="edit" action="edit" resource="${this.blog}">
                        <button type="button" class="edit btn btn-default">Edit</button>
                    </g:link>
                </span>
                </div>
            </g:if>
            </sec:ifLoggedIn>


        <br>


            <sec:ifLoggedIn>
                <form onsubmit="jQuery.ajax({type:'POST',data:jQuery(this).serialize(),
                                            url:'/blog/userComments',success:function(data,textStatus){
                jQuery('.comment-area').val(''),
                jQuery('#comments-area').html(data);
                                            },error:function(XMLHttpRequest,textStatus,errorThrown){}});return false"
                                            method="post">

                                            <div class="form-group pad-left">

                    <g:hiddenField name="user" value="${sec.username()}"/>

                    <div class="col-sm-12">
                        <label class="comment-tag">Comment:</label>
                        <g:textArea class="comment-area" name="comment" rows="4" />
                        </div>

                        <div class="col-sm-12">
                            <small>commenting as </small>${sec.username()}
                        </div>

                        <g:hiddenField name="blogId" value="${blog.id}" />                  
                        <div class="col-sm-12">
                            <g:submitButton name="create" class="save btn" value="Post Comment" />
                        </div>
                    </div>
                </form>
            </sec:ifLoggedIn>

            <sec:ifNotLoggedIn>
               <div class="center"><a href="/login">Login</a> or <a href="/user/create">Sign-up</a> to comment.</div>
            </sec:ifNotLoggedIn>

            <br>

            <div id="comments-area">
                <g:render template="results"  model="['comments':blog.comments]"/>
            </div>
    </body>
</html>



