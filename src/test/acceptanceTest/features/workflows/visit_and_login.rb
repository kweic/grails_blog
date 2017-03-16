require_relative '../../features/workflows/login'
include Login

module VisitAndLogin
  def goto_main_blog_page

  end

  def login_to_blog
    login('user', 'password')
  end
end