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

    public ActivityRouteBundleExtras() {}

    protected ActivityRouteBundleExtras(Parcel in) {
        extras = in.readBundle();
        requestCode = in.readInt();
        inAnimation = in.readInt();
        outAnimation = in.readInt();
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

    public ActivityRouteBundleExtras setExtras(Bundle extras) {
        this.extras.putAll(extras);
        return this;
    }

    public ActivityRouteBundleExtras setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public ActivityRouteBundleExtras setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
        return this;
    }

    public ActivityRouteBundleExtras setOutAnimation(int outAnimation) {
        this.outAnimation = outAnimation;
        return this;
    }

    public Bundle getExtras() {
        return extras;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getInAnimation() {
        return inAnimation;
    }

    public int getOutAnimation() {
        return outAnimation;
    }

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
    }
}
