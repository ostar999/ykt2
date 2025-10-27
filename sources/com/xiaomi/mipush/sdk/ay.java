package com.xiaomi.mipush.sdk;

import com.xiaomi.push.hw;

/* loaded from: classes6.dex */
/* synthetic */ class ay {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f24536a;

    static {
        int[] iArr = new int[hw.values().length];
        f24536a = iArr;
        try {
            iArr[hw.SendMessage.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f24536a[hw.Registration.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f24536a[hw.UnRegistration.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f24536a[hw.Subscription.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f24536a[hw.UnSubscription.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f24536a[hw.Command.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f24536a[hw.Notification.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
    }
}
