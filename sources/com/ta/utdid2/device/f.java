package com.ta.utdid2.device;

import com.ta.a.e.h;

/* loaded from: classes6.dex */
public class f {
    public static boolean a(com.ta.a.d.a aVar) {
        String str;
        try {
            str = new String(aVar.data, "UTF-8");
        } catch (Exception e2) {
            Object[] objArr = {e2};
            str = "";
            h.m109a("", objArr);
        }
        if (com.ta.a.d.a.a(str, aVar.f73a)) {
            return b.b(b.a(str).f17267e);
        }
        return false;
    }
}
