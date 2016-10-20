package com.lzh.nonview.router.compiler.util;

/**
 * Created by admin on 16/10/18.
 */

public class LogUtil {

    public static boolean debug = false;

    public static void print (Object message) {
        if (debug && message != null) {
            System.out.println(message);
        }
    }

    public static void printException (Throwable e) {
        if (debug) {
            e.printStackTrace();
        }
    }

    public static void printError (String message) {
        if (debug) {
            System.err.println(message);
        }
    }
}
