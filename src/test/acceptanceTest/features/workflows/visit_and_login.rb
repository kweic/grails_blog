require_relative '../../features/workflows/login'
include Login

module VisitAndLogin
  def login_to_blog
    login('user', 'password')
  end
end