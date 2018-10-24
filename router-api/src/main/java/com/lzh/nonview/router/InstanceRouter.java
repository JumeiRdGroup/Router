package com.lzh.nonview.router;

import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.module.CreatorRouteRule;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.route.ICreatorInjector;
import com.lzh.nonview.router.tools.Cache;
import com.lzh.nonview.router.tools.RouterLog;
import com.lzh.nonview.router.tools.Utils;

import java.util.Map;

/**
 * @author haoge on 2018/5/25
 */
public class InstanceRouter {

    private Uri uri;
    private Bundle extra = new Bundle();

    private InstanceRouter(Uri uri) {
        this.uri = uri;
    }

    static InstanceRouter build(String url) {
        return new InstanceRouter(Uri.parse(url));
    }

    public InstanceRouter addExtras(Bundle extra){
        if (extra != null) {
            this.extra.putAll(extra);
        }
        return this;
    }

    public <T> T createInstance() {
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

            if (instance instanceof ICreatorInjector) {
                Bundle bundle = Utils.parseRouteMapToBundle(parser, rule);
                ((ICreatorInjector) instance).inject(bundle);
            }

            return (T) instance;
        } catch (Throwable e) {
            RouterLog.e("Create target class from InstanceRouter failed. cause by:" + e.getMessage(), e);
            return null;
        }
    }
}
