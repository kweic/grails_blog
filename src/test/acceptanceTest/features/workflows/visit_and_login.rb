require_relative '../../features/workflows/login'
require_relative '../../features/workflows/page_interactions'
include Login
include PageInteractions

module VisitAndLogin
  def login_to_blog
    login('user', 'password')
  end
end