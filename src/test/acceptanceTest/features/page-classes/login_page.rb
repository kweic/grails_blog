require 'page-object'
require_relative '../../features/workflows/navigation'
include Navigation

class LoginPage
  include PageObject
  page_url "#{base_url}/login"
  text_field(:username, id: 'username')
  text_field(:pswd, id: 'password')
  button(:login, id: 'submit')
end