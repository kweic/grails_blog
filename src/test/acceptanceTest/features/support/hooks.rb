require 'watir'
Before() do |scenario|
  @browser = Watir::Browser.new :chrome
end
After() do |scenario|
  sleep(5)
end