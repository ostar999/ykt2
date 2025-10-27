package com.tencent.smtt.sdk;

import android.content.Context;
import java.io.IOException;
import java.util.UnknownFormatConversionException;

/* loaded from: classes6.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    static int f21174a = 5;

    /* renamed from: b, reason: collision with root package name */
    static int f21175b = 16;

    /* renamed from: c, reason: collision with root package name */
    static char[] f21176c = new char[16];

    /* renamed from: d, reason: collision with root package name */
    static String f21177d = "dex2oat-cmdline";

    /* renamed from: e, reason: collision with root package name */
    static long f21178e = 4096;

    public static String a(Context context, String str) throws Exception {
        com.tencent.smtt.utils.c cVar = new com.tencent.smtt.utils.c(str);
        cVar.a(f21176c);
        cVar.a(f21176c[f21174a] == 1);
        cVar.a(f21178e);
        return a(new String(a(cVar)));
    }

    private static String a(String str) {
        String[] strArrSplit = str.split(new String("\u0000"));
        int i2 = 0;
        while (i2 < strArrSplit.length) {
            int i3 = i2 + 1;
            String str2 = strArrSplit[i2];
            int i4 = i3 + 1;
            String str3 = strArrSplit[i3];
            if (str2.equals(f21177d)) {
                return str3;
            }
            i2 = i4;
        }
        return "";
    }

    public static char[] a(com.tencent.smtt.utils.c cVar) throws IOException {
        char[] cArr = new char[4];
        char[] cArr2 = new char[4];
        cVar.a(cArr);
        if (cArr[0] != 'o' || cArr[1] != 'a' || cArr[2] != 't') {
            throw new UnknownFormatConversionException(String.format("Invalid art magic %c%c%c", Character.valueOf(cArr[0]), Character.valueOf(cArr[1]), Character.valueOf(cArr[2])));
        }
        cVar.a(cArr2);
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        if (cArr2[1] <= '4') {
            cVar.b();
            cVar.b();
            cVar.b();
        }
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        cVar.b();
        char[] cArr3 = new char[cVar.b()];
        cVar.a(cArr3);
        return cArr3;
    }
}
