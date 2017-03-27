            <g:each var="item" in="${comments}" status="i">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="comment-author-${i}"><strong>${item.user}</strong></div>
                        </div>

                        <div class="panel-body">
                            <div class="blog-comment" id="comment-${i}">
                                 ${item.comment}
                            </div>
                            <div class="text-muted" id="comment-date-${i}"><small>${item.dateCreated}</small></div>
                        </div>
                    </div>
            </g:each>