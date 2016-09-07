package com.lzh.nonview.router.route;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by admin on 16/9/6.
 */
public class ActivityRouteBundleExtras implements Serializable{
    Bundle extras = new Bundle();
    int requestCode = -1;
    int inAnimation = -1;
    int outAnimation = -1;

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
}
