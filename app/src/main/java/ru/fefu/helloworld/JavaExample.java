package ru.fefu.helloworld;

public class JavaExample {

    private String s;

    void method() {
        String c = null;
        if (s != null) {
            c = String.valueOf(s.charAt(0));
        }
    }

}
