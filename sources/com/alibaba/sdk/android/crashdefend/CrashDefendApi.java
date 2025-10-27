package com.alibaba.sdk.android.crashdefend;

import android.content.Context;

/* loaded from: classes2.dex */
public class CrashDefendApi {
    public static void registerCrashDefendSdk(Context context, String str, String str2, int i2, int i3, CrashDefendCallback crashDefendCallback) {
        a.a(context).a(str, str2, i2, i3, crashDefendCallback);
    }
}
