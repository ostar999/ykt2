package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.jx;
import com.xiaomi.push.kh;

/* loaded from: classes6.dex */
public class jp {
    public static short a(Context context, jb jbVar) {
        return (short) (g.m438a(context, jbVar.f757b).a() + 0 + (ah.b(context) ? 4 : 0) + (ah.a(context) ? 8 : 0) + (com.xiaomi.push.service.bo.a(context, jbVar) ? 16 : 0));
    }

    public static short a(boolean z2, boolean z3, boolean z4) {
        return (short) ((z2 ? 4 : 0) + 0 + (z3 ? 2 : 0) + (z4 ? 1 : 0));
    }

    public static <T extends jq<T, ?>> void a(T t2, byte[] bArr) {
        if (bArr == null) {
            throw new jv("the message byte is empty.");
        }
        new ju(new kh.a(true, true, bArr.length)).a(t2, bArr);
    }

    public static <T extends jq<T, ?>> byte[] a(T t2) {
        if (t2 == null) {
            return null;
        }
        try {
            return new jw(new jx.a()).a(t2);
        } catch (jv e2) {
            com.xiaomi.channel.commonutils.logger.b.a("convertThriftObjectToBytes catch TException.", e2);
            return null;
        }
    }
}
