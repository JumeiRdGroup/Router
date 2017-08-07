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
    ClassName activityLauncher;
    ClassName actionLauncher;

    public BasicConfigurations(RouteConfig config) {
        if (config == null) return;

        this.pack = config.pack();
        this.baseUrl = parseBaseUrl(config);
        parseLauncher(config);
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

    private void parseLauncher(RouteConfig config) {
        if (config == null) {
            return;
        }
        ClassName activity = activity(config);
        ClassName action = action(config);
        ClassName aVoid = ClassName.get(Void.class);
        Elements utils = UtilMgr.getMgr().getElementUtils();
        if (!aVoid.equals(activity) && !Utils.isSuperClass(utils.getTypeElement(activity.toString()),
                    Constants.CLASSNAME_ACTIVITY_LAUNCHER)) {
            throw new IllegalArgumentException(String.format(
                    "The class you set via method #activityLauncher() should be a subclass of %s", Constants.CLASSNAME_ACTIVITY_LAUNCHER));
        }
        if (!aVoid.equals(action) && !Utils.isSuperClass(utils.getTypeElement(action.toString()),
                Constants.CLASSNAME_ACTION_LAUNCHER)) {
            throw new IllegalArgumentException(String.format(
                    "The class you set via method #actionLauncher() should be a subclass of %s", Constants.CLASSNAME_ACTIVITY_LAUNCHER));
        }
        activityLauncher = aVoid.equals(activity) ? null : activity;
        actionLauncher = aVoid.equals(action) ? null : action;
    }

    private ClassName action(RouteConfig config) {
        try {
            return ClassName.get(config.actionLauncher());
        } catch (MirroredTypeException mirrored) {
            TypeMirror typeMirror = mirrored.getTypeMirror();
            return ClassName.get((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(typeMirror));
        }
    }

    private ClassName activity(RouteConfig config) {
        try {
            return ClassName.get(config.activityLauncher());
        } catch (MirroredTypeException mirrored) {
            TypeMirror typeMirror = mirrored.getTypeMirror();
            return ClassName.get((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(typeMirror));
        }
    }
}
