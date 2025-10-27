package com.alipay.sdk.authjs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.authjs.a;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    c f3189a;

    /* renamed from: b, reason: collision with root package name */
    Context f3190b;

    public d(Context context, c cVar) {
        this.f3190b = context;
        this.f3189a = cVar;
    }

    private int b(a aVar) {
        if (aVar != null && "toast".equals(aVar.f3178k)) {
            JSONObject jSONObject = aVar.f3180m;
            String strOptString = jSONObject.optString("content");
            int i2 = jSONObject.optInt("duration") < 2500 ? 0 : 1;
            Toast.makeText(this.f3190b, strOptString, i2).show();
            new Timer().schedule(new f(this, aVar), i2);
        }
        return a.EnumC0026a.f3182a;
    }

    private void c(a aVar) {
        JSONObject jSONObject = aVar.f3180m;
        String strOptString = jSONObject.optString("content");
        int i2 = jSONObject.optInt("duration") < 2500 ? 0 : 1;
        Toast.makeText(this.f3190b, strOptString, i2).show();
        new Timer().schedule(new f(this, aVar), i2);
    }

    private void a(String str) throws JSONException {
        String str2 = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(a.f3172e);
            try {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("param");
                JSONObject jSONObject3 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                String string2 = jSONObject.getString(a.f3174g);
                String string3 = jSONObject.getString(a.f3171d);
                a aVar = new a("call");
                aVar.f3177j = string3;
                aVar.f3178k = string2;
                aVar.f3180m = jSONObject3;
                aVar.f3176i = string;
                a(aVar);
            } catch (Exception unused) {
                str2 = string;
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                try {
                    a(str2, a.EnumC0026a.f3185d);
                } catch (JSONException unused2) {
                }
            }
        } catch (Exception unused3) {
        }
    }

    public final void a(String str, int i2) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("error", i2 - 1);
        a aVar = new a(a.f3170c);
        aVar.f3180m = jSONObject;
        aVar.f3176i = str;
        this.f3189a.a(aVar);
    }

    private static void a(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public final void a(a aVar) throws JSONException {
        if (TextUtils.isEmpty(aVar.f3178k)) {
            a(aVar.f3176i, a.EnumC0026a.f3184c);
            return;
        }
        e eVar = new e(this, aVar);
        if (Looper.getMainLooper() == Looper.myLooper()) {
            eVar.run();
        } else {
            new Handler(Looper.getMainLooper()).post(eVar);
        }
    }

    private static /* synthetic */ int a(d dVar, a aVar) {
        if (aVar != null && "toast".equals(aVar.f3178k)) {
            JSONObject jSONObject = aVar.f3180m;
            String strOptString = jSONObject.optString("content");
            int i2 = jSONObject.optInt("duration") < 2500 ? 0 : 1;
            Toast.makeText(dVar.f3190b, strOptString, i2).show();
            new Timer().schedule(new f(dVar, aVar), i2);
        }
        return a.EnumC0026a.f3182a;
    }
}
