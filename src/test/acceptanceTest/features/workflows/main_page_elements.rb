require 'page-object'

module MainPage
  include PageObject::PageFactory
  def index_page_notification
    on_page(MainBlog).message
  end

  def first_post_on_page
    on_page(MainBlog).firstBlog
  end

  # def get_blog_list_from_page
  #   list = Array.new
  #   list = @browser.find('#list-blog').all('div')
  # end


  def get_blog_count_from_page
    @browser.elements(:class => "blogPost").size
  end
end