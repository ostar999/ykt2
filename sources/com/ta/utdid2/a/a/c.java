package com.ta.utdid2.a.a;

import android.os.Build;

/* loaded from: classes6.dex */
public class c {
    public static boolean c() {
        if (Build.VERSION.SDK_INT >= 29) {
            return true;
        }
        String str = Build.VERSION.CODENAME;
        return str.length() == 1 && str.charAt(0) >= 'Q' && str.charAt(0) <= 'Z';
    }
}
