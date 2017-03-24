<ol>
<g:each var="blog" in="${blogs}">
<li>${blog?.name}
<g:if test="${blog.title}">- </g:if>
${blog?.title}
</li>
</g:each>
</ol>