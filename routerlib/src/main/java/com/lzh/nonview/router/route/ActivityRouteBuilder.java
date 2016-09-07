package com.lzh.nonview.router.route;

import android.os.Bundle;

/**
 * Created by admin on 16/9/6.
 */
public class ActivityRouteBuilder {
    Bundle extras = new Bundle();
    int requestCode = -1;
    int inAnimation = -1;
    int outAnimation = -1;

    public ActivityRouteBuilder setExtras(Bundle extras) {
        this.extras.putAll(extras);
        return this;
    }

    public ActivityRouteBuilder setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public ActivityRouteBuilder setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
        return this;
    }

    public ActivityRouteBuilder setOutAnimation(int outAnimation) {
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
