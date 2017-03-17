require 'page-object'

module MainPage
  include PageObject::PageFactory
  def index_page_notification
    on_page(MainBlog).message
  end

  def first_post_on_page
    on_page(MainBlog).firstBlog
  end
end