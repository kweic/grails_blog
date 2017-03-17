require_relative '../../features/workflows/visit_and_login'
require_relative '../../features/workflows/page_interactions'

include VisitAndLogin
include PageInteractions

Given(/^I visit the blog for my favorite blogger$/) do
  login_to_blog
end

When(/^I choose a blog post$/) do
  click_first_blog
end

Then(/^I should see comments left by other readers$/) do

end