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
package com.lzh.nonview.router.launcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

/**
 * Default Activity Launcher for {@link com.lzh.nonview.router.route.ActivityRoute}
 */
public class DefaultActivityLauncher extends ActivityLauncher{

    @Override
    public Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, rule.getRuleClz());
        intent.putExtras(bundle);
        intent.putExtras(extras.getExtras());
        intent.addFlags(extras.getFlags());
        return intent;
    }

    @Override
    public void open(Fragment fragment) throws Exception {
        Intent intent = createIntent(fragment.getActivity());
        fragment.startActivityForResult(intent, extras.getRequestCode());
    }

    @Override
    public void open(android.support.v4.app.Fragment fragment) throws Exception {
        Intent intent = createIntent(fragment.getContext());
        fragment.startActivityForResult(intent, extras.getRequestCode());
    }

    @Override
    public void open(Context context) throws Exception{
        Intent intent = createIntent(context);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent,extras.getRequestCode());
            int inAnimation = extras.getInAnimation();
            int outAnimation = extras.getOutAnimation();
            if (inAnimation >= 0 && outAnimation >= 0) {
                ((Activity) context).overridePendingTransition(inAnimation,outAnimation);
            }
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
