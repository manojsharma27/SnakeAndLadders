package com.ms.util;

public class LogUtil {

    private static final boolean ENABLE_LOGGING = false;

    public static void logConditional(String msg, Object... args) {
        if (ENABLE_LOGGING) {
            System.out.printf(msg, args);
        }
    }

    public static void log(String msg, Object... args) {
        System.out.printf(msg, args);
    }
}
