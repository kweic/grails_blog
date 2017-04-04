            <br>
            <g:each var="item" in="${comments}" status="i">

                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="blog-comment" id="comment-${i}">
                                 <pre>${item.comment}</pre>
                            </div>
                            <div>
                            <span id="comment-author-${i}"><strong>${item.user}</strong></span>
                            <span class="text-muted" id="comment-date-${i}">- <small>${item.dateCreated}</small></span>
                            </div>
                        </div>
                    </div>
            </g:each>