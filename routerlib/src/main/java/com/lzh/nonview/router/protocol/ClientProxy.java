package com.lzh.nonview.router.protocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ClientProxy implements InvocationHandler {

    static IClient newInstance() {
        return (IClient) Proxy.newProxyInstance(ClientProxy.class.getClassLoader(), new Class[]{IClient.class}, new ClientProxy());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("canOpen".equals(methodName)) {

        } else if ("open".equals(methodName)) {

        }
        return null;
    }
}
