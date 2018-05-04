package com.lzh.nonview.router.tools;

import java.util.Arrays;

/**
 * 进行临时性存储。方便将不方便放入Intent中进行存储的数据进行存储：
 *
 *
 * <pre>
 * // 举例：跨页面传递Context
 *
 * // 跳转前存储context
 * Intent intent = getIntent();
 * int index = CacheStore.get().put(context);
 * intent.putExtra(KEY_INDEX_CONTEXT, index);
 * startActivity(intent);
 *
 * // 跳转后读取context
 * Intent intent = getIntent();
 * int index = intent.getExtra(KEY_INDEX_CONTEXT);
 * // 读取后自动从CacheStore容器中移除。
 * Context context = CacheStore.get().get(index);
 * </pre>
 * Created by haoge on 2018/5/3.
 */
public class CacheStore {

    private Object[] stores = new Object[10];

    private static CacheStore INSTANCE = new CacheStore();
    private CacheStore(){}
    public static CacheStore get() {
        return INSTANCE;
    }

    public Object get(int index) {
        if (index < 0 || index >= stores.length) {
            return null;
        }
        Object value = stores[index];
        stores[index] = null;
        return value;
    }

    public int put(Object value) {
        if (value == null) {
            return -1;
        }
        int index = findIndex(value);
        stores[index] = value;
        return index;
    }

    private int findIndex(Object value) {
        int firstEmptyIndex = -1;
        for (int i = 0; i < stores.length; i++) {
            Object item = stores[i];
            if (item == null && firstEmptyIndex == -1) {
                firstEmptyIndex = i;
            }

            if (item == value) {
                return i;
            }
        }

        if (firstEmptyIndex == -1) {
            // 到此说明容器已满，需要扩容。定每次扩容大小为10
            int lastLength = stores.length;
            stores = Arrays.copyOf(stores, lastLength + 10);
            return lastLength;
        }
        // 返回首个空闲元素索引值。
        return firstEmptyIndex;
    }
}