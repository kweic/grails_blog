<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main" />
<title>Todo Find</title>
</head>
<body>

<br/>
<br/>
<h2>Post Search</h2>
<g:form controller="todo" method="post" >
<input type='text' name='value' value="${value}" />
<g:actionSubmit value="Search" />
</g:form>
Results:
<ol>
<g:each var="blog" in="${blogs}">
<li>${blog?.title}
<g:if test="${blog.blogEntry}">- </g:if>
${blog?.blogEntry}
</li>
</g:each>
</ol>
</body>
</html>