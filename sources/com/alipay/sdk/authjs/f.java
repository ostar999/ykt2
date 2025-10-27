package com.alipay.sdk.authjs;

import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
final class f extends TimerTask {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f3193a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f3194b;

    public f(d dVar, a aVar) {
        this.f3194b = dVar;
        this.f3193a = aVar;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("toastCallBack", k.a.f27523u);
        } catch (JSONException unused) {
        }
        a aVar = new a(a.f3170c);
        aVar.f3176i = this.f3193a.f3176i;
        aVar.f3180m = jSONObject;
        this.f3194b.f3189a.a(aVar);
    }
}
