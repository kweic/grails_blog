require 'page-object'
require 'watir'

module PageInteractions
  include PageObject::PageFactory

  def click_create_new_blog
    @browser.li(:text => 'New Blog').click
  end
end