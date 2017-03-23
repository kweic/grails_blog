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

  def post_dates_are_reverse_order
    #todo get collection of all dates from page
    puts 'doing check dates are reverse'
    #step through and compare every two dates to check that the next is higher than the first
    #return true if get to the end, else return false
    dates_list = get_dates_from_main_page(10)
    $i = 0
    $num = 9

    while $i < $num  do
      # puts("Inside the loop i = #$i" )
      # puts dates_list[$i]
      # date_current = Date.parse(dates_list[$i])
      # date_next = Date.parse(dates_list[$i+1])
      # puts date_current

      #if(date_current < date_next)
      if(dates_list[$i] < dates_list[$i+1])
        return false
      # else if(date_current == date_next)
      #        puts 'doing time compare...'
      #        time_current = Time.parse(dates_list[$i])
      #        time_next = Time.parse(dates_list[$i+1])
      #        if(time_current < time_next)
      #          return false
      #        end
      #      end
      end
      $i +=1
    end
    true
  end

  def get_dates_from_main_page(n)
    #loop through 10 times to grab each post
    date_array = Array.new
    i = 0
    while i < n do
      puts "getting date #{i}"
      date_array << '2017-03-22 16:11:03.0'
      i += 1
    end
    puts "array size: #{date_array.size}"
    date_array
  end
end