
module StringConverter
  def make_text_link_friendly(text)
    text = @selected_title.gsub! ' ', '-'
    text = text.downcase
    text
  end
end