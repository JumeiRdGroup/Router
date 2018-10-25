package com.lzh.nonview.router.compiler.model;

import com.lzh.nonview.router.anno.RouteExecutor;
import com.lzh.nonview.router.compiler.Constants;
import com.lzh.nonview.router.compiler.exception.RouterException;
import com.lzh.nonview.router.compiler.util.UtilMgr;
import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

public class Parser {
    private static Map<String,TypeElement> parsed = new HashMap<>();
    private String[] routers;
    private TypeElement type;
    private RouteRuleConfig configurations;
    private ClassName executorClass;

    public static Parser create (TypeElement element, RouteRuleConfig configurations) {
        Parser parser = new Parser();
        parser.type = element;
        parser.configurations = configurations;
        parser.executorClass = parser.obtainExecutor();
        return parser;
    }

    private ClassName obtainExecutor() {
        RouteExecutor annotation = type.getAnnotation(RouteExecutor.class);
        if (annotation == null) {
            return ClassName.bestGuess(Constants.CLASSNAME_MAINTHREADEXECUTOR);
        }
        try {
            return ClassName.get(annotation.value());
        } catch (MirroredTypeException mirrored) {
            TypeMirror typeMirror = mirrored.getTypeMirror();
            return ClassName.get((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(typeMirror));
        }
    }

    public void parse () {
        routers = configurations.getRoute();
        for (int i = 0; i < routers.length; i++) {
            String route = routers[i];
            checkIsDuplicate(route);
        }
    }

    private void checkIsDuplicate(String route) {
        if (route.endsWith("/")) {
            route = route.substring(0,route.lastIndexOf("/"));
        }

        if (parsed.containsKey(route)) {
            throw new RouterException(String.format("A same scheme was double defined on another class %s", parsed.get(route)),type);
        }
        parsed.put(route,type);
    }

    public String[] getScheme() {
        return routers;
    }

    public TypeElement getType() {
        return type;
    }

    public ClassName getExecutorClass() {
        return executorClass;
    }

    public RouteRuleConfig getConfigurations() {
        return configurations;
    }
}

