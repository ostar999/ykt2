package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.i;
import com.alipay.sdk.util.k;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
final class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f3251a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ a f3252b;

    public b(a aVar, Context context) {
        this.f3252b = aVar;
        this.f3251a = context;
    }

    @Override // java.lang.Runnable
    public final void run() throws JSONException {
        try {
            com.alipay.sdk.packet.impl.b bVar = new com.alipay.sdk.packet.impl.b();
            Context context = this.f3251a;
            com.alipay.sdk.packet.b bVarA = bVar.a(context, "", k.a(context), true);
            if (bVarA != null) {
                a aVar = this.f3252b;
                String str = bVarA.f3282b;
                if (!TextUtils.isEmpty(str)) {
                    try {
                        JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(a.f3246g);
                        aVar.f3249i = jSONObjectOptJSONObject.optInt("timeout", 3500);
                        aVar.f3250j = jSONObjectOptJSONObject.optString(a.f3247h, a.f3241b).trim();
                    } catch (Throwable unused) {
                    }
                }
                a aVar2 = this.f3252b;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("timeout", aVar2.a());
                jSONObject.put(a.f3247h, aVar2.f3250j);
                i.a(com.alipay.sdk.sys.b.a().f3333a, a.f3244e, jSONObject.toString());
            }
        } catch (Throwable unused2) {
        }
    }
}
