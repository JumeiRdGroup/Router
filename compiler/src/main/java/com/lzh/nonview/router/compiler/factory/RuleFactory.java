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

public class RuleFactory {

    private ClassName clzName;
    private List<Parser> parserList;

    public RuleFactory(ClassName name, List<Parser> parserList) {
        this.clzName = name;
        this.parserList = parserList;
    }

    public void generateCode () throws IOException {
        ClassName creator = Utils.getClassName(Constants.CLASSNAME_ROUTE_CREATOR);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(clzName)
                .addSuperinterface(Utils.getClassName(Constants.CLASSNAME_ROUTE_CREATOR))
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder methodCreator = MethodSpec.overriding(getOverrideMethod (creator));
        methodCreator.addStatement("$T<String,RouteMap> routes = new $T<>()",Map.class, HashMap.class);
        for (Parser parser : parserList ) {
            appendMethod(parser,methodCreator);
        }
        methodCreator.addStatement("return routes");
        typeBuilder.addMethod(methodCreator.build());
        JavaFile.Builder javaBuilder = JavaFile.builder(clzName.packageName(), typeBuilder.build());
        javaBuilder.build().writeTo(UtilMgr.getMgr().getFiler());
    }

    private void appendMethod(Parser parser, MethodSpec.Builder methodCreator) {
        Map<String, TypeName> map = parser.getMap();
        String target = parser.getType().getQualifiedName().toString();
        TypeElement actType = UtilMgr.getMgr().getElementUtils().getTypeElement(target);
        CodeBlock.Builder codeBuilder = CodeBlock.builder().add("routes.put($S,new $T($T.class)",
                parser.getScheme(), Utils.getClassName(Constants.CLASSNAME_ROUTE_MAP), actType);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            codeBuilder.add(".addParam($S,$L)",key,getTypeFromName (map.get(key)));
        }
        codeBuilder.addStatement(");");
        methodCreator.addCode(codeBuilder.build());
    }

    private String getTypeFromName(TypeName name) {
        /*
        * public static final int STRING = -1;
    public static final int BYTE = 0;
    public static final int SHORT = 1;
    public static final int INT = 2;
    public static final int LONG = 3;
    public static final int FLOAT = 4;
    public static final int DOUBLE = 5;
    public static final int BOOLEAN = 6;
    public static final int CHAR = 7;
        * */
        switch (name.toString()) {
            case "boolean":
            case "java.lang.Boolean":
                return "RouteMap.BOOLEAN";
            case "byte":
            case "java.lang.Byte":
                return "RouteMap.BYTE";
            case "char":
            case "java.lang.Character":
                return "RouteMap.CHAR";
            case "short":
            case "java.lang.Short":
                return "RouteMap.SHORT";
            case "int":
            case "java.lang.Integer":
                return "RouteMap.INT";
            case "long":
            case "java.lang.Long":
                return "RouteMap.LONG";
            case "float":
            case "java.lang.Float":
                return "RouteMap.FLOAT";
            case "double":
            case "java.lang.Double":
                return "RouteMap.DOUBLE";
            case "java.lang.String":
                return "RouteMap.STRING";
        }
        return "RouteMap.STRING";
    }

    private ExecutableElement getOverrideMethod(ClassName creator) {
        TypeElement element = UtilMgr.getMgr().getElementUtils().getTypeElement(creator.toString());
        List<? extends Element> elements = element.getEnclosedElements();
        for (Element ele : elements) {
            if (ele.getKind() != ElementKind.METHOD) continue;
            if (Constants.METHODNAME_CREATE_ROUTER_CREATOR.equals(ele.getSimpleName().toString())) {
                return (ExecutableElement) ele;
            }
        }
        throw new RuntimeException("method createRouteRules of interface RouteCreator not found");
    }
}
