require File.join(File.expand_path(File.dirname(__FILE__)), '../../../src/main/ruby/TextUtils.rb')
require "test/unit"

class TextUtilsTest < Test::Unit::TestCase
  def test_extract_words
    words = extractWords("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNldGV0dXIgc2FkaXBzY2luZyBlbGl0ciwgc2VkIGRpYW0=")
    assert_equal words.length(), 10
    assert_equal words[0].to_str, "Lorem"
    assert_equal words[1].to_str, "amet"
    assert_equal words[2].to_str, "consetetur"
    assert_equal words[3].to_str, "diam"
    assert_equal words[4].to_str, "dolor"
    assert_equal words[5].to_str, "elitr"
    assert_equal words[6].to_str, "ipsum"
    assert_equal words[7].to_str, "sadipscing"
    assert_equal words[8].to_str, "sed"
    assert_equal words[9].to_str, "sit"

  end
end