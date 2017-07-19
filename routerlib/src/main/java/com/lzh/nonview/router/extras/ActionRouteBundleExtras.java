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
