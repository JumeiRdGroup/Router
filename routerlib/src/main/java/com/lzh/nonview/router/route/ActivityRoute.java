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
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.parser.URIParser;

/**
 * A route tool to check route rule by uri and launch activity
 * Created by lzh on 16/9/5.
 */
public class ActivityRoute extends BaseRoute<IActivityRoute, ActivityRouteBundleExtras> implements IActivityRoute {

    @Override
    public void open(Context context, Uri uri) {
        try {
            Utils.checkInterceptor(uri, getExtras(),context,getInterceptors());

            ActivityRoute route = (ActivityRoute) getRoute(uri);
            route.openInternal(context);

            callback.onOpenSuccess(uri,routeMap.getClzName());
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public IRoute getRoute(Uri uri) {
        try {
            return getRouteInternal(uri);
        } catch (Throwable e) {
            callback.onOpenFailed(uri,e);
            return IRoute.EMPTY;
        }
    }

    @Override
    public void resumeRoute(Context context, Uri uri, RouteBundleExtras extras) {
        try {
            this.extras = (ActivityRouteBundleExtras) extras;
            ActivityRoute route = (ActivityRoute) getRoute(uri);
            route.openInternal(context);
            callback.onOpenSuccess(uri, routeMap.getClzName());
        } catch (Throwable e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(uri,e);
            }
        }
    }

    private IRoute getRouteInternal(Uri uri) {
        this.uri = uri;
        this.parser = new URIParser(uri);
        this.extras = getExtras();
        URIParser parser = new URIParser(uri);
        routeMap = RouteManager.get().getRouteMapByUri(parser);
        bundle = getBundle();
        return this;
    }

    @Override
    public void open(Context context) {
        try {
            Utils.checkInterceptor(uri, extras,context,getInterceptors());
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
        if (extras instanceof ActivityRouteBundleExtras) {
            intent.addFlags(extras.getFlags());
        }
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

    @Override
    protected ActivityRouteBundleExtras createExtras() {
        return new ActivityRouteBundleExtras();
    }
}
