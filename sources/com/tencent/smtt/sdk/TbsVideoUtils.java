package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.DexLoader;

/* loaded from: classes6.dex */
public class TbsVideoUtils {

    /* renamed from: a, reason: collision with root package name */
    private static s f21034a;

    private static void a(Context context) {
        synchronized (TbsVideoUtils.class) {
            if (f21034a == null) {
                g.a(true).a(context, false, false);
                u uVarA = g.a(true).a();
                DexLoader dexLoaderC = uVarA != null ? uVarA.c() : null;
                if (dexLoaderC != null) {
                    f21034a = new s(dexLoaderC);
                }
            }
        }
    }

    public static void deleteVideoCache(Context context, String str) {
        a(context);
        s sVar = f21034a;
        if (sVar != null) {
            sVar.a(context, str);
        }
    }

    public static String getCurWDPDecodeType(Context context) {
        a(context);
        s sVar = f21034a;
        return sVar != null ? sVar.a(context) : "";
    }
}
