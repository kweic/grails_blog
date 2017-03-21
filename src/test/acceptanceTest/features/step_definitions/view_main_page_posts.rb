


Given(/^my favorite blogger has been very active$/) do
  #todo use this to log in as visitor
end

Then(/^then I should see a summary of my favorite blogger's (\d+) most recent posts in reverse order$/) do |arg|
  blog_count = get_blog_count_from_page
  expect(blog_count).to eq 10

end