package com.lzh.nonview.router.compiler.model;

import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.nonview.router.compiler.Constants;
import com.lzh.nonview.router.compiler.util.UtilMgr;
import com.lzh.nonview.router.compiler.util.Utils;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class BasicConfigurations {
    String baseUrl;
    String pack;

    public BasicConfigurations(RouteConfig config) {
        if (config == null) return;

        this.pack = config.pack();
        this.baseUrl = parseBaseUrl(config);
    }

    private String parseBaseUrl(RouteConfig config) {
        if (!Utils.isEmpty(config.baseUrl())) {
            return config.baseUrl();
        }

        if (!Utils.isEmpty(config.schema())) {
            return config.schema() + "://";
        }
        return "";
    }
}
