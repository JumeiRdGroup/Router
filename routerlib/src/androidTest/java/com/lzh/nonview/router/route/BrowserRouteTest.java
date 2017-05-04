package com.lzh.nonview.router.route;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BrowserRouteTest {

    @Test
    public void testCanOpenRouter() throws Exception {
        Assert.assertTrue(BrowserRoute.getInstance().canOpenRouter(Uri.parse("http://www.baidu.com")));
        Assert.assertFalse(BrowserRoute.getInstance().canOpenRouter(Uri.parse("haoge://main")));
    }
}