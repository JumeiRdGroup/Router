package com.lzh.nonview.router.protocol;

import android.content.Intent;
import android.net.Uri;

import com.lzh.nonview.router.module.RemoteRule;

/**
 * Remote Service for Host app. it provided a bridge to do multi-module supported.
 */
interface IService {

    void addActivityRules(in Map rules);
    void addActionRules(in Map rules);

    RemoteRule getActionRule(in Uri uri);
    RemoteRule getActivityRule(in Uri uri);
}