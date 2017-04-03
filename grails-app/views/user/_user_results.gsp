                <g:each var="user" in="${usersFound}" status="i">

                  <div class="blogPost col-sm-12">
                      <div class="panel panel-default mouse-highlight">
                          <div class="panel-heading">
                          <g:link action="blogs" id="${user.id}">
                            <div class = "inline" id="${user.username}"><h2><strong>${user.username}</strong>
                            <span>
                               <g:if test="${user.blogs[0] && user.blogs[0].mood != null}">
                                     <small> <i class="text-info">${user.blogs[0].mood}</i></small>
                                </g:if>
                                </span>
                                </h2>
                            </div>
                            <g:if test="${user.blogs.size() > 0}">
                            <div><small><strong>${user.blogs.size()}</strong> post<g:if test="${user.blogs.size() > 1}">s</g:if>
                            , Latest: <span id="post-date-${i}" class="text-muted">${user.blogs[0].dateCreated}</span></small>
                            </div>
                            </g:if>
                            <g:else>
                                <small>No posts</small>
                            </g:else>
                          </g:link>
                          </div>
                          <g:if test="${user.blogs[0] != null}">
                              <g:link action="blogs" id="${user.id}">
                                  <div class="panel-body">
                                      <h3>
                                      <div class="main-page-titles">${user.blogs[0].title}</div>
                                      </h3>
                              </g:link>


                            </div>
                          </g:if>

                      </div>
                  </div>
                </g:each>