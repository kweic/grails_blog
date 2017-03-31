require 'page-object'

module Login
  include PageObject::PageFactory
  def login(user, password)
    goto_login_page
    on_page(LoginPage) do |page|
      page.username = user
      page.pswd = password
      on_page(LoginPage).login
    end
  end
end