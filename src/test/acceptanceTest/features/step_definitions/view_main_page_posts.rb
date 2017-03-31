


Given(/^my favorite blogger has been very active$/) do
  login_to_blog
  click_my_blog
  blog_count = get_blog_count_from_page
  puts "first check count is: #{blog_count}"
  while blog_count < 10 do
    create_and_save_post
    click_my_blog
    blog_count = get_blog_count_from_page
    puts "blog count: #{blog_count}"
  end
  click_logout
end

Then(/^then I should see a summary of my favorite blogger's (\d+) most recent posts in reverse order$/) do |arg|
  blog_count = get_blog_count_from_page
  expect(blog_count).to eq 10

  dates_list = get_dates_from_main_page(10)
  expect(post_dates_are_reverse_order(dates_list)).to eq true
end