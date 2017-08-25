package com.lzh.usercenetr;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.ActionSupport;

@RouterRule("action")
public class UserCenterAction extends ActionSupport{
    @Override
    public void onRouteTrigger(Context context, Bundle bundle) {
        Toast.makeText(context, "UserCenter Action invoked", Toast.LENGTH_SHORT).show();
    }
}
