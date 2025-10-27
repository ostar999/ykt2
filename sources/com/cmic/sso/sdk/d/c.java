package com.cmic.sso.sdk.d;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class c extends b {

    /* renamed from: b, reason: collision with root package name */
    public static ArrayList<Throwable> f6417b = new ArrayList<>();

    /* renamed from: c, reason: collision with root package name */
    private JSONObject f6418c;

    /* renamed from: d, reason: collision with root package name */
    private JSONArray f6419d;

    @Override // com.cmic.sso.sdk.d.b
    public void a(JSONArray jSONArray) {
        this.f6419d = jSONArray;
    }

    @Override // com.cmic.sso.sdk.d.b, com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObjectB = super.b();
        try {
            jSONObjectB.put(NotificationCompat.CATEGORY_EVENT, this.f6418c);
            jSONObjectB.put("exceptionStackTrace", this.f6419d);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObjectB;
    }
}
