package com.demo;

import java.util.Objects;

public class AboutString {

    public static void RunDemo() {
        // These two have the same value
        var x = new String("test").equals("test"); // --> true

        // ... but they are not the same object
        var x1 = new String("test") == "test"; // --> false

        // ... neither are these
        var x2 = new String("test") == new String("test"); // --> false

        // ... but these are because literals are interned by
        // the compiler and thus refer to the same object
        var x3 = "test" == "test"; // --> true

        // ... string literals are concatenated by the compiler
        // and the results are interned.
        var x4 = "test" == "te" + "st"; // --> true


        // ... but you should really just call Objects.equals()
        var x5 = Objects.equals("test", new String("test")); // --> true
        var x6 = Objects.equals(null, "test"); // --> false
        var x7 = Objects.equals(null, null); // --> true
    }
}
