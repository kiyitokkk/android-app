package com.example.job.util;

public class Cyber {
    static {
        System.loadLibrary("fooLib");
    }
    public static native String hello1(String str1);
    public static native String hello2(String str2);
}
