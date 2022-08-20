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

  return sortWords(plainText)
end

Polyglot.export_method("extractWords")
