package com.alipay.sdk.authjs;

import android.widget.Toast;
import com.alipay.sdk.authjs.a;
import java.util.Timer;
import org.json.JSONObject;

/* loaded from: classes2.dex */
final class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f3191a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f3192b;

    public e(d dVar, a aVar) {
        this.f3192b = dVar;
        this.f3191a = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        d dVar = this.f3192b;
        a aVar = this.f3191a;
        if (aVar != null && "toast".equals(aVar.f3178k)) {
            JSONObject jSONObject = aVar.f3180m;
            String strOptString = jSONObject.optString("content");
            int i2 = jSONObject.optInt("duration") < 2500 ? 0 : 1;
            Toast.makeText(dVar.f3190b, strOptString, i2).show();
            new Timer().schedule(new f(dVar, aVar), i2);
        }
        int i3 = a.EnumC0026a.f3182a;
    }
}
