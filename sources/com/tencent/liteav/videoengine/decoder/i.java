package com.tencent.liteav.videoengine.decoder;

import java.io.IOException;
import org.json.JSONException;

/* loaded from: classes6.dex */
final /* synthetic */ class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final h f20351a;

    /* renamed from: b, reason: collision with root package name */
    private final Object f20352b;

    /* renamed from: c, reason: collision with root package name */
    private final o f20353c;

    private i(h hVar, Object obj, o oVar) {
        this.f20351a = hVar;
        this.f20352b = obj;
        this.f20353c = oVar;
    }

    public static Runnable a(h hVar, Object obj, o oVar) {
        return new i(hVar, obj, oVar);
    }

    @Override // java.lang.Runnable
    public void run() throws JSONException, IOException {
        this.f20351a.a(this.f20352b, this.f20353c);
    }
}
