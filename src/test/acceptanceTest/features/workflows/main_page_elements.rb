require 'page-object'

module MainPage
  include PageObject::PageFactory
  def index_page_notification
    on_page(MainBlog).message
  end

  def first_post_on_page
    on_page(MainBlog).firstBlog
  end

  def get_blog_count_from_page
    @browser.elements(:class => "blogPost").size
  end

  def post_dates_are_reverse_order(dates_list)
    $i = 0
    $dates_size = dates_list.size-1
    while $i < $dates_size do
      if(dates_list[$i] < dates_list[$i+1])
        return false
      end
      $i +=1
    end
    true
  end

  def get_dates_from_main_page(n)
    date_array = Array.new
    i = 0
    while i < n do
      date_array << @browser.div(id: "post-date-#{i}").text
      i += 1
    end
    date_array
  end
end