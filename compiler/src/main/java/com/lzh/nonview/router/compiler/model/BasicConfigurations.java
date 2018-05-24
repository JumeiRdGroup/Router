package com.lzh.nonview.router.compiler.model;

import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.nonview.router.compiler.util.Utils;

public class BasicConfigurations {
    public String baseUrl;
    public String pack = "com.lzh.router";

    public BasicConfigurations(RouteConfig config) {
        if (config == null) return;

        if (!Utils.isEmpty(config.pack())) {
            this.pack = config.pack();
        }
        this.baseUrl = parseBaseUrl(config);
    }

    private String parseBaseUrl(RouteConfig config) {
        if (!Utils.isEmpty(config.baseUrl())) {
            return config.baseUrl();
        }
        return "";
    }
}
