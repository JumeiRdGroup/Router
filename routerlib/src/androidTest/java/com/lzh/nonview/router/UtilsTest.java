package com.lzh.nonview.router;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    @Test
    public void testIsHttp() throws Exception {
        Assert.assertEquals(true,Utils.isHttp("http"));
        Assert.assertEquals(true,Utils.isHttp("https"));
    }

    @Test
    public void testIsClassSupport() throws Exception {
        Assert.assertEquals(true,Utils.isClassSupport("android.app.Activity"));
    }

    @Test
    public void testWrapScheme () throws Exception {
        String scheme = "jumei://www.com.cn/data/";
        Assert.assertEquals("jumei://www.com.cn/data",Utils.wrapScheme(scheme));
    }

}