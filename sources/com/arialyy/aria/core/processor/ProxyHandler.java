package com.arialyy.aria.core.processor;

import com.arialyy.aria.core.inf.IEventHandler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes2.dex */
public class ProxyHandler implements InvocationHandler {
    private Object mTarget;

    public Object bind(Class<? extends IEventHandler> cls) {
        try {
            this.mTarget = cls.newInstance();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InstantiationException e3) {
            e3.printStackTrace();
        }
        return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), this);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        return method.invoke(this.mTarget, objArr);
    }
}
