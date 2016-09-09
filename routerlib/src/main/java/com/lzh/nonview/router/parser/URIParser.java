package com.lzh.nonview.router.parser;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    void parse() {
        scheme = this.uri.getScheme();
        host = this.uri.getHost() + this.uri.getPath();
        params = parseParams();
    }

    Map<String,String> parseParams() {
        Map<String,String> params = new HashMap<>();
        Set<String> names = uri.getQueryParameterNames();
        for (String name : names) {
            params.put(name,uri.getQueryParameter(name));
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
