package com.ta.a.d;

import android.text.TextUtils;
import com.tencent.open.SocialOperation;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public int f17207a = -1;
    public long timestamp = 0;

    /* renamed from: a, reason: collision with other field name */
    public String f73a = "";
    public byte[] data = null;

    /* renamed from: b, reason: collision with root package name */
    public long f17208b = 0;

    public static boolean a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                com.ta.a.e.h.b("", "result", str, SocialOperation.GAME_SIGNATURE, str2);
                if (str2.equals(com.ta.utdid2.a.a.b.encodeToString(com.ta.a.e.d.d(str).getBytes(), 2))) {
                    com.ta.a.e.h.m109a("", "signature is ok");
                    return true;
                }
                com.ta.a.e.h.m109a("", "signature is error");
            }
        } catch (Exception e2) {
            com.ta.a.e.h.m109a("", e2);
        }
        return false;
    }
}
