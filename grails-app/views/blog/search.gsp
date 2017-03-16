<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main" />
<title>Todo Find</title>
<j:javascript library="jquery" />
</head>
<body>

<h2>Ajax Search</h2>
<j:formRemote name="remoteSearch"
url="[controller:'blog',action:'remoteSearch']"
update="[success:'results', failure:'error']" >

<input type='text' name='value' value="${value}" />
<j:submitButton name="search" value="Search" />
</j:formRemote>
<div id="error"></div>
Results:
<div id="results"></div>

            <fieldset class="form">
                <g:form action="search" method="GET">
                    <div class="fieldcontain">
                        <label for="query">Search:</label>
                        <g:textField name="query" value="${params.query}"/>
                    </div>
                </g:form>
            </fieldset>
                ${blogsList}
                 <ol>
                     <g:each var="blog" in="${blogsList}">
                        <li>${blog?.title}
                             <g:if test="${blog.title}">- </g:if>
                            ${blog?.title}
                        </li>
                     </g:each>
                  </ol>

</body>
</html>