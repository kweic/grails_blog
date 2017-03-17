require 'webrick/utils'
include WEBrick
include Utils

module RandomStrings
  def generate_random_words(words)
    generated = ''
    i = 0
    while i < words do
      generated += random_string(rand(2..10))
      generated += ' '
      i +=1
    end
    generated
  end
end