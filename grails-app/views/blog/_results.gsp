<ol>
<g:each var="blog" in="${blogs}">
<li>${blog?.title}
<g:if test="${blog.blogEntry}">- </g:if>
${blog?.blogEntry}
 </li>
 </g:each>
 </ol>