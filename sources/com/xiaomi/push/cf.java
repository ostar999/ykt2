package com.xiaomi.push;

import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
final class cf {

    /* renamed from: a, reason: collision with root package name */
    private final double f24674a;

    /* renamed from: a, reason: collision with other field name */
    private final long f250a;

    /* renamed from: a, reason: collision with other field name */
    private final String f251a;

    /* renamed from: a, reason: collision with other field name */
    private final List f252a;

    /* renamed from: b, reason: collision with root package name */
    private final double f24675b;

    /* renamed from: b, reason: collision with other field name */
    private final long f253b;

    /* renamed from: b, reason: collision with other field name */
    private final String f254b;

    /* renamed from: c, reason: collision with root package name */
    private final String f24676c;

    /* renamed from: d, reason: collision with root package name */
    private final String f24677d;

    /* renamed from: e, reason: collision with root package name */
    private final String f24678e;

    /* renamed from: f, reason: collision with root package name */
    private final String f24679f;

    /* renamed from: g, reason: collision with root package name */
    private final String f24680g;

    /* renamed from: h, reason: collision with root package name */
    private final String f24681h;

    private cf(cd cdVar) {
        this.f251a = cdVar.f245a;
        this.f254b = cdVar.f248b;
        this.f24676c = cdVar.f24667c;
        this.f24677d = cdVar.f24668d;
        this.f24674a = cdVar.f24665a;
        this.f24675b = cdVar.f24666b;
        this.f24678e = cdVar.f24669e;
        this.f24679f = cdVar.f24670f;
        this.f250a = cdVar.f244a;
        this.f253b = cdVar.f247b;
        this.f24680g = cdVar.f24671g;
        this.f24681h = cdVar.f24672h;
        this.f252a = cdVar.f246a;
    }

    private void a(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (jSONObject == null || obj == null) {
            return;
        }
        if ((obj instanceof String) && ((String) obj).isEmpty()) {
            return;
        }
        try {
            jSONObject.put(str, obj);
        } catch (JSONException unused) {
        }
    }

    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        a(jSONObject, "m", this.f251a);
        a(jSONObject, com.umeng.analytics.pro.am.aC, this.f254b);
        a(jSONObject, com.umeng.analytics.pro.am.av, this.f24676c);
        a(jSONObject, "o", this.f24677d);
        a(jSONObject, "lg", Double.valueOf(this.f24674a));
        a(jSONObject, "lt", Double.valueOf(this.f24675b));
        a(jSONObject, CommonNetImpl.AM, this.f24678e);
        a(jSONObject, "as", this.f24679f);
        a(jSONObject, "ast", Long.valueOf(this.f250a));
        a(jSONObject, com.umeng.analytics.pro.am.aw, Long.valueOf(this.f253b));
        a(jSONObject, "ds", this.f24680g);
        a(jSONObject, AliyunLogKey.KEY_DEVICE_MODEL, this.f24681h);
        JSONArray jSONArray = new JSONArray();
        Iterator it = this.f252a.iterator();
        while (it.hasNext()) {
            jSONArray.put((String) it.next());
        }
        a(jSONObject, "devices", jSONArray);
        return jSONObject;
    }
}
