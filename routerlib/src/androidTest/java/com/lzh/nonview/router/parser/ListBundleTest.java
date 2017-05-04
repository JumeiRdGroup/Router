package com.lzh.nonview.router.parser;

import android.os.Bundle;

import com.lzh.nonview.router.module.RouteMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by haoge on 16/11/11.
 */
public class ListBundleTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testListBundle () throws Exception {
        Bundle bundle = new Bundle();

//        public static final int INT_LIST = 8;
        ListBundle wrapper = new ListBundle(RouteMap.INT_LIST);
        wrapper.set("100");
        wrapper.set("200");
        wrapper.set("300");
        wrapper.put(bundle,"INT_LIST");
        assertEquals(3,bundle.getIntegerArrayList("INT_LIST").size());
//        public static final int STRING_LIST = 9;

        wrapper = new ListBundle(RouteMap.STRING_LIST);
        wrapper.set("100");
        wrapper.set("200");
        wrapper.set("300");
        wrapper.put(bundle,"STRING_LIST");
        assertEquals(3,bundle.getIntegerArrayList("STRING_LIST").size());
    }
}