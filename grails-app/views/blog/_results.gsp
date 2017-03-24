<ol>
THE RESULTS PAGE TEMPLATE!!

<g:each var="comment" in="${comments}">


<li>
user: ${comment?.user}
<br>
comment: ${comment?.comment}
</li>
</g:each>
</ol>