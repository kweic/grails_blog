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
                <sec:ifLoggedIn>
                <li>|</li>
                    <li><g:link class="blogs" action="blogs" id="${userId}" controller="user">My Blog</g:link></li>
                </sec:ifLoggedIn>
                <li>|</li>
                <li><g:link class="create" controller="blog" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>

                </ul>
        </div>

        <br>



        <div id="show-blog" class="content scaffold-show" role="main">

            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
                <div class="col-sm-12">
                <div class="well well-lg">
                    <div class="blog-title"><h1><strong>${this.blog.title}</strong></h1></div>
                    <g:if test="${this.blog.mood != null}">
                        <div class="blog-mood text-info">Mood: ${this.blog.mood}</div>
                    </g:if>
                    <div class="blog-blogEntry"><pre>${this.blog.blogEntry}</pre></div>
                    <div class="blog-date text-muted"><small>${this.blog.dateCreated}</small> - post by: ${this.blog.postBy}</div>
                </div>


                <sec:ifLoggedIn>
                    <g:if test="${sec.username() == this.blog.postBy}">
                        <div class="show-blog-buttons inline">


                                <g:link class="edit" action="edit" resource="${this.blog}">
                                    <button type="button" class="edit btn btn-default right">Edit</button>
                                </g:link>

                                <g:form resource="${this.blog}" method="DELETE">
                                    <button type="submit" class="delete btn btn-default right" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete</button>
                                </g:form>


                        </div>
                        <br>
                    </g:if>
                </sec:ifLoggedIn>
            </div>
        </div>
        <br>
    <sec:ifLoggedIn>
            <div class="col-sm-12">

            <form onsubmit="jQuery.ajax({type:'POST',data:jQuery(this).serialize(),
                                        url:'/blog/userComments',success:function(data,textStatus){
                                        jQuery('.comment-area').val(''),
                                        jQuery('#comments-area').html(data);
                                        },error:function(XMLHttpRequest,textStatus,errorThrown){}});return false"
                                        method="post">

                <div class="form-group pad-left">

                    <label class="comment-tag"> Comment</label>
                    <g:textArea class="comment-area" name="comment" rows="4" ></g:textArea>
                    <br>
                    <small>commenting as </small>${sec.username()}
                    <br>
                    <g:hiddenField name="user" value="${sec.username()}"/>
                    <g:hiddenField name="blogId" value="${blog.id}" />    
                    <g:submitButton name="create" class="save btn btn-default" value="Post Comment" />
                    </div>



                </div>
            </form>
        </div>
    </sec:ifLoggedIn>





            <sec:ifNotLoggedIn>
               <div class="center"><a href="/login">Login</a> or <a href="/user/create">Sign-up</a> to comment.</div>
            </sec:ifNotLoggedIn>

        <div class="col-sm-12">
            <div id="comments-area">
                <g:render template="results"  model="['comments':blog.comments]"/>
            </div>
        </div>

    </body>
</html>



