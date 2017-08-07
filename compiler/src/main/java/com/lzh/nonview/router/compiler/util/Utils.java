package com.lzh.nonview.router.compiler.util;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.compiler.Constants;
import com.lzh.nonview.router.compiler.exception.RouterException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Created by admin on 16/10/20.
 */

public class Utils {

    /**
     * check out if the class are an effective class;
     * <p>
     *     <i>should not be modified by abstract,if set,should be skip</i><br>
     *     <i>should not be modified by private,if set,should lead to crash</i><br>
     *     <i>should be subclass of <i><b>android.app.Activity</b></i>,if not,should lead to crash</i><br>
     * </p>
     * @param type A element of class
     * @return true if it is a effective class
     */
    public static boolean checkTypeValid (TypeElement type) {
        Set<Modifier> modifiers = type.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)) {
            throw new RouterException(String.format("The class %s should not be modified by private",type.getSimpleName()),type);
        } else if (modifiers.contains(Modifier.ABSTRACT)) {
            // skip it
            return false;
        } else if (!isSuperClass(type, Constants.CLASSNAME_ACTIVITY)
                && !isSuperClass(type, Constants.CLASSNAME_ACTION_SUPPORT)) {
            throw new RouterException(String.format("The class %s you annotated by RouterRule should be a subclass of Activity or ActionSupport",type.getSimpleName()),type);
        }
        return true;
    }

    /**
     * Check out if the class {@code type} is a subclass of {@code superClass}
     * @param type the class to check
     * @param superClassName the super class name
     * @return true if is subclass
     */
    public static boolean isSuperClass (TypeElement type,String superClassName) {
        if (type == null) {
            return false;
        }

        do {
            type = (TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(type.getSuperclass());
            if (type.getQualifiedName().toString().equals(superClassName)) {
                return true;
            }
            if ("java.lang.Object".equals(type.getQualifiedName().toString())) {
                return false;
            }
        } while (true);
//        while (ClassName.get(superclass))

//        return !(type == null || "java.lang.Object".equals(type.getQualifiedName().toString()))
//                && (type.getQualifiedName().toString().equals(superClassName)
//                        || isSuperClass((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(type.getSuperclass()), superClassName));
    }

    public static String getKeyFromArg(Arg arg, String def) {
        return isEmpty(arg.value()) ? def : arg.value();
    }

    public static boolean isEmpty (String data) {
        return data == null || data.length() == 0;
    }

    public static boolean isEffectType (VariableElement field) {
        String type = field.asType().toString();
        switch (type) {
            case "boolean":
            case "java.lang.Boolean":
            case "byte":
            case "java.lang.Byte":
            case "char":
            case "java.lang.Character":
            case "short":
            case "java.lang.Short":
            case "int":
            case "java.lang.Integer":
            case "long":
            case "java.lang.Long":
            case "float":
            case "java.lang.Float":
            case "double":
            case "java.lang.Double":
            case "java.lang.String":
            case "java.util.ArrayList<java.lang.String>":
            case "java.util.ArrayList<java.lang.Integer>":
                return true;
        }
        return false;
    }
}
