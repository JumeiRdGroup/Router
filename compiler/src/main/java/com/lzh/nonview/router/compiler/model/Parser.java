package com.lzh.nonview.router.compiler.model;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.compiler.exception.RouterException;
import com.lzh.nonview.router.compiler.factory.RuleFactory;
import com.lzh.nonview.router.compiler.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class Parser {
    private static Map<String,TypeElement> parsed = new HashMap<>();
    private String scheme;
    private TypeElement type;
    private Map<String,TypeMirror> map = new HashMap<>();

    public static Parser create (TypeElement element) {
        Parser parser = new Parser();
        parser.type = element;
        return parser;
    }

    public void parse () {
        parseEffectField (type);
        checkIsDuplicate();
    }

    private void checkIsDuplicate() {
        scheme = type.getAnnotation(RouterRule.class).value();
        if (Utils.isEmpty(scheme)) {
            throw new RouterException("value of annotation RouteRule can not be null!",type);
        }
        if (scheme.endsWith("/")) {
            scheme = scheme.substring(0,scheme.lastIndexOf("/"));
        }

        if (parsed.containsKey(scheme)) {
            throw new RouterException(String.format("A same scheme was double defined on another class %s", parsed.get(scheme)),type);
        }
        parsed.put(scheme,type);
    }

    private void parseEffectField(TypeElement type) {
        List<? extends Element> elements = type.getEnclosedElements();
        for (Element ele : elements) {
            if (ele.getKind() != ElementKind.FIELD) continue;
            // get field annotated by Arg
            if (!Utils.isEffectType((VariableElement) ele)) continue;

            Arg arg = ele.getAnnotation(Arg.class);
            if (arg == null) continue;

            String key = Utils.getKeyFromArg(arg,ele.getSimpleName().toString());
            map.put(key,ele.asType());
        }
    }

    public Map<String, TypeMirror> getMap() {
        return map;
    }

    public String getScheme() {
        return scheme;
    }

    public TypeElement getType() {
        return type;
    }
}

