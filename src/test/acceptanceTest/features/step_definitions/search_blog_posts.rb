

When(/^I search for a blog post$/) do
  @my_title = create_and_save_post
  click_home
  search_for_post(@my_title)
end

Then(/^I should see posts with that value in the title$/) do
  expect(@browser.text.include?(@my_title)).to be true
end