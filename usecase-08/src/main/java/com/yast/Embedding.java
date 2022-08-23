package com.yast;

// host java

import com.oracle.truffle.espresso.polyglot.Polyglot;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



public class Embedding {
    public static void main(String[] args) {
        Context context = Context.newBuilder()
                .option("java.JavaHome","/opt/work/tools/sdkman/candidates/java/8.0.242.hs-adpt/")
                .allowExperimentalOptions(true)
                .allowAllAccess(true).build();

        Value org_omg_corba_principal = context.getBindings("java").getMember("org.omg.CORBA.Principal");

        Value java_lang_Class = context.getBindings("java").getMember("java.lang.Class");


        /*-------------------- schon mal gut classArray.getMember("class").invokeMember("getName") = [Ljava.lang.Class;---------------------
        * 
        * YEEEEEEEEEEEEEEEEEEEEEEEEEEEEY classArray.newInstance(5).getArraySize() returns 5
        * 
        * */
// Class#forName(String) returns the Class<Integer> instance.
        Value string_class = java_lang_Class.invokeMember("forName", "java.lang.String");
        Value java_lang_string = context.getBindings("java").getMember("java.lang.String");

        Value objectArray = context.getBindings("java").getMember("[Ljava.lang.Object;");

        Value classArray = context.getBindings("java").getMember("[Ljava.lang.Class;");
        Value classArrayInstance = classArray.newInstance(1);
        classArrayInstance.setArrayElement(0, string_class);


        Value stringArray = context.getBindings("java").getMember("[Ljava.lang.String;");
        Value stringArrayInstance = stringArray.newInstance(1);
        stringArrayInstance.setArrayElement(0, "Yasser Taoufiq");


        Value stringConstructor = java_lang_string.getMember("class").invokeMember("getDeclaredConstructor", classArrayInstance);
        String yassertaoufiq = stringConstructor.invokeMember("newInstance", stringArrayInstance).asString();

/*
        // Class loading is exposed through language bindings, with class
        // names using the same format as Class#forName(String).
//        Value intArray = polyglot.getBindings("java").getMember("[I");
//        Value objectArray = polyglot.getBindings("java").getMember("[Ljava.lang.Object");
//        Value java_lang_Math = polyglot.getBindings("java").getMember("java.lang.Math");
//        double sqrt2 = java_lang_Math.invokeMember("sqrt", 2).asDouble();
//        double pi = java_lang_Math.getMember("PI").asDouble();
//        System.out.println(sqrt2);
//        System.out.println(pi);
//
//        // host java
//        Value java_lang_String = polyglot.getBindings("java").getMember("java.lang.String");
//// String#valueOf(int)
//        String valueOf = String.format("%s/%s", "valueOf", "(I)Ljava/lang/String;");
//        Value fortyTwo = java_lang_String.invokeMember(valueOf, 42);
//        assert "42".equals(fortyTwo.asString());

// Class#forName(String) returns the Class<Integer> instance.
        //Value integer_class = java_lang_Class.invokeMember("forName", "java.lang.Integer");

// Static class accessor to Class<?> instance and viceversa.
        //assert integer_class.equals(java_lang_Integer.getMember("class"));
        //assert java_lang_Integer.equals(integer_class.getMember("static"));


//        Value java_lang_integer = polyglot.getBindings("java").getMember("java.lang.Integer");
//        java_lang_integer.newInstance();
//
//        Value myclass = java_lang_String.newInstance(fortyTwo);
//
//        try {
//            Value java_io_InputStream = polyglot.getBindings("java").getMember("java.io.InputStream");
//            java_io_InputStream.newInstance();
//
//            Value java_io_file = polyglot.getBindings("java").getMember("java.io.File");
//            java_io_file.newInstance("/opt/work/workspaces/idea/master-thesis/usecase-08/README");
//
//
//
//            Value java_io_fileinputstream = polyglot.getBindings("java").getMember("java.io.FileInputStream");
//
//            Value fileInputStream = java_io_fileinputstream.newInstance("/opt/work/workspaces/idea/master-thesis/usecase-08/README");
//            //fileInputStream = new FileInputStream("/opt/work/workspaces/idea/master-thesis/usecase-08/README");
//            //Runtime.getRuntime().getLocalizedInputStream(fileInputStream);
            Value java_lang_runtime = polyglot.getBindings("java").getMember("java.lang.Runtime");
            Value runtime = java_lang_runtime.invokeMember("getRuntime");
        java_lang_runtime.getMember("class").invokeMember("isEnum/()Z");
//            runtime.invokeMember("getLocalizedInputStream", fileInputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // guest java
        Object foreign = polyglot.eval("js", "[2, 0, 2, 1]");
        Object local = new int[]{2, 0, 2, 1};
        System.out.println(Polyglot.isForeignObject(foreign)); // prints true
        System.out.println(Polyglot.isForeignObject(local));   // prints false*/

    }




    public static void printHostJavaInfo() {
        printJavaInfo(System.getProperty("java.version"), System.getProperty("java.vm.name"));
    }

    public static void printGuestJavaInfo(Context context) {
        Value java_lang_system = context.getBindings("java").getMember("java.lang.System");
        Value javaName = java_lang_system.invokeMember("getProperty", "java.vm.name");
        Value javaVersion = java_lang_system.invokeMember("getProperty", "java.version");
        printJavaInfo(javaName.asString(), javaVersion.asString());
    }

    private static void printJavaInfo(String name, String version) {
        System.out.println("Name:\t" + name);
        System.out.println("Version:\t" + version);
    }
}