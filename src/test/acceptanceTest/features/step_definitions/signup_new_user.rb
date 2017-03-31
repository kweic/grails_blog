require_relative '../workflows/create_user'
include CreateUser

When(/^I sign up as a new user$/) do
  goto_main_user_index
  click_sign_up
  username = create_new_user
  sign_in_as(username)
end

Then(/^I can post blogs$/) do
  @my_post_title = create_and_save_post
  click_my_blog
  expect(first_post_on_page).to eq(@my_post_title)
end

And(/^comment on blogs$/) do
  click_first_blog
  @my_comment = generate_random_words(5).strip
  comment_on_blog(@my_comment)
  expect(first_comment_on_page).to eq(@my_comment)
end