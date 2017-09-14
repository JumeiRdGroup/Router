package com.lzh.nonview.router.module;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class RemoteRule implements Parcelable{
    // target class name
    private String name;
    // params in RouteRule
    private HashMap params;
    // the extra bundle created by IRemoteFactory.
    private Bundle extra;
    // type in [Action, Activity]
    private int type;

    private RouteRule rule;

    public RemoteRule() {
    }

    protected RemoteRule(Parcel in) {
        name = in.readString();
        params = in.readHashMap(getClass().getClassLoader());
        type = in.readInt();
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
        remote.name = rule.getRuleClz();
        remote.params = rule.getParams();
        remote.type = (rule instanceof ActivityRouteRule) ? 0 : 1;
        remote.extra = extra;
        return remote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeMap(params);
        dest.writeInt(type);
        dest.writeBundle(extra);
    }

    public Bundle getExtra() {
        return extra;
    }

    public RouteRule getRule() {
        if (rule != null) {
            return rule;
        }

        switch (type) {
            case 0:
                rule = new ActivityRouteRule(name).setParams(params);
                break;
            default:
                rule = new ActionRouteRule(name).setParams(params);
        }
        return rule;
    }
}
