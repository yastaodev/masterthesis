package com.yast.masterthesis.usecase01;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> options = new HashMap<>();
        // Enable CommonJS experimental support.
        options.put("js.commonjs-require", "true");
        // Folder where the NPM modules to be loaded are located
        options.put("js.commonjs-require-cwd", "/home/yastao/node_modules/");
        try (Context context = Context.newBuilder("js")
                .allowExperimentalOptions(true)
                .allowIO(true)
                .options(options)
                .build()) {
            Value module = context.eval("js", "require('validator');");
            module.getMemberKeys().forEach(System.out::println);
            Value value = module.getMember("isIBAN").execute("DE86390700240173134800");
            System.out.println(value);
        }
    }
}
