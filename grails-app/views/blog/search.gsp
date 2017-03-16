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

<g:form controller="blog" method="post" >
<input type='text' name='value' value="${value}" />
<g:actionSubmit value="Search" />
</g:form>
Results:
<ol>
<g:each var="todo" in="${todos}">
<li>${todo?.name}
<g:if test="${todo.note}">- </g:if>
${todo?.note}
</li>
</g:each>
</ol>
</body>
</html>