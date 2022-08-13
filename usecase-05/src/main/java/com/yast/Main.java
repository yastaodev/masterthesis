package com.yast;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(Addition.add(1, 3));
        System.out.println(new Subtraction().subtract(3, 1));
        System.out.println(Multiplication.multiply(1, 3));
        //Callable fn = (Callable) callClojure("com.yast.division", "add");
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("com.yast.Division"));
        IFn add = Clojure.var("com.yast.Division", "divide");
        Object ox = add.invoke(6, 3);
        System.out.println("fn says " + ox);
        System.out.println("Hello world!");
    }

}