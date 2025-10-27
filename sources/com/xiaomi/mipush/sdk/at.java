package com.xiaomi.mipush.sdk;

import com.xiaomi.push.hw;

/* loaded from: classes6.dex */
/* synthetic */ class at {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f24532a;

    static {
        int[] iArr = new int[hw.values().length];
        f24532a = iArr;
        try {
            iArr[hw.Registration.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f24532a[hw.UnRegistration.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f24532a[hw.Subscription.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f24532a[hw.UnSubscription.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f24532a[hw.SendMessage.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f24532a[hw.AckMessage.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f24532a[hw.SetConfig.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f24532a[hw.ReportFeedback.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f24532a[hw.Notification.ordinal()] = 9;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f24532a[hw.Command.ordinal()] = 10;
        } catch (NoSuchFieldError unused10) {
        }
    }
}
