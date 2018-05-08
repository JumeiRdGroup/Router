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

import com.lzh.nonview.router.activityresult.ActivityResultCallback;
import com.lzh.nonview.router.activityresult.ActivityResultDispatcher;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.tools.Constants;
import com.lzh.nonview.router.tools.Utils;

import java.util.Random;

/**
 * Default Activity Launcher for {@link com.lzh.nonview.router.route.ActivityRoute}
 */
public class DefaultActivityLauncher extends ActivityLauncher{

    private static Random sCodeGenerator = new Random();

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
    public void open(Fragment fragment) {
        if (getResumeActivity() != null) {
            open(getResumeActivity());
        } else if (getResultCallback() != null) {
            open(fragment.getActivity());
        } else {
            Intent intent = createIntent(fragment.getActivity());
            fragment.startActivityForResult(intent, extras.getRequestCode());
            overridePendingTransition(fragment.getActivity(), extras);
        }
    }

    @Override
    public void open(android.support.v4.app.Fragment fragment) {
        if (getResumeActivity() != null) {
            open(getResumeActivity());
        } else if (getResultCallback() != null) {
            open(fragment.getActivity());
        } else {
            Intent intent = createIntent(fragment.getActivity());
            fragment.startActivityForResult(intent, extras.getRequestCode());
            overridePendingTransition(fragment.getActivity(), extras);
        }
    }

    @Override
    public void open(Context context) {
        Activity resume = getResumeActivity();
        if (resume != null) {
            context = resume;
        }

        ActivityResultCallback callback = getResultCallback();
        int requestCode = extras.getRequestCode();
        if (callback != null && requestCode == -1) {
            requestCode = sCodeGenerator.nextInt(0x0000ffff);
        }

        Intent intent = createIntent(context);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.startActivityForResult(intent,requestCode);
            overridePendingTransition((Activity) context, extras);
            ActivityResultDispatcher.get().bindRequestArgs(activity, requestCode, callback);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    protected void overridePendingTransition(Activity activity, RouteBundleExtras extras) {
        if (activity == null || extras == null) {
            return;
        }

        int inAnimation = extras.getInAnimation();
        int outAnimation = extras.getOutAnimation();
        if (inAnimation >= 0 && outAnimation >= 0) {
            activity.overridePendingTransition(inAnimation,outAnimation);
        }
    }

    private Activity getResumeActivity() {
        Activity activity = extras.getValue(Constants.KEY_RESUME_CONTEXT);
        return Utils.isValid(activity) ? activity : null;
    }

    private ActivityResultCallback getResultCallback() {
        return extras.getValue(Constants.KEY_RESULT_CALLBACK);
    }

}
