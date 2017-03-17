require 'page-object'
module Login
  include PageObject::PageFactory
  def login(user, password)
    visit_page(LoginPage)
    on_page(LoginPage) do |page|
      page.username = user
      page.pswd = password
      on_page(LoginPage).login
    end
  end
end