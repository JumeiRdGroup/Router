package com.lzh.nonview.router.compiler.factory;

import com.lzh.nonview.router.compiler.Constants;
import com.lzh.nonview.router.compiler.model.Parser;
import com.lzh.nonview.router.compiler.util.UtilMgr;
import com.lzh.nonview.router.compiler.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class RuleFactory {

    private ClassName clzName;
    private List<Parser> activityParser = new ArrayList<>();
    private List<Parser> actionParser = new ArrayList<>();
    private List<Parser> creatorParser = new ArrayList<>();
    private ClassName routeMap = ClassName.bestGuess(Constants.CLASSNAME_ROUTE_MAP);
    private ClassName activityRouteMap = ClassName.bestGuess(Constants.CLASSNAME_ACTIVITY_ROUTE_MAP);
    private ClassName actionRouteMap = ClassName.bestGuess(Constants.CLASSNAME_ACTION_ROUTE_MAP);
    private ClassName creatorRouteMap = ClassName.bestGuess(Constants.CLASSNAME_CREATOR_ROUTE_MAP);

    public RuleFactory(ClassName name, List<Parser> parserList) {
        this.clzName = name;
        for (Parser item :  parserList) {
            if (Utils.isSuperClass(item.getType(), Constants.CLASSNAME_ACTIVITY)) {
                activityParser.add(item);
            } else if (Utils.isSuperClass(item.getType(), Constants.CLASSNAME_ACTION_SUPPORT)){
                actionParser.add(item);
            } else {
                creatorParser.add(item);
            }
        }
    }

    public void generateCode () throws IOException {
        ClassName creator = ClassName.bestGuess(Constants.CLASSNAME_ROUTE_CREATOR);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(clzName)
                .addSuperinterface(creator)
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder methodActivityRulesCreator = MethodSpec.overriding(getOverrideMethod(creator, Constants.METHODNAME_CREATE_ACTIVITY_ROUTER));
        methodActivityRulesCreator.addStatement("$T<String,$T> routes = new $T<>()",Map.class, activityRouteMap, HashMap.class);
        for (Parser parser : activityParser ) {
            String[] schemaes = parser.getScheme();
            for (String schema : schemaes) {
                appendMethod(parser,methodActivityRulesCreator,schema, Constants.CLASSNAME_ACTIVITY_ROUTE_MAP);
            }
        }
        methodActivityRulesCreator.addStatement("return routes");

        MethodSpec.Builder methodActionRulesCreator = MethodSpec.overriding(getOverrideMethod(creator, Constants.METHODNAME_CREATE_ACTION_ROUTER));
        methodActionRulesCreator.addStatement("$T<String,$T> routes = new $T<>()",Map.class, actionRouteMap, HashMap.class);
        for (Parser parser : actionParser ) {
            String[] schemaes = parser.getScheme();
            for (String schema : schemaes) {
                appendMethod(parser,methodActionRulesCreator,schema, Constants.CLASSNAME_ACTION_ROUTE_MAP);
            }
        }
        methodActionRulesCreator.addStatement("return routes");

        MethodSpec.Builder methodCreatorRulesCreator = MethodSpec.overriding(getOverrideMethod(creator, Constants.METHODNAME_CREATE_CREATOR_ROUTER));
        methodCreatorRulesCreator.addStatement("$T<String,$T> routes = new $T<>()",Map.class, creatorRouteMap, HashMap.class);
        for (Parser parser : creatorParser ) {
            String[] schemaes = parser.getScheme();
            for (String schema : schemaes) {

                appendMethod(parser,methodCreatorRulesCreator,schema, Constants.CLASSNAME_CREATOR_ROUTE_MAP);
            }
        }
        methodCreatorRulesCreator.addStatement("return routes");

        typeBuilder.addMethod(methodActivityRulesCreator.build());
        typeBuilder.addMethod(methodActionRulesCreator.build());
        typeBuilder.addMethod(methodCreatorRulesCreator.build());
        JavaFile.Builder javaBuilder = JavaFile.builder(clzName.packageName(), typeBuilder.build());
        javaBuilder.build().writeTo(UtilMgr.getMgr().getFiler());
    }

    private void appendMethod(Parser parser, MethodSpec.Builder methodCreator,String schema, String className) {
        Map<String, TypeMirror> map = parser.getMap();
        String target = parser.getType().getQualifiedName().toString();
        TypeElement actType = UtilMgr.getMgr().getElementUtils().getTypeElement(target);
        CodeBlock.Builder codeBuilder;
        codeBuilder = CodeBlock.builder().add("routes.put($S,new $T($T.class)",
                schema, ClassName.bestGuess(className), actType);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            codeBuilder.add("\r\n\t\t");
            codeBuilder.add(".addParam($S,$T.$L)",key, routeMap, getTypeFromName (map.get(key)));
        }

        if (Constants.CLASSNAME_ACTION_ROUTE_MAP.equals(className)) {
            codeBuilder.add("\r\n\t\t");
            codeBuilder.add(".setExecutorClass($T.class)", parser.getExecutorClass());
        }

        if (parser.getConfigurations().getLauncher() != null) {
            codeBuilder.add("\r\n\t\t");
            codeBuilder.add(".setLauncher($T.class)", parser.getConfigurations().getLauncher());
        }

        // add interceptors
        TypeName[] interceptors = parser.getConfigurations().getInterceptors();
        if (interceptors.length != 0) {
            codeBuilder.add("\r\n\t\t");
            codeBuilder.add(".setInterceptors(");
            for (int i = 0; i < interceptors.length; i++) {
                TypeName interceptor = interceptors[i];
                if (i > 0) {
                    codeBuilder.add(",");
                }
                codeBuilder.add("$T.class", interceptor);
            }
            codeBuilder.add(")");
        }

        codeBuilder.addStatement(")");
        methodCreator.addCode(codeBuilder.build());
    }

    private String getTypeFromName(TypeMirror name) {
        switch (name.toString()) {
            case "boolean":
            case "java.lang.Boolean":
                return "BOOLEAN";
            case "byte":
            case "java.lang.Byte":
                return "BYTE";
            case "char":
            case "java.lang.Character":
                return "CHAR";
            case "short":
            case "java.lang.Short":
                return "SHORT";
            case "int":
            case "java.lang.Integer":
                return "INT";
            case "long":
            case "java.lang.Long":
                return "LONG";
            case "float":
            case "java.lang.Float":
                return "FLOAT";
            case "double":
            case "java.lang.Double":
                return "DOUBLE";
            case "java.lang.String":
                return "STRING";
        }
        return "STRING";
    }

    private ExecutableElement getOverrideMethod(ClassName creator, String methodName) {
        TypeElement element = UtilMgr.getMgr().getElementUtils().getTypeElement(creator.toString());
        List<? extends Element> elements = element.getEnclosedElements();
        for (Element ele : elements) {
            if (ele.getKind() != ElementKind.METHOD) continue;
            if (methodName.equals(ele.getSimpleName().toString())) {
                return (ExecutableElement) ele;
            }
        }
        throw new RuntimeException("method createRouteRules of interface RouteCreator not found");
    }
}
