package com.lzh.nonview.router;

import android.support.test.runner.AndroidJUnit4;

import com.lzh.nonview.router.module.ActionRouteMap;
import com.lzh.nonview.router.module.ActivityRouteMap;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.route.ActivityRoute;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class RouteManagerTest {

    @Test
    public void testGetRouteMap() throws Exception {
        RouteManager.get().addCreator(new RouteCreator() {
            @Override
            public Map<String, ActivityRouteMap> createActivityRouteRules() {
                return null;
            }

            @Override
            public Map<String, ActionRouteMap> createActionRouteRules() {
                return null;
            }

        });

//        RouteManager.get().addCreator(new RouteCreator() {
//            @Override
//            public Map<String, RouteMap> createRouteRules() {
//                Map<String, RouteMap> routes = new HashMap<>();
//                routes.put("jumei://test",new RouteMap("className"));
//                return routes;
//            }
//        });

        Map<String, ActivityRouteMap> routeMap = RouteManager.get().getActivityRouteMap();
        Assert.assertTrue(routeMap.containsKey("jumei://main"));
        Assert.assertTrue(routeMap.containsKey("jumei://test"));
    }
}