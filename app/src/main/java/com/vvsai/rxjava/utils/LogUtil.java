package com.vvsai.rxjava.utils;

import android.util.Log;

/**
 * Created by lychee on 2015/12/1.
 */
public class LogUtil {
    private static boolean LOG = true;

    public static void v(String mess) {
        if (LOG) {
            Log.v(getTag(), mess);
        }
    }

    public static void d(String mess) {
        if (LOG) {
            Log.d(getTag(), mess);
        }
    }

    public static void i(String mess) {
        if (LOG) {
            Log.i(getTag(), mess);
        }
    }

    public static void w(String mess) {
        if (LOG) {
            Log.w(getTag(), mess);
        }
    }

    public static void e(String mess) {
        if (LOG) {
            Log.e(getTag(), mess);
        }
    }

//    private static String getTag() {
//        StackTraceElement[] trace = new Throwable().fillInStackTrace()
//                .getStackTrace();
//        String callingClass = "";
//        for (int i = 2; i < trace.length; i++) {
//            Class<?> clazz = trace[i].getClass();
//            if (!clazz.equals(LogUtil.class)) {
//                callingClass = trace[i].getClassName();
//                callingClass = callingClass.substring(callingClass
//                        .lastIndexOf('.') + 1);
//                break;
//            }
//        }
//        return callingClass;
//    }

    private static String getTag() {
        return "lychee";
    }
}
