package com.lzh.nonview.router.route;

import android.net.Uri;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.BundleWrapper;
import com.lzh.nonview.router.parser.ListBundle;
import com.lzh.nonview.router.parser.SimpleBundle;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class ActivityRouteTest {

    private ActivityRoute route;

    @Before
    public void setUp() throws Exception {
        route = new ActivityRoute();
        Router.addRouteCreator(new RouteCreator() {
            @Override
            public Map<String, RouteMap> createRouteRules() {
                Map<String,RouteMap> routes = new HashMap<>();
                routes.put("haoge://main",new RouteMap("xxx"));
                return routes;
            }
        });
    }

    @Test
    public void testCanOpenRouter() throws Exception {
        Assert.assertFalse(route.canOpenRouter(Uri.parse("http://www.baidu.com")));
        Assert.assertTrue(route.canOpenRouter(Uri.parse("haoge://main")));
        Assert.assertFalse(route.canOpenRouter(Uri.parse("haoge://test")));
    }

    @Test
    public void testCreateBundleWrapper() throws Exception{
        for (int i = -1; i < 10; i++) {
            BundleWrapper bundleWrapper = route.createBundleWrapper(i);
            if ((i >= -1 && i <= 7)) {
                if (!(bundleWrapper instanceof  SimpleBundle))
                    throw new AssertionError("match failed! type = " + i + ",create bundle with :" + bundleWrapper);
            } else if ((i == 8 || i == 9)) {
                if (!(bundleWrapper instanceof ListBundle))
                    throw new AssertionError("match failed! type = " + i + ",create bundle with :" + bundleWrapper);
            } else {
                if (!(bundleWrapper instanceof SimpleBundle)) {
                    throw new AssertionError("match failed! type = " + i + ",create bundle with :" + bundleWrapper);
                }
            }
        }
    }
}