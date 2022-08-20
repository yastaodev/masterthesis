package com.yast;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.*;

public class TextUtils {

    public static String[] extractWords(String base64String) throws IOException {
        String[] result = null;
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        File script = new File("src/main/ruby/TextUtils.rb");
        Source source = Source.newBuilder("ruby", script).build();
        polyglot.eval(source);
        Value words = polyglot.getPolyglotBindings().getMember("extractWords").execute(base64String);
        if (words.hasArrayElements()) {
            result = new String[(int) words.getArraySize()];
            for (int i = 0; i < words.getArraySize(); i++) {
                result[i] = String.valueOf(words.getArrayElement(i));
            }
        }
        return result;
    }

}
