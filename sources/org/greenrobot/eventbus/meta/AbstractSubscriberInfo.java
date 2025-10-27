package org.greenrobot.eventbus.meta;

import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.SubscriberMethod;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes9.dex */
public abstract class AbstractSubscriberInfo implements SubscriberInfo {
    private final boolean shouldCheckSuperclass;
    private final Class subscriberClass;
    private final Class<? extends SubscriberInfo> superSubscriberInfoClass;

    public AbstractSubscriberInfo(Class cls, Class<? extends SubscriberInfo> cls2, boolean z2) {
        this.subscriberClass = cls;
        this.superSubscriberInfoClass = cls2;
        this.shouldCheckSuperclass = z2;
    }

    public SubscriberMethod createSubscriberMethod(String str, Class<?> cls) {
        return createSubscriberMethod(str, cls, ThreadMode.POSTING, 0, false);
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public Class getSubscriberClass() {
        return this.subscriberClass;
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public SubscriberInfo getSuperSubscriberInfo() {
        Class<? extends SubscriberInfo> cls = this.superSubscriberInfoClass;
        if (cls == null) {
            return null;
        }
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        }
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public boolean shouldCheckSuperclass() {
        return this.shouldCheckSuperclass;
    }

    public SubscriberMethod createSubscriberMethod(String str, Class<?> cls, ThreadMode threadMode) {
        return createSubscriberMethod(str, cls, threadMode, 0, false);
    }

    public SubscriberMethod createSubscriberMethod(String str, Class<?> cls, ThreadMode threadMode, int i2, boolean z2) {
        try {
            return new SubscriberMethod(this.subscriberClass.getDeclaredMethod(str, cls), cls, threadMode, i2, z2);
        } catch (NoSuchMethodException e2) {
            throw new EventBusException("Could not find subscriber method in " + this.subscriberClass + ". Maybe a missing ProGuard rule?", e2);
        }
    }
}
