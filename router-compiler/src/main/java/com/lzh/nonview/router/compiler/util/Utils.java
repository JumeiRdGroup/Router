package com.lzh.nonview.router.compiler.util;

import com.lzh.nonview.router.compiler.exception.RouterException;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class Utils {

    /**
     * Check out if the class are an effective class;
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
        } else return !modifiers.contains(Modifier.ABSTRACT);
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
    }

    public static boolean isEmpty (String data) {
        return data == null || data.length() == 0;
    }
}
