

When(/^I search for a blog post$/) do
  @my_title = first_post_on_page
  search_for_post(@my_title)
end

Then(/^I should see posts with that value in the title$/) do
  expect(@browser.text.include?(@my_title)).to be true
end