require_relative "../../features/workflows/create_blog_post"
require_relative "../../features/workflows/edit_blog_post"

include CreatePost
include EditPost

And(/^I have created a blog post$/) do
  @my_post_title = create_and_save_post
end

When(/^I edit my blog post$/) do
  on_page()
  edit_post
end


Then(/^I am notified that the blog post was successfully edited$/) do

end

And(/^the edits I have made are saved$/) do

end