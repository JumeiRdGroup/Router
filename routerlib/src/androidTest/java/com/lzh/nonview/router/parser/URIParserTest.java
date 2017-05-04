package com.lzh.nonview.router.parser;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class URIParserTest {

    @Test
    public void testParse() throws Exception {
        String url = "http://www.baidu.com?";
        URIParser parser = new URIParser(Uri.parse(url));
        Assert.assertEquals("http",parser.getScheme());
        Assert.assertEquals("www.baidu.com",parser.getHost());
        assertEquals(0,parser.getParams().size());
    }

    @Test
    public void testParseQuery () throws Exception {
        String query = "username=haoge&password=123&username=liuzhihao";
        Map<String, String> map = URIParser.parseParams(query);
        assertEquals(3,map.size());
        System.out.println(map);
    }
}