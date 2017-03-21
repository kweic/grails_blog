require 'page-object'


module EditPost
  include PageObject::PageFactory
  def edit_post(new_title)
    on_page(UpdatePage) do |page|

    end
  end


end