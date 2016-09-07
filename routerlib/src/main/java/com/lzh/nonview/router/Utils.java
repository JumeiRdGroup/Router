package com.lzh.nonview.router;

import java.util.List;

/**
 * Created by admin on 16/9/5.
 */
public class Utils {

    public static boolean isHttp (String scheme) {
        return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https");
    }

    public static boolean isEmpty (String data) {
        return data == null || data.isEmpty();
    }

    public static boolean isListEmpty (List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isClassSupport (String clzName) {
        try {
            Class.forName(clzName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
