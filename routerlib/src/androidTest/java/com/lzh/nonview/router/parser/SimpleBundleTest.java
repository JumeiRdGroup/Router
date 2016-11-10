package com.lzh.nonview.router.parser;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.module.RouteMap;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by haoge on 16/11/10.
 */
public class SimpleBundleTest {

    @Test
    public void testSimpleBundle () throws Exception {
        SimpleBundle intBundle = new SimpleBundle(RouteMap.INT);
    }
}