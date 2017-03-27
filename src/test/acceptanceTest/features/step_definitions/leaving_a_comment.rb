require_relative '../../features/workflows/generate_randoms'

include RandomStrings

Given(/^I am reading a blog post from my favorite blogger$/) do
  login_to_blog
  click_first_blog
end

When(/^I add my genius comment to the blog post$/) do
  @my_comment = generate_random_words(5).strip
  comment_on_blog(@my_comment)
end

Then(/^my genius comment is at the top of the blog post comments$/) do
  expect(first_comment_on_page).to eq(@my_comment)
end