<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blog.label', default: 'Blog')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <style>
    textarea{
      resize: none;
      outline: none;
      width: 394px;
      font-family: tahoma;
      background: #f9f9f9;
    }

    textarea:focus{
      background: #fff;
    }

    input[type="submit"]{
      width: 400px;
      padding: 5px 0px;
      font-weight: bold;
      margin-top: -6px;
    }

    .content{
      width: 100%;
    }

    .comments{
      width: 400px;
      margin: 30px auto;
    }

    .insert-text{
      position: relative;
    }

    .insert-text .loading{
      position: absolute;
      bottom: -25px;
      display: none;
    }

    .insert-text .total-comment{
      position: absolute;
      bottom: -25px;
      right: 0px;
    }

    .insert-text .total-comment:before{
      content: "Total comment: ";
      font-weight: bold;
    }

    .list-comments{
      margin-top: 30px;
      border: 1px solid #ccc;
      background: #f0f0f0;
    }

    .list-comments > div{
      padding: 10px;
      border-bottom: 1px solid #ccc;
    }

    .list-comments > div:last-child{
      border-bottom: none;
    }

    .editor{
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .editor-header{
      border-bottom: 1px solid #ccc;
    }

    .editor-header a{
      display: inline-block;
      padding: 10px;
      color: #666;
    }

    .editor-header a:hover{
      color: #000;
    }

    .editor-content{
      padding: 10px;
      outline: none;
      min-height: 80px;
      background: #f9f9f9;
      border-radius: 0px 0px 5px 5px;
    }

    .editor-content:focus{
      background: #fff;
    }

    b{
      font-weight: bold;
    }

    i{
      font-style: italic;
    }

    p{
      line-height: 20px;
    }

    a{
      text-decoration: none;
    }

    [data-role="bold"]{
      font-weight: bold;
    }

    [data-role="italic"]{
      font-style: italic;
    }

    [data-role="underline"]{
      text-decoration: underline;
    }

    [class^="menu"] {
      position: relative;
      top: 6px;
      display: block;
      width: 27px;
      height: 2px;
      margin: 0 auto;
      background: #999;
    }

    [class^="menu"]:before {
      content: '';
      top: -5px;
      width: 80%;
      position: relative;
      display: block;
      height: 2px;
      margin: 0 auto;
      background: #999;
    }

    [class^="menu"]:after {
      content: '';
      top: 3px;
      width: 80%;
      position: relative;
      display: block;
      height: 2px;
      margin: 0 auto;
      background: #999;
    }

    .menu-left {
      margin-right: 5px;
    }

    .menu-left:before{
      margin-right: 5px;
    }

    .menu-left:after{
      margin-right: 5px;
    }

    .menu-right {
      margin-left: 5px;
    }

    .menu-right:before{
      margin-left: 5px;
    }

    .menu-right:after{
      margin-left: 5px;
    }
    </style>
    <body>
        <a href="#list-blog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="search" action="search"><g:img dir="images" file="search-icon.png" width="20" height="20"/>Search</g:link></li>
            </ul>
        </div>
        <div id="show-blog" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="blog" />
            <g:form resource="${this.blog}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.blog}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>

            <div class="content">
              <div class="comments">
                <div class="editor">
                  <div class="editor-header">
                    <a href='#' data-role='bold'>B</a>
                    <a href='#' data-role='italic'>I</a>
                    <a href='#' data-role='underline'>U</a>
                    <a href='#' data-role='justifyleft'><i class="menu-left"></i></a>
                    <a href='#' data-role='justifycenter'><i class="menu-center"></i></a>
                    <a href='#' data-role='justifyright'><i class="menu-right"></i></a>
                  </div>
                  <div id="text" class="editor-content" contenteditable>
                    <p>Let's make a statement!</p>
                    <p>This is an italicised sentence.</p>
                    <p>Very important information.</p>
                  </div>
                </div>
                <div class="insert-text">
                  <span class="loading">Loading...</span>
                  <span class="total-comment"></span>
                  <p>
                    <input type="submit" value="Comment" />
                  </p>
                </div>
                <div class="list-comments">
                  <div>Hello!</div>
                  <div>My name's Tuan Linh.</div>
                </div>
              </div>
            </div>
        </div>
    </body>
</html>
