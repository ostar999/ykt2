package com.beizi.ad.a.a;

import android.util.Base64;

/* loaded from: classes2.dex */
public class b {
    public static String a(String str) {
        try {
            return new String(Base64.decode(str, 0));
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
