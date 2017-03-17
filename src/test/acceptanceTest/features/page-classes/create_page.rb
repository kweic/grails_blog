require 'page-object'
require_relative '../../features/workflows/navigation'
include Navigation

class CreatePage
  include PageObject
  page_url "#{base_url}/blog/create"
  text_field(:title, id: 'title')
  text_area(:blogEntry, id: 'blogEntry')
  select_list(:mood, id: 'mood')
  text_field(:postBy, id: 'postBy')
  button(:create, :id => 'create')
end