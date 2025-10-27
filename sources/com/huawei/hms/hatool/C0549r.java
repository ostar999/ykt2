package com.huawei.hms.hatool;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.huawei.hms.hatool.r, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public class C0549r implements s {

    /* renamed from: a, reason: collision with root package name */
    public List<q> f7858a;

    /* renamed from: b, reason: collision with root package name */
    public o f7859b;

    /* renamed from: c, reason: collision with root package name */
    public p f7860c;

    /* renamed from: d, reason: collision with root package name */
    public s f7861d;

    /* renamed from: e, reason: collision with root package name */
    public String f7862e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f7863f;

    public C0549r(String str) {
        this.f7863f = str;
    }

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        String str;
        List<q> list = this.f7858a;
        if (list == null || list.size() == 0) {
            str = "Not have actionEvent to send";
        } else if (this.f7859b == null || this.f7860c == null || this.f7861d == null) {
            str = "model in wrong format";
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("header", this.f7859b.a());
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObjectA = this.f7861d.a();
            jSONObjectA.put("properties", this.f7860c.a());
            try {
                jSONObjectA.put("events_global_properties", new JSONObject(this.f7862e));
            } catch (JSONException unused) {
                jSONObjectA.put("events_global_properties", this.f7862e);
            }
            jSONObject2.put("events_common", jSONObjectA);
            JSONArray jSONArray = new JSONArray();
            Iterator<q> it = this.f7858a.iterator();
            while (it.hasNext()) {
                JSONObject jSONObjectA2 = it.next().a();
                if (jSONObjectA2 != null) {
                    jSONArray.put(jSONObjectA2);
                } else {
                    y.e("hmsSdk", "custom event is empty,delete this event");
                }
            }
            jSONObject2.put(com.umeng.analytics.pro.d.ar, jSONArray);
            try {
                String strA = d.a(t0.a(jSONObject2.toString().getBytes("UTF-8")), this.f7863f);
                if (TextUtils.isEmpty(strA)) {
                    y.e("hmsSdk", "eventInfo encrypt failed,report over!");
                    return null;
                }
                jSONObject.put(NotificationCompat.CATEGORY_EVENT, strA);
                return jSONObject;
            } catch (UnsupportedEncodingException unused2) {
                str = "getBitZip(): Unsupported coding : utf-8";
            }
        }
        y.e("hmsSdk", str);
        return null;
    }

    public void a(e1 e1Var) {
        this.f7861d = e1Var;
    }

    public void a(o oVar) {
        this.f7859b = oVar;
    }

    public void a(p pVar) {
        this.f7860c = pVar;
    }

    public void a(String str) {
        if (str != null) {
            this.f7862e = str;
        }
    }

    public void a(List<q> list) {
        this.f7858a = list;
    }
}
