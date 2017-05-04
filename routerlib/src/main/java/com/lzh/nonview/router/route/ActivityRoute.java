package com.lzh.nonview.router.route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.BundleWrapper;
import com.lzh.nonview.router.parser.ListBundle;
import com.lzh.nonview.router.parser.SimpleBundle;
import com.lzh.nonview.router.parser.URIParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
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
            Utils.checkInterceptor(uri,extras,context,getInterceptors());

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
        try {
            return RouteManager.get().getRouteMapByUri(new URIParser(uri)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public IRoute getRoute(Uri uri) {
        try {
            return getRouteInternal(uri);
        } catch (Exception e) {
            callback.onOpenFailed(uri,e);
            return EmptyActivityRoute.get();
        }
    }

    private IRoute getRouteInternal(Uri uri) {
        this.uri = uri;
        this.extras = new ActivityRouteBundleExtras();

        URIParser parser = new URIParser(uri);
        routeMap = RouteManager.get().getRouteMapByUri(parser);
        Map<String, Integer> keyMap = routeMap.getParams();

        bundle = new Bundle();
        Map<String, String> params = parser.getParams();
        Set<String> keySet = params.keySet();
        Map<String,BundleWrapper> wrappers = new HashMap<>();
        for (String key : keySet) {
            Integer type = keyMap.get(key);
            type = type == null ? RouteMap.STRING : type;

            BundleWrapper wrapper = wrappers.get(key);
            if (wrapper == null) {
                wrapper = createBundleWrapper(type);
                wrappers.put(key,wrapper);
            }
            wrapper.set(params.get(key));
        }
        keySet = wrappers.keySet();
        for (String key : keySet) {
            wrappers.get(key).put(bundle,key);
        }
        return this;
    }

    @Override
    public void open(Context context) {
        try {
            Utils.checkInterceptor(uri,extras,context,getInterceptors());
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
        intent.putExtras(extras.getExtras());
        intent.addFlags(extras.getFlags());
        return intent;
    }

    private void openInternal(Context context) {
        String clzName = routeMap.getClzName();
        if (!Utils.isClassSupport(clzName)) {
            throw new NotFoundException(String.format("target activity is not found : %s",clzName), NotFoundException.NotFoundType.CLZ,clzName);
        }
        Intent intent = createIntent(context);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent,extras.getRequestCode());
            int inAnimation = extras.getInAnimation();
            int outAnimation = extras.getOutAnimation();
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
        this.extras.setRequestCode(requestCode);
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        this.extras.setInAnimation(enterAnim);
        this.extras.setOutAnimation(exitAnim);
        return this;
    }

    @Override
    public IActivityRoute addExtras(Bundle extras) {
        this.extras.setExtras(extras);
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        this.extras.addFlags(flag);
        return this;
    }

    /**
     * create {@link BundleWrapper} instance by type.
     * <p>
     *     When <i>type</i> between -1 and 7,should create subclass of {@link SimpleBundle} with type<br>
     *     When <i>type</i> between 8 and 9,should create subclass of {@link ListBundle}with type <br>
     *     Otherwise,should create of {@link SimpleBundle} with type {@link RouteMap#STRING}
     * </p>
     * @return The type to indicate how tyce should be use to create wrapper instance
     */
    BundleWrapper createBundleWrapper (int type) {
        switch (type) {
            case RouteMap.STRING:
            case RouteMap.BYTE:
            case RouteMap.SHORT:
            case RouteMap.INT:
            case RouteMap.LONG:
            case RouteMap.FLOAT:
            case RouteMap.DOUBLE:
            case RouteMap.BOOLEAN:
            case RouteMap.CHAR:
                return new SimpleBundle(type);
            case RouteMap.INT_LIST:
            case RouteMap.STRING_LIST:
                return new ListBundle(type);
            default:
                return new SimpleBundle(RouteMap.STRING);
        }
    }

    @Override
    public IActivityRoute addInterceptor(RouteInterceptor interceptor) {
        extras.addInterceptor(interceptor);
        return this;
    }

    @Override
    public IActivityRoute removeInterceptor(RouteInterceptor interceptor) {
        extras.removeInterceptor(interceptor);
        return this;
    }

    @Override
    public IActivityRoute removeAllInterceptors() {
        extras.removeAllInterceptors();
        return this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {
        List<RouteInterceptor> list = new ArrayList<>();
        RouteInterceptor global = RouteManager.get().getGlobalInterceptor();
        if (global != null) {
            list.add(global);
        }
        if (extras != null && extras.getInterceptors() != null) {
            list.addAll(extras.getInterceptors());
        }
        return list;
    }

}
