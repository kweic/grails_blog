require_relative '../../features/workflows/navigation'
require_relative '../../features/workflows/visit_and_login'
require_relative '../../features/workflows/page_interactions'
require_relative '../../features/workflows/create_blog_post'
include Navigation
include VisitAndLogin
include PageInteractions
include CreatePost


Given(/^I am logged in as a blogger$/) do
  login_to_blog
end

When(/^I publish a new blog post$/) do
  click_create_new_blog
  fill_post_fields
  click_save_blog
end

Then(/^I am notified that the blog post was successfully added$/) do

end

And (/^the newly added blog post is at the top of the recent posts list$/) do

end