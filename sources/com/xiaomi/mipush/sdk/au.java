package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.l;

/* loaded from: classes6.dex */
public class au {
    public static AbstractPushManager a(Context context, f fVar) {
        return b(context, fVar);
    }

    private static AbstractPushManager b(Context context, f fVar) {
        l.a aVarM177a = l.m177a(fVar);
        if (aVarM177a == null || TextUtils.isEmpty(aVarM177a.f24572a) || TextUtils.isEmpty(aVarM177a.f24573b)) {
            return null;
        }
        return (AbstractPushManager) com.xiaomi.push.at.a(aVarM177a.f24572a, aVarM177a.f24573b, context);
    }
}
