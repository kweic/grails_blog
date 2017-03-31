require_relative "../../features/workflows/create_blog_post"
require_relative "../../features/workflows/edit_blog_post"
require_relative "../../features/workflows/page_interactions"
require_relative "../../features/workflows/main_page_elements"

include CreatePost
include EditPost
include PageInteractions
include MainPage

And(/^I have created a blog post$/) do
  @my_post_title = create_and_save_post
  click_my_blog
end

When(/^I edit my blog post$/) do
  click_first_blog
  click_edit_button
  @new_title = generate_random_words(2).strip
  fill_post_fields(@new_title)
  click_update_button
end


Then(/^I am notified that the blog post was successfully edited$/) do
  expect(index_page_notification).to eq('Post updated.')
end

And(/^the edits I have made are saved$/) do
  click_my_blog
  expect(first_post_on_page).to eq(@new_title)
end
