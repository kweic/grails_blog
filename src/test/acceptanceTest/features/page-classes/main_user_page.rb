require 'page-object'
require_relative '../../features/workflows/navigation'
include Navigation

class MainBlog
  include PageObject
  page_url "#{base_url}/blog/"
  # text_field(:title, id: 'title')
  # text_area(:blogEntry, id: 'blogEntry')
  # select_list(:mood, id: 'mood')
  # text_field(:postBy, id: 'postBy')
  link(:create, text: 'New Blog')
  div(:message, class: 'message')
end