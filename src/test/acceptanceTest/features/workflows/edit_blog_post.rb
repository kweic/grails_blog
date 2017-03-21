require 'page-object'


module EditPost
  include PageObject::PageFactory
  def edit_post
    on_page(CreatePage) do |page|

    end
  end


end