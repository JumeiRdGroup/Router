package com.lzh.nonview.router.extras;

import android.os.Parcel;

/**
 * An extra container associate with {@link com.lzh.nonview.router.route.ActionRoute}
 *
 * @author haoge
 *
 * @see com.lzh.nonview.router.route.ActionRoute
 * @see com.lzh.nonview.router.route.ActionSupport
 */
public class ActionRouteBundleExtras extends RouteBundleExtras {

    public ActionRouteBundleExtras() {}

    private ActionRouteBundleExtras(Parcel in) {
        super(in);
    }

    public static final Creator<ActionRouteBundleExtras> CREATOR = new Creator<ActionRouteBundleExtras>() {
        @Override
        public ActionRouteBundleExtras createFromParcel(Parcel in) {
            return new ActionRouteBundleExtras(in);
        }

        @Override
        public ActionRouteBundleExtras[] newArray(int size) {
            return new ActionRouteBundleExtras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
