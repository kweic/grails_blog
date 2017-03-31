

module CreateUser

  def create_new_user
    username = generate_random_words(1).strip
    fill_username_and_password(username)
    username
  end

  def fill_username_and_password(username)
    puts "creating #{username}"
    on_page(CreateUserPage) do |page|
      fill_username(page, username)
      fill_password(page, 'password')
    end
    @browser.input(:id => 'create').click
  end

  def fill_username(page, username)
    page.username = username;
  end

  def fill_password(page, password)
    page.password = password;
  end
end