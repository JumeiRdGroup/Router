package com.lzh.nonview.router.route;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A container to contains some extra data for intent
 * Created by lzh on 16/9/6.
 */
public class ActivityRouteBundleExtras implements Parcelable{
    Bundle extras = new Bundle();
    int requestCode = -1;
    int inAnimation = -1;
    int outAnimation = -1;
    int flags;

    ActivityRouteBundleExtras(){}

    protected ActivityRouteBundleExtras(Parcel in) {
        extras = in.readBundle(getClass().getClassLoader());
        requestCode = in.readInt();
        inAnimation = in.readInt();
        outAnimation = in.readInt();
        flags = in.readInt();
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
    }
}
