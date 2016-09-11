package com.lzh.nonview.router.parser;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class URIParserTest {

    @Test
    public void testParse() throws Exception {
        String url = "http://www.baidu.com?username=liu&password=mimi";
        URIParser parser = new URIParser(Uri.parse(url));
        Assert.assertEquals("http",parser.getScheme());
        Assert.assertEquals("www.baidu.com",parser.getHost());
        Assert.assertEquals("liu",parser.getParams().get("username"));
        Assert.assertEquals("mimi",parser.getParams().get("password"));
    }
}