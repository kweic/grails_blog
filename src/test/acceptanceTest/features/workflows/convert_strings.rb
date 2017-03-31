
module StringConverter
  def make_text_link_friendly(text)
    text_fix = text.gsub ' ', '-'
    text_fix = text_fix.downcase
    text_fix
  end
end