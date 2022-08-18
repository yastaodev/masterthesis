package com.yast;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FibonacciRunner {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("usecase-06/target/lib/fibonacci.wasm");
        byte[] binary = Files.readAllBytes(path);
        Context.Builder contextBuilder = Context.newBuilder("wasm");
        Source.Builder sourceBuilder = Source.newBuilder("wasm", ByteSequence.create(binary), "fibonacci");
        Source source = sourceBuilder.build();
        Context context = contextBuilder.build();

        context.eval(source);

        Value mainFunction = context.getBindings("wasm").getMember("main").getMember("_start");
        mainFunction.execute();
    }
}
