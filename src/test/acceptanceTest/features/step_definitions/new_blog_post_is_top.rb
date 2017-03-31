require_relative '../../features/workflows/navigation'
require_relative '../../features/workflows/visit_and_login'
require_relative '../../features/workflows/page_interactions'
require_relative '../../features/workflows/create_blog_post'
require_relative '../../features/workflows/main_page_elements'
require_relative '../../features/workflows/generate_randoms'

include RandomStrings
include Navigation
include VisitAndLogin
include PageInteractions
include CreatePost
include MainPage


Given(/^I am logged in as a blogger$/) do
  login_to_blog
end

When(/^I publish a new blog post$/) do
  @my_post_title = create_and_save_post
end

Then(/^I am notified that the blog post was successfully added$/) do
  expect(index_page_notification).to eq('New post added.')
end

And (/^the newly added blog post is at the top of the recent posts list$/) do
  click_my_blog
  expect(first_post_on_page).to eq(@my_post_title)
end