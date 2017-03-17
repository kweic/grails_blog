require 'page-object'
require 'watir'

module Navigation
  include PageObject::PageFactory

  def base_url
    @url = 'localhost:8080/'
  end
  def goto_login_page
    visit_page(LoginPage)
  end
  def goto_the_create_post_page
    visit_page(CreatePostPage)
  end

  def reload_page
    @browser.refresh
  end

end