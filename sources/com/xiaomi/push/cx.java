package com.xiaomi.push;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class cx {

    /* renamed from: a, reason: collision with root package name */
    private int f24702a;

    /* renamed from: a, reason: collision with other field name */
    private long f287a;

    /* renamed from: a, reason: collision with other field name */
    private String f288a;

    /* renamed from: b, reason: collision with root package name */
    private long f24703b;

    /* renamed from: c, reason: collision with root package name */
    private long f24704c;

    public cx() {
        this(0, 0L, 0L, null);
    }

    public cx(int i2, long j2, long j3, Exception exc) {
        this.f24702a = i2;
        this.f287a = j2;
        this.f24704c = j3;
        this.f24703b = System.currentTimeMillis();
        if (exc != null) {
            this.f288a = exc.getClass().getSimpleName();
        }
    }

    public int a() {
        return this.f24702a;
    }

    public cx a(JSONObject jSONObject) {
        this.f287a = jSONObject.getLong("cost");
        this.f24704c = jSONObject.getLong(DatabaseManager.SIZE);
        this.f24703b = jSONObject.getLong("ts");
        this.f24702a = jSONObject.getInt("wt");
        this.f288a = jSONObject.optString("expt");
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public JSONObject m303a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("cost", this.f287a);
        jSONObject.put(DatabaseManager.SIZE, this.f24704c);
        jSONObject.put("ts", this.f24703b);
        jSONObject.put("wt", this.f24702a);
        jSONObject.put("expt", this.f288a);
        return jSONObject;
    }
}
