require 'page-object'
require_relative '../../features/workflows/generate_randoms'

include RandomStrings


module CreatePost
  include PageObject::PageFactory
  def fill_post_fields(specified_title)
    on_page(CreatePage) do |page|
      fill_title_field(page, specified_title)
      fill_blog_entry(page)
      select_mood
      fill_post_by(page)
    end
  end

  def fill_title_field(page, specified_title)
    page.title = specified_title
  end

  def fill_blog_entry(page)
    page.blogEntry = generate_random_words(rand(20..100))
  end

  def select_mood
    @browser.select(:id => 'mood').click
    @browser.option(:value => 'Excited').click
  end

  def fill_post_by(page)
    page.postBy = 'Kevin'
  end

  def click_save_blog
    on_page(CreatePage).create
  end

  def create_and_save_post
    click_create_new_blog
    @my_post_title = generate_random_words(rand(2..7)).strip
    fill_post_fields(@my_post_title)
    click_save_blog
    @my_post_title
  end

end