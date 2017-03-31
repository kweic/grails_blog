require 'page-object'

class UserIndex
  include PageObject
  page_url "#{base_url}"
end