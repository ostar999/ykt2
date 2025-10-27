package com.tencent.tbs.one.impl.a;

import android.content.Context;
import com.tencent.smtt.sdk.TbsConfig;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final EnumC0360a[] f21702a;

    /* renamed from: b, reason: collision with root package name */
    public static final EnumC0360a[] f21703b;

    /* renamed from: com.tencent.tbs.one.impl.a.a$a, reason: collision with other inner class name */
    public enum EnumC0360a {
        Mm("com.tencent.mm"),
        Mqq("com.tencent.mobileqq"),
        Mtt(TbsConfig.APP_QB),
        SogouExplorer("sogou.mobile.explorer"),
        SogouReader("com.sogou.reader.free");


        /* renamed from: f, reason: collision with root package name */
        public final String f21710f;

        EnumC0360a(String str) {
            this.f21710f = str;
        }
    }

    static {
        EnumC0360a enumC0360a = EnumC0360a.Mqq;
        f21702a = new EnumC0360a[]{enumC0360a, EnumC0360a.Mtt, EnumC0360a.SogouExplorer, EnumC0360a.SogouReader};
        f21703b = new EnumC0360a[]{enumC0360a};
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        return a(context, f21702a);
    }

    public static boolean a(Context context, EnumC0360a... enumC0360aArr) {
        if (enumC0360aArr != null && context != null) {
            for (EnumC0360a enumC0360a : enumC0360aArr) {
                if (context.getPackageName().equals(enumC0360a.f21710f)) {
                    return true;
                }
            }
        }
        return false;
    }
}
