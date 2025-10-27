package com.huawei.agconnect.core;

import com.huawei.agconnect.annotation.AutoCreated;
import com.huawei.agconnect.annotation.SharedInstance;
import com.huawei.agconnect.annotation.Singleton;
import java.lang.reflect.Modifier;

/* loaded from: classes4.dex */
public class Service {

    /* renamed from: a, reason: collision with root package name */
    private final Class<?> f7322a;

    /* renamed from: b, reason: collision with root package name */
    private final Class<?> f7323b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f7324c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f7325d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f7326e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f7327f;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        Class<?> f7328a;

        /* renamed from: b, reason: collision with root package name */
        Class<?> f7329b;

        /* renamed from: c, reason: collision with root package name */
        Object f7330c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f7331d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f7332e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f7333f;

        public Service build() {
            Class<?> cls = this.f7328a;
            if (cls == null) {
                throw new IllegalArgumentException("the interface parameter cannot be NULL");
            }
            Class<?> cls2 = this.f7329b;
            if (cls2 == null) {
                Object obj = this.f7330c;
                if (obj == null) {
                    throw new IllegalArgumentException("the clazz or object parameter must set one");
                }
                Service service = new Service(cls, obj);
                service.f7325d = this.f7331d;
                return service;
            }
            if (cls2.isInterface() || !Modifier.isPublic(this.f7329b.getModifiers())) {
                throw new IllegalArgumentException("the clazz parameter cant be interface type or not public");
            }
            Service service2 = new Service((Class) this.f7328a, (Class) this.f7329b);
            service2.f7325d = this.f7331d;
            service2.f7326e = this.f7332e;
            service2.f7327f = this.f7333f;
            return service2;
        }

        public Builder isAutoCreated(boolean z2) {
            this.f7333f = z2;
            return this;
        }

        public Builder isSharedInstance(boolean z2) {
            this.f7332e = z2;
            return this;
        }

        public Builder isSingleton(boolean z2) {
            this.f7331d = z2;
            return this;
        }

        public Builder setClass(Class<?> cls) {
            this.f7329b = cls;
            return this;
        }

        public Builder setInterface(Class<?> cls) {
            this.f7328a = cls;
            return this;
        }

        public Builder setObject(Object obj) {
            this.f7330c = obj;
            return this;
        }
    }

    private Service(Class<?> cls, Class<?> cls2) {
        this.f7322a = cls;
        this.f7323b = cls2;
        this.f7324c = null;
    }

    private Service(Class<?> cls, Object obj) {
        this.f7322a = cls;
        this.f7323b = null;
        this.f7324c = obj;
    }

    public static Builder builder(Class<?> cls) {
        return new Builder().setInterface(cls).setClass(cls).isSingleton(cls.isAnnotationPresent(Singleton.class)).isSharedInstance(cls.isAnnotationPresent(SharedInstance.class)).isAutoCreated(cls.isAnnotationPresent(AutoCreated.class));
    }

    public static Builder builder(Class<?> cls, Class<?> cls2) {
        return new Builder().setInterface(cls).setClass(cls2).isSingleton(cls2.isAnnotationPresent(Singleton.class)).isSharedInstance(cls2.isAnnotationPresent(SharedInstance.class)).isAutoCreated(cls2.isAnnotationPresent(AutoCreated.class));
    }

    public static Builder builder(Class<?> cls, Object obj) {
        return new Builder().setInterface(cls).setObject(obj).isSingleton(true).isSharedInstance(cls.isAnnotationPresent(SharedInstance.class)).isAutoCreated(cls.isAnnotationPresent(AutoCreated.class));
    }

    public Object getInstance() {
        return this.f7324c;
    }

    public Class<?> getInterface() {
        return this.f7322a;
    }

    public Class<?> getType() {
        return this.f7323b;
    }

    public boolean isAutoCreated() {
        return this.f7327f;
    }

    public boolean isSharedInstance() {
        return this.f7326e;
    }

    public boolean isSingleton() {
        return this.f7325d;
    }
}
