package com.lzh.nonview.router;

import android.net.Uri;
import android.os.Bundle;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.nonview.router.module.CreatorRouteRule;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.tools.Cache;
import com.lzh.nonview.router.tools.RouterLog;
import com.lzh.nonview.router.tools.Utils;

import java.util.Map;

/**
 * @author haoge on 2018/5/25
 */
public class CreatorRouter {

    Uri uri;
    Bundle extra = new Bundle();

    private CreatorRouter(Uri uri) {
        this.uri = uri;
    }

    public static CreatorRouter create(String url) {
        return new CreatorRouter(Uri.parse(url));
    }

    public static CreatorRouter create(Uri uri) {
        return new CreatorRouter(uri);
    }

    public CreatorRouter addExtras(Bundle extra){
        if (extra != null) {
            this.extra.putAll(extra);
        }
        return this;
    }

    public <T> T createTarget() {
        try {
            Map<String, CreatorRouteRule> rules = Cache.getCreatorRules();
            URIParser parser = new URIParser(uri);
            String route = parser.getRoute();
            CreatorRouteRule rule = rules.get(route);
            if (rule == null) {
                RouterLog.d("Could not match rule for this uri");
                return null;
            }

            Object instance = rule.getTarget().newInstance();

            Bundle bundle = Utils.parseRouteMapToBundle(parser, rule);
            if (Utils.PARCELER_SUPPORT) {
                Parceler.toEntity(instance, bundle);
            }

            return (T) instance;
        } catch (Throwable e) {
            RouterLog.e("Create target class from CreatorRouter failed. cause by:" + e.getMessage(), e);
            return null;
        }
    }
}
