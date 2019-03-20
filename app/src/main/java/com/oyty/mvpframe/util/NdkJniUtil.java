package com.oyty.mvpframe.util;


import com.oyty.mvpframe.base.JNI;

public class NdkJniUtil {
    public static String get() {
        return JNI.stringFromJNI();
    }
}
