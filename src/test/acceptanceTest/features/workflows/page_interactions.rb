require 'page-object'
require 'watir'

module PageInteractions
  include PageObject::PageFactory

  def click_create_new_blog
    on_page(MainBlog).create
  end
end