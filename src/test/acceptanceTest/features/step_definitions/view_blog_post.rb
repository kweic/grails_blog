require_relative '../../features/workflows/visit_and_login'
require_relative '../../features/workflows/page_interactions'
require_relative '../../features/workflows/main_page_elements'
require_relative '../../features/workflows/convert_strings'

include VisitAndLogin
include PageInteractions
include MainPage
include StringConverter

Given(/^I visit the blog for my favorite blogger$/) do
  login_to_blog
end

When(/^I choose a blog post$/) do
  @selected_title =first_post_on_page
  puts 'selected title is: '+@selected_title
  click_first_blog
end

Then(/^I should see comments left by other readers$/) do

end

Then(/^the url should contain information about the post$/) do
  url_from_page = get_page_url
  converted_title = make_text_link_friendly(@selected_title)
  url_from_page.should match(converted_title)
end

Then(/^I should see the blog post$/) do
  pending
end