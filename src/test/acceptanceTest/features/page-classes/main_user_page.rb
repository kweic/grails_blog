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
  link(:home, text: 'Home')
  div(:message, class: 'message')
  div(:firstBlog, id: 'blog-0')
  link(:firstBlogLink, id: 'blog-0')
  text_field(:searchInput, id: 'query')
  button(:search, id: 'search')
end