require 'watir'
Before() do |scenario|
  @browser = Watir::Browser.new :chrome
end

Before() do
  #create a new user (cucumber, password)
  #login and create 12 posts
  #logout
  #create new user (user2, password)
  #login, comment on first blog
  #logout
end

After() do |scenario|
  #delete last 12 blogs
  sleep(1)
end