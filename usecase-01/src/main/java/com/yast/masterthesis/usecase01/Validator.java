package com.yast.masterthesis.usecase01;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.Map;

public class Validator {

    private Context context;
    private Value nodeModule;

    public Validator() {
        context = buildContext();
        nodeModule = context.eval("js", "require('validator');");
    }

    public boolean isMACAddress(String input) {
        return nodeModule.getMember("isMACAddress").execute(input).asBoolean();
    }

    public boolean isIBAN(String input) {
        return nodeModule.getMember("isIBAN").execute(input).asBoolean();
    }

    public boolean isHexColor(String input) {
        return nodeModule.getMember("isHexColor").execute(input).asBoolean();
    }

    public boolean isJWT(String input) {
        return nodeModule.getMember("isJWT").execute(input).asBoolean();
    }

    public boolean isCreditCard(String input) {
        return nodeModule.getMember("isCreditCard").execute(input).asBoolean();
    }

    public boolean isEthereumAddress(String input) {
        return nodeModule.getMember("isEthereumAddress").execute(input).asBoolean();
    }

    public boolean isStrongPassword(String input) {
        return nodeModule.getMember("isStrongPassword").execute(input).asBoolean();
    }

    private Context buildContext() {
        Map<String, String> options = new HashMap<>();
        options.put("js.commonjs-require", "true");
        options.put("js.commonjs-require-cwd", "./node_modules");
        context = Context.newBuilder("js")
                .allowExperimentalOptions(true)
                .allowIO(true)
                .options(options)
                .build();
        return context;
    }

}
