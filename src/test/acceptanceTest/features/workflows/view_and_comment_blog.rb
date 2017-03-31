

def comment_on_blog(comment)
  on_page(ShowPage) do |page|
    #fill_user(page, "asdf")
    fill_comment(page, comment)
  end
  comment
  @browser.input(:id => 'create').click
end

def fill_user(page, user)
  page.commenter= user
end

def fill_comment(page, comment)
  page.comment = comment
end

def first_comment_on_page
  on_page(ShowPage).firstComment
end

def comments_exist_on_blog
  @browser.div(:id => 'comment-0').exists?
end