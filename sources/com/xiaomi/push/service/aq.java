package com.xiaomi.push.service;

import com.xiaomi.push.id;
import com.xiaomi.push.ie;

/* loaded from: classes6.dex */
/* synthetic */ class aq {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f25586a;

    /* renamed from: b, reason: collision with root package name */
    static final /* synthetic */ int[] f25587b;

    static {
        int[] iArr = new int[ie.values().length];
        f25587b = iArr;
        try {
            iArr[ie.INT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f25587b[ie.LONG.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f25587b[ie.STRING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f25587b[ie.BOOLEAN.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        int[] iArr2 = new int[id.values().length];
        f25586a = iArr2;
        try {
            iArr2[id.MISC_CONFIG.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f25586a[id.PLUGIN_CONFIG.ordinal()] = 2;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
