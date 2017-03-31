require 'page-object'
require 'watir'

require_relative '../../features/workflows/page_interactions'

module PageInteractions
  include PageObject::PageFactory

  def click_create_new_blog
    on_page(MainBlog).create
  end

  def click_logout
    @browser.input(:class => 'logout-button').click
  end

  def search_for_post(searched)
    # on_page(MainBlog) do |page|
    #   page.searchInput = searched
    # end
    on_page(MainBlog).searchInput = searched;
    @browser.send_keys :enter
  end

  def click_first_blog
    @browser.div(:id => 'blog-0').click
  end

  def click_edit_button
    @browser.link(:class => 'edit').click
  end

  def click_update_button
    @browser.button(:class => 'save').click
  end

  def click_home
    on_page(MainBlog).home
  end

  def click_my_blog
    on_page(MainBlog).my_blog
  end

  def get_page_url
  @browser.url
  end
end