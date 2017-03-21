require 'page-object'
require 'watir'

require_relative '../../features/workflows/page_interactions'

module PageInteractions
  include PageObject::PageFactory

  def click_create_new_blog
    on_page(MainBlog).create
  end

  def click_first_blog
    @browser.div(:id => 'blog-0').click
  end

  def click_edit_button
    @browser.link(:class => 'edit').click
  end

  def click_update_button
    @browser.input(:class => 'save').click
  end

  def fill_comment_field(comment)
    @browser.text_area(id: 'comment-area').text = comment;
  end

  def click_home
    on_page(MainBlog).home
  end

  def get_page_url
  @browser.url
  end
end