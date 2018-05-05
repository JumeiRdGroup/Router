/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.nonview.router.extras;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;
import com.lzh.nonview.router.route.IBaseRoute;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.nonview.router.tools.Cache;
import com.lzh.nonview.router.tools.CacheStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An extra container contains {@link RouteBundleExtras#extras} and {@link RouteBundleExtras#interceptors}
 * <p>
 *      <i>extras: the extra bundle data set by {@link IBaseRoute#addExtras(Bundle)}</i>
 *      <i>interceptors: the extra RouteInterceptor set by {@link IBaseRoute#addInterceptor(RouteInterceptor)}</i>
 * </p>
 *
 * @author haoge
 * @see com.lzh.nonview.router.route.IBaseRoute
 */
public final class RouteBundleExtras implements Parcelable, RouteInterceptorAction<RouteBundleExtras>{
    private Bundle extras = new Bundle();
    private ArrayList<RouteInterceptor> interceptors = new ArrayList<>();
    private RouteCallback callback;

    // the extras belows is only supports for ActivityRoute.
    private int requestCode = -1;
    private int inAnimation = -1;
    private int outAnimation = -1;
    private int flags = 0;

    public RouteBundleExtras() {}

    @SuppressWarnings("ConstantConditions")
    @Override
    public RouteBundleExtras addInterceptor(RouteInterceptor interceptor) {
        if (interceptor != null && !interceptors.contains(interceptor)) {
            interceptors.add(interceptor);
        }
        return this;
    }

    @Override
    public RouteBundleExtras removeInterceptor(RouteInterceptor interceptor) {
        if (interceptor != null) {
            interceptors.remove(interceptor);
        }
        return this;
    }

    @Override
    public RouteBundleExtras removeAllInterceptors() {
        interceptors.clear();
        return this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setCallback(RouteCallback callback) {
        this.callback = callback;
    }

    public RouteCallback getCallback() {
        return callback;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getInAnimation() {
        return inAnimation;
    }

    public void setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
    }

    public int getOutAnimation() {
        return outAnimation;
    }

    public void setOutAnimation(int outAnimation) {
        this.outAnimation = outAnimation;
    }

    public int getFlags() {
        return flags;
    }

    public void addFlags(int flags) {
        this.flags |= flags;
    }

    // ------------------------- divider for parcelable ------------------------
    private RouteBundleExtras(Parcel in) {
        requestCode = in.readInt();
        inAnimation = in.readInt();
        outAnimation = in.readInt();
        flags = in.readInt();
        extras = in.readBundle(getClass().getClassLoader());
        interceptors = CacheStore.get().get(in.readInt());
        callback = CacheStore.get().get(in.readInt());
    }

    public static final Creator<RouteBundleExtras> CREATOR = new Creator<RouteBundleExtras>() {
        @Override
        public RouteBundleExtras createFromParcel(Parcel in) {
            return new RouteBundleExtras(in);
        }

        @Override
        public RouteBundleExtras[] newArray(int size) {
            return new RouteBundleExtras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(requestCode);
        dest.writeInt(inAnimation);
        dest.writeInt(outAnimation);
        dest.writeInt(this.flags);

        dest.writeBundle(extras);

        dest.writeInt(CacheStore.get().put(interceptors));
        dest.writeInt(CacheStore.get().put(callback));
    }

    public Bundle getExtras() {
        return extras;
    }

    public void addExtras(Bundle extras) {
        if (extras != null) {
            this.extras.putAll(extras);
        }
    }
}
