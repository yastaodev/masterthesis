package com.yast;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;

public class Main {

    @CEntryPoint(name = "distance")
    private static double distance(IsolateThread thread, double a_laF) {
        return 6;
    }
}