package com.lzh.nonview.router.extras;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RouteBundleExtras implements Parcelable, RouteInterceptorAction<RouteBundleExtras>{
    private Bundle extras = new Bundle();
    private ArrayList<RouteInterceptor> interceptors = new ArrayList<>();

    protected RouteBundleExtras() {}

    protected RouteBundleExtras(Parcel in) {
        extras = in.readBundle(getClass().getClassLoader());
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
        dest.writeBundle(extras);
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
    }

    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public void setInterceptors(ArrayList<RouteInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

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
}
