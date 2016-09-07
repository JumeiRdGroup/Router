package com.lzh.nonview.router;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by admin on 16/9/7.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    @Test
    public void testIsHttp() throws Exception {
        Assert.assertEquals(true,Utils.isHttp("http"));
        Assert.assertEquals(true,Utils.isHttp("https"));
        Assert.assertEquals(false,Utils.isHttp("scheme"));
    }

    @Test
    public void testIsClassSupport() throws Exception {
        Assert.assertEquals(true,Utils.isClassSupport("android.os.Bundle"));
    }
}