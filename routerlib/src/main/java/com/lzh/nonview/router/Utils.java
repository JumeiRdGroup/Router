package com.lzh.nonview.router;

import android.text.TextUtils;

public class Utils {

    /**
     * Adjust if the scheme is http or https
     * @param scheme scheme for uri
     * @return return true if is http or https
     */
    public static boolean isHttp (String scheme) {
        return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https");
    }

    /**
     * Check if the class is available with clzName
     * @param clzName class name
     * @return return true if the clz name is supported
     */
    public static boolean isClassSupport (String clzName) {
        try {
            Class.forName(clzName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static String wrapScheme (String scheme) {
        if (TextUtils.isEmpty(scheme) || !scheme.endsWith("/")) return scheme;

        return scheme.substring(0,scheme.lastIndexOf("/"));
    }

    public static String unwrapScheme (String scheme) {
        return scheme + "/";
    }
}
