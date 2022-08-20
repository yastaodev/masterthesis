require "base64"

def decode64(encodedText)
  return Base64.decode64(encodedText)
end

def extractWords(text)
  plainText = decode64(text)

  # puts plainText
  # puts File.basename(Dir.getwd)

  Polyglot.eval_file("js", "src/main/javascript/TextUtils.js")

  sortWords = Polyglot.import_method("sortWords")

  #        String[] expectedWords = {"Lorem", "amet", "consetetur", "diam", "dolor", "elitr", "ipsum", "sadipscing", "sed", "sit"};
  # TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNldGV0dXIgc2FkaXBzY2luZyBlbGl0ciwgc2VkIGRpYW0=

  return sortWords(plainText)
end

Polyglot.export_method("extractWords")
