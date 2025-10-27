package org.greenrobot.eventbus.meta;

import org.greenrobot.eventbus.SubscriberMethod;

/* loaded from: classes9.dex */
public class SimpleSubscriberInfo extends AbstractSubscriberInfo {
    private final SubscriberMethodInfo[] methodInfos;

    public SimpleSubscriberInfo(Class cls, boolean z2, SubscriberMethodInfo[] subscriberMethodInfoArr) {
        super(cls, null, z2);
        this.methodInfos = subscriberMethodInfoArr;
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public synchronized SubscriberMethod[] getSubscriberMethods() {
        SubscriberMethod[] subscriberMethodArr;
        int length = this.methodInfos.length;
        subscriberMethodArr = new SubscriberMethod[length];
        for (int i2 = 0; i2 < length; i2++) {
            SubscriberMethodInfo subscriberMethodInfo = this.methodInfos[i2];
            subscriberMethodArr[i2] = createSubscriberMethod(subscriberMethodInfo.methodName, subscriberMethodInfo.eventType, subscriberMethodInfo.threadMode, subscriberMethodInfo.priority, subscriberMethodInfo.sticky);
        }
        return subscriberMethodArr;
    }
}
