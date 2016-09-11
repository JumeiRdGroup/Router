package com.lzh.nonview.router;

import android.support.test.runner.AndroidJUnit4;

import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class RouteManagerTest {

    @Test
    public void testGetRouteMap() throws Exception {
        RouteManager.INSTANCE.addCreator(new RouteCreator() {
            @Override
            public Map<String, RouteMap> createRouteRules() {
                Map<String, RouteMap> routes = new HashMap<>();
                routes.put("jumei://main",new RouteMap("className"));
                return routes;
            }
        });

        RouteManager.INSTANCE.addCreator(new RouteCreator() {
            @Override
            public Map<String, RouteMap> createRouteRules() {
                Map<String, RouteMap> routes = new HashMap<>();
                routes.put("jumei://test",new RouteMap("className"));
                return routes;
            }
        });

        Map<String, RouteMap> routeMap = RouteManager.INSTANCE.getRouteMap();
        Assert.assertTrue(routeMap.containsKey("jumei://main"));
        Assert.assertTrue(routeMap.containsKey("jumei://test"));
    }
}