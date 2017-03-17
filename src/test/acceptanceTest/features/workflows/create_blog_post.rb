require 'page-object'

module CreatePost
  include PageObject::PageFactory
  def fill_post_fields
    on_page(CreatePage) do |page|
      page.title = 'hey'
      page.blogEntry = '123 abc'
      #page.mood = 'Excited'
      @browser.select(:id => 'mood').click
      @browser.option(:value => 'Excited').click
      page.postBy = 'Kevin'
    end
  end

  def click_save_blog
    on_page(CreatePage).create
  end

end