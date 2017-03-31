require_relative '../../features/workflows/login'
require_relative '../../features/workflows/page_interactions'
require 'page-object'
require 'watir'
include Login
include PageInteractions

module VisitAndLogin
  include PageObject::PageFactory
  def login_to_blog
    login('user', 'password')
  end

  def sign_in_as(username)
    login(username, 'password')
  end
end