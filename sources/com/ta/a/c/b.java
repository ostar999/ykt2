package com.ta.a.c;

import android.text.TextUtils;
import com.ta.a.e.g;
import com.ta.a.e.h;

/* loaded from: classes6.dex */
public class b {
    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new String(com.ta.utdid2.a.a.b.encode(g.b(str.getBytes()), 2), "UTF-8");
        } catch (Exception e2) {
            h.a("", e2, new Object[0]);
            return "";
        }
    }
}
