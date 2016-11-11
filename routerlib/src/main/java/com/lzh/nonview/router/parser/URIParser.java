package com.lzh.nonview.router.parser;

import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 * A parser to parse uri to scheme/host/params .etc
 * Created by lzh on 16/9/5.
 */
public class URIParser {

    private Uri uri;
    private String scheme;
    private String host;
    private Map<String,String> params;

    public URIParser(Uri uri) {
        this.uri = uri;
        parse();
    }

    private void parse() {
        scheme = this.uri.getScheme();
        host = this.uri.getHost() + this.uri.getPath();
        if (host.endsWith("/")) {
            host = host.substring(0,host.lastIndexOf("/"));
        }
        String query = uri.getQuery();
        if (!TextUtils.isEmpty(query)) {
            params = parseParams(uri.getQuery());
        } else {
            params = new HashMap<>();
        }
    }

    /**
     * Parse params form query string
     * <p>
     * To support parse list to bundle,use {@link IdentityHashMap} to hold key-value
     * </p>
     * @param query query in uri
     * @return a map contains key-value data parsed by query in uri
     */
    static Map<String,String> parseParams(String query) {
        Map<String,String> params = new IdentityHashMap<>();
        String[] split = query.split("&");
        for (String param : split) {
            String[] keyValue = param.split("=");
            //noinspection RedundantStringConstructorCall
            params.put(new String(keyValue[0]),keyValue[1]);
        }
        return params;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
