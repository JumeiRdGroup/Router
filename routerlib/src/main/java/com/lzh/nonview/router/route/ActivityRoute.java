package com.lzh.nonview.router.route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.URIParser;

import java.util.Map;
import java.util.Set;

/**
 * A route tool to check route rule by uri and launch activity
 * Created by lzh on 16/9/5.
 */
public class ActivityRoute implements IActivityRoute, IRoute {
    /**
     * Uri to open
     */
    private Uri uri;
    /**
     * The bundle data that contains all of data parsed by uri
     */
    private Bundle bundle;
    /**
     * A entity to contains some extra data exclude uri parse
     */
    private ActivityRouteBundleExtras extras;
    /**
     * A routeMap entity that associated with uri
     */
    private RouteMap routeMap = null;
    /**
     * route callback,will not be null
     */
    private RouteCallback callback;

    public void setCallback (RouteCallback callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    @Override
    public IActivityRoute replaceBundleExtras(ActivityRouteBundleExtras extras) {
        if (extras == null) {
            extras = new ActivityRouteBundleExtras();
        }
        this.extras = extras;
        return this;
    }

    @Override
    public void open(Context context, Uri uri) {
        try {
            if (callback.interceptOpen(uri,context,extras)) return;

            ActivityRoute route = (ActivityRoute) getRoute(uri);
            route.openInternal(context);

            callback.onOpenSuccess(uri,routeMap.getClzName());
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(uri,e);
            }
        }
    }

    @Override
    public boolean canOpenRouter(Uri uri) {
        return getRouteMapByUri(new URIParser(uri)) != null;
    }

    /**
     * find route by scheme of URIParser.
     * @param parser uri parser
     * @return routeMap associate with scheme of parser
     */
    private RouteMap getRouteMapByUri (URIParser parser) {
        String route = parser.getScheme() + "://" + parser.getHost();
        Map<String, RouteMap> routes = RouteManager.INSTANCE.getRouteMap();
        String wrap = Utils.wrapScheme(route);
        if (routes.containsKey(wrap)) {
            return routes.get(wrap);
        }
        String unWrap = Utils.unwrapScheme(wrap);
        return routes.get(unWrap);
    }

    @Override
    public IRoute getRoute(Uri uri) {
        this.uri = uri;
        this.extras = new ActivityRouteBundleExtras();

        URIParser parser = new URIParser(uri);
        routeMap = getRouteMapByUri(parser);
        Map<String, Integer> keyMap = routeMap.getParams();

        bundle = new Bundle();
        Map<String, String> params = parser.getParams();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            Integer type = keyMap.get(key);
            putExtraByType(bundle, params, key, type == null ? RouteMap.STRING : type);
        }
        return this;
    }

    private static void putExtraByType(Bundle extras, Map<String, String> params, String key, int type) {
        // when not set this key in router.json,reset type to string
        switch (type) {
            case RouteMap.BYTE:
                extras.putByte(key,Byte.parseByte(params.get(key)));
                break;
            case RouteMap.SHORT:
                extras.putShort(key,Short.parseShort(params.get(key)));
                break;
            case RouteMap.INT:
                extras.putInt(key,Integer.parseInt(params.get(key)));
                break;
            case RouteMap.LONG:
                extras.putLong(key,Long.parseLong(params.get(key)));
                break;
            case RouteMap.FLOAT:
                extras.putFloat(key,Float.parseFloat(params.get(key)));
                break;
            case RouteMap.DOUBLE:
                extras.putDouble(key,Double.parseDouble(params.get(key)));
                break;
            case RouteMap.CHAR:
                extras.putChar(key,params.get(key).charAt(0));
                break;
            case RouteMap.BOOLEAN:
                extras.putBoolean(key,Boolean.parseBoolean(params.get(key)));
                break;
            case RouteMap.STRING:
            default://string
                extras.putString(key,params.get(key));
                break;
        }
    }

    @Override
    public void open(Context context) {
        try {
            if (callback.interceptOpen(uri,context,extras)) return;
            openInternal(context);
            callback.onOpenSuccess(uri,routeMap.getClzName());
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(this.uri,e);
            }
        }
    }

    public Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context,routeMap.getClzName());
        intent.putExtras(bundle);
        intent.putExtras(extras.extras);
        intent.addFlags(extras.flags);
        return intent;
    }

    private void openInternal(Context context) {
        String clzName = routeMap.getClzName();
        if (!Utils.isClassSupport(clzName)) {
            throw new NotFoundException(String.format("target activity is not found : %s",clzName), NotFoundException.NotFoundType.CLZ,clzName);
        }
        Intent intent = createIntent(context);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent,extras.requestCode);
            int inAnimation = extras.inAnimation;
            int outAnimation = extras.outAnimation;
            if (inAnimation >= 0 && outAnimation >= 0) {
                ((Activity) context).overridePendingTransition(inAnimation,outAnimation);
            }
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        this.extras.requestCode = requestCode;
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        this.extras.inAnimation = enterAnim;
        this.extras.outAnimation = exitAnim;
        return this;
    }

    @Override
    public IActivityRoute addExtras(Bundle extras) {
        if (extras != null) {
            this.extras.extras.putAll(extras);
        }
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        this.extras.flags |= flag;
        return this;
    }

}
