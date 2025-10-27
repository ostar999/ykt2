package com.xiaomi.push;

import android.content.Context;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;

/* loaded from: classes6.dex */
public class af {

    /* renamed from: a, reason: collision with root package name */
    static final char[] f24595a = BinTools.hex.toCharArray();

    public static String a(byte[] bArr, int i2, int i3) {
        StringBuilder sb = new StringBuilder(i3 * 2);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = bArr[i2 + i4] & 255;
            char[] cArr = f24595a;
            sb.append(cArr[i5 >> 4]);
            sb.append(cArr[i5 & 15]);
        }
        return sb.toString();
    }

    public static boolean a(Context context) {
        return ae.f180a;
    }
}
