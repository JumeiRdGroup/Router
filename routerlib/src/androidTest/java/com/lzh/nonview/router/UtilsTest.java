package com.lzh.nonview.router;

import android.net.Uri;
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

    @Test
    public void testCompatUri () throws Exception {
        String url = "http://www.baidu.com:1010/path/data/san/si/wu?username=haoge&data=null";
        String complete = "http://www.baidu.com:1010/path/data/san/si/wu?username=haoge&data=null";
        Uri completeUri = Uri.parse(complete);
        Uri source = Uri.parse(url);
        Uri compatUri = Utils.completeUri(source);
        Assert.assertEquals(completeUri,compatUri);
    }
}