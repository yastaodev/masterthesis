package com.yast;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Java8Utils {

    private static final Context context = buildContext();
    private static final Value java_lang_Class = getJava8Class("java.lang.Class");
    private static final Value java_lang_String = getJava8Class("java.lang.String");
    private static final Value java_lang_Thread = getJava8Class("java.lang.Thread");
    private static final Value javax_xml_bind_DatatypeConverter = getJava8Class("javax.xml.bind.DatatypeConverter");
    private static final Value java_lang_String_ArrayClass = getJava8ArrayClass("java.lang.String");
    private static final Value java_lang_Class_ArrayClass = getJava8ArrayClass("java.lang.Class");

    public static boolean parseBoolean(String string) {
        return javax_xml_bind_DatatypeConverter.invokeMember("parseBoolean", string).as(Boolean.class);
    }

    public static double parseDouble(String string) {
        return javax_xml_bind_DatatypeConverter.invokeMember("parseDouble", string).as(Double.class);
    }

    public static String printHexBinary(byte[] val) {
        return javax_xml_bind_DatatypeConverter.invokeMember("printHexBinary", val).asString();
    }

    public static Value createJava8Thread() {
        Value thread = java_lang_Thread.newInstance();
        System.out.println("Thread created: " + thread);
        return thread;
    }

    public static void startJava8Thread(Value thread) {
        thread.invokeMember("start");
        System.out.println("Thread started: " + thread);
    }

    public static void stopJava8Thread(Value thread, Value throwable) {
        System.out.println("Trying to stop thread: " + thread);
        thread.invokeMember("stop", throwable);
    }

    public static void destroyJava8Thread(Value thread) {
        System.out.println("Trying to destroy thread: " + thread);
        thread.invokeMember("destroy");
    }

    public static Value createJava8StringUsingDeclaredConstructor(String string) {
        Value classArrayInstance = java_lang_Class_ArrayClass.newInstance(1);
        Value stringClass = java_lang_Class.invokeMember("forName", "java.lang.String");
        classArrayInstance.setArrayElement(0, stringClass);

        Value stringArrayInstance = java_lang_String_ArrayClass.newInstance(1);
        stringArrayInstance.setArrayElement(0, string);

        Value stringConstructor = java_lang_String.getMember("class").invokeMember("getDeclaredConstructor", classArrayInstance);
        return stringConstructor.invokeMember("newInstance", stringArrayInstance);
    }

    public static void printHostJavaInfo() {
        printJavaInfo(System.getProperty("java.version"), System.getProperty("java.vm.name"));
    }

    public static void printGuestJavaInfo() {
        Value java_lang_system = context.getBindings("java").getMember("java.lang.System");
        Value javaName = java_lang_system.invokeMember("getProperty", "java.vm.name");
        Value javaVersion = java_lang_system.invokeMember("getProperty", "java.version");
        printJavaInfo(javaName.asString(), javaVersion.asString());
    }

    private static void printJavaInfo(String name, String version) {
        System.out.println("Name:\t" + name);
        System.out.println("Version:\t" + version);
    }

    private static Value getJava8Class(String classQualifiedName) {
        return context.getBindings("java").getMember(classQualifiedName);
    }

    private static Value getJava8ArrayClass(String classQualifiedName) {
        return context.getBindings("java").getMember("[L" + classQualifiedName + ";");
    }

    private static Context buildContext() {
        return Context.newBuilder()
                .option("java.JavaHome", "/opt/work/tools/sdkman/candidates/java/8.0.242.hs-adpt/")
                .option("java.MultiThreaded", "true")
                .allowExperimentalOptions(true)
                .allowAllAccess(true)
                .build();
    }
}
