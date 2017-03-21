require 'page-object'
require_relative '../../features/workflows/navigation'
include Navigation

class ShowPage
  include PageObject
  page_url "#{base_url}/blog/show"
  a(:edit, class: 'edit')
  text_area(:blogEntry, id: 'blogEntry')
  select_list(:mood, id: 'mood')
  text_field(:postBy, id: 'postBy')
  button(:create, :id => 'create')
end