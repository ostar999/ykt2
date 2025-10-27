package com.tencent.smtt.sdk;

import android.os.HandlerThread;

/* loaded from: classes6.dex */
class n extends HandlerThread {

    /* renamed from: a, reason: collision with root package name */
    private static n f21240a;

    public n(String str) {
        super(str);
    }

    public static synchronized n a() {
        if (f21240a == null) {
            n nVar = new n("TbsHandlerThread");
            f21240a = nVar;
            nVar.start();
        }
        return f21240a;
    }
}
