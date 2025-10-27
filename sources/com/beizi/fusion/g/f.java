package com.beizi.fusion.g;

import android.util.Base64;

/* loaded from: classes2.dex */
public class f {
    public static String a(String str) {
        try {
            return new String(Base64.decode(str, 0));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
