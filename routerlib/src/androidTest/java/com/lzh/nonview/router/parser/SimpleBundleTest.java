package com.lzh.nonview.router.parser;

import android.os.Bundle;

import com.lzh.nonview.router.module.RouteMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by haoge on 16/11/10.
 */
public class SimpleBundleTest {

    @Test
    public void testSimpleBundle () throws Exception {
        Bundle bundle = new Bundle();

//        public static final int STRING = -1;
        SimpleBundle wrapper = new SimpleBundle(RouteMap.STRING);
        wrapper.set("String");
        wrapper.put(bundle,"String");
        assertEquals("String",bundle.get("String"));
//        public static final int BYTE = 0;
        wrapper = new SimpleBundle(RouteMap.BYTE);
        wrapper.set("3");
        wrapper.put(bundle,"BYTE");
        assertEquals((byte) 3,bundle.get("BYTE"));
//        public static final int SHORT = 1;
        wrapper = new SimpleBundle(RouteMap.SHORT);
        wrapper.set("2");
        wrapper.put(bundle,"SHORT");
        assertEquals((short)2,bundle.get("SHORT"));
//        public static final int INT = 2;
        wrapper = new SimpleBundle(RouteMap.INT);
        wrapper.set("1");
        wrapper.put(bundle,"INT");
        assertEquals(1,bundle.getInt("INT"));
//        public static final int LONG = 3;
        wrapper = new SimpleBundle(RouteMap.LONG);
        wrapper.set("100");
        wrapper.put(bundle,"LONG");
        assertEquals(100L,bundle.get("LONG"));
//        public static final int FLOAT = 4;
        wrapper = new SimpleBundle(RouteMap.FLOAT);
        wrapper.set("3.14");
        wrapper.put(bundle,"FLOAT");
        assertEquals(3.14f,bundle.get("FLOAT"));
//        public static final int DOUBLE = 5;
        wrapper = new SimpleBundle(RouteMap.DOUBLE);
        wrapper.set("3.14");
        wrapper.put(bundle,"DOUBLE");
        assertEquals(3.14,bundle.get("DOUBLE"));
//        public static final int BOOLEAN = 6;
        wrapper = new SimpleBundle(RouteMap.BOOLEAN);
        wrapper.set("true");
        wrapper.put(bundle,"BOOLEAN");
        assertEquals(true,bundle.get("BOOLEAN"));
//        public static final int CHAR = 7;
        wrapper = new SimpleBundle(RouteMap.CHAR);
        wrapper.set("BYTE");
        wrapper.put(bundle,"CHAR");
        assertEquals('B',bundle.get("CHAR"));
    }
}