package com.lzh.nonview.router.route;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A container to contains some extra data for intent
 * Created by lzh on 16/9/6.
 */
public class ActivityRouteBundleExtras implements Parcelable,RouteInterceptorAction<ActivityRouteBundleExtras>{
    Bundle extras = new Bundle();
    int requestCode = -1;
    int inAnimation = -1;
    int outAnimation = -1;
    int flags;
    ArrayList<RouteInterceptor> interceptors;

    ActivityRouteBundleExtras(){
        interceptors = new ArrayList<>();
    }

    protected ActivityRouteBundleExtras(Parcel in) {
        this();
        extras = in.readBundle(getClass().getClassLoader());
        requestCode = in.readInt();
        inAnimation = in.readInt();
        outAnimation = in.readInt();
        flags = in.readInt();
        int parcelableInterceptorSize = in.readInt();
        for (int i = 0; i < parcelableInterceptorSize; i++) {
            int type = in.readInt();
            RouteInterceptor interceptor;
            if (type == 0) {
                interceptor = in.readParcelable(getClass().getClassLoader());
            } else {
                interceptor = (RouteInterceptor) in.readSerializable();
            }
            addInterceptor(interceptor);
        }

    }

    public static final Creator<ActivityRouteBundleExtras> CREATOR = new Creator<ActivityRouteBundleExtras>() {
        @Override
        public ActivityRouteBundleExtras createFromParcel(Parcel in) {
            return new ActivityRouteBundleExtras(in);
        }

        @Override
        public ActivityRouteBundleExtras[] newArray(int size) {
            return new ActivityRouteBundleExtras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(extras);
        dest.writeInt(requestCode);
        dest.writeInt(inAnimation);
        dest.writeInt(outAnimation);
        dest.writeInt(flags);
        List<RouteInterceptor> parcelInterceptors = new ArrayList<>();

        for (RouteInterceptor interceptor : interceptors) {
            if (interceptor instanceof Parcelable
                    || interceptor instanceof Serializable) {
                parcelInterceptors.add(interceptor);
            }
        }
        dest.writeInt(parcelInterceptors.size());
        for (RouteInterceptor interceptor : parcelInterceptors) {
            if (interceptor instanceof Parcelable) {
                Parcelable parcelable = (Parcelable) interceptor;
                dest.writeInt(0);
                dest.writeParcelable(parcelable,flags);
            } else {
                Serializable serializable = (Serializable) interceptor;
                dest.writeInt(1);
                dest.writeSerializable(serializable);
            }
        }
        Log.e("RouteBundleExtras","Parcel success");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ActivityRouteBundleExtras addInterceptor(RouteInterceptor interceptor) {
        if (interceptor != null && !interceptors.contains(interceptor)) {
            interceptors.add(interceptor);
        }
        return this;
    }

    @Override
    public ActivityRouteBundleExtras removeInterceptor(RouteInterceptor interceptor) {
        if (interceptor != null) {
            interceptors.remove(interceptor);
        }
        return this;
    }

    @Override
    public ActivityRouteBundleExtras removeAllInterceptors() {
        interceptors.clear();
        return this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {
        return interceptors;
    }

}
