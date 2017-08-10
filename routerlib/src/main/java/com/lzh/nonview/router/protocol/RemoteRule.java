package com.lzh.nonview.router.protocol;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.nonview.router.module.RouteRule;

public class RemoteRule implements Parcelable{
    RouteRule rule;
    Bundle extra;

    public RemoteRule() {
    }

    protected RemoteRule(Parcel in) {
        rule = in.readParcelable(RouteRule.class.getClassLoader());
        extra = in.readBundle(getClass().getClassLoader());
    }

    public static final Creator<RemoteRule> CREATOR = new Creator<RemoteRule>() {
        @Override
        public RemoteRule createFromParcel(Parcel in) {
            return new RemoteRule(in);
        }

        @Override
        public RemoteRule[] newArray(int size) {
            return new RemoteRule[size];
        }
    };

    public static RemoteRule create(RouteRule rule, Bundle extra) {
        RemoteRule remote = new RemoteRule();
        remote.rule = rule;
        remote.extra = extra;
        return remote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(rule, flags);
        dest.writeBundle(extra);
    }
}
