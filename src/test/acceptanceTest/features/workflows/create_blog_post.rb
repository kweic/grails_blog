require 'page-object'
require_relative '../../features/workflows/generate_randoms'

include RandomStrings

module CreatePost
  include PageObject::PageFactory
  def fill_post_fields(specified_title)
    on_page(CreatePage) do |page|
      page.title = specified_title
      page.blogEntry = generate_random_words(rand(20..100))
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