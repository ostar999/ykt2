package com.xiaomi.push;

import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class dh implements Comparable<dh> {

    /* renamed from: a, reason: collision with root package name */
    protected int f24725a;

    /* renamed from: a, reason: collision with other field name */
    private long f307a;

    /* renamed from: a, reason: collision with other field name */
    String f308a;

    /* renamed from: a, reason: collision with other field name */
    private final LinkedList<cx> f309a;

    public dh() {
        this(null, 0);
    }

    public dh(String str) {
        this(str, 0);
    }

    public dh(String str, int i2) {
        this.f309a = new LinkedList<>();
        this.f307a = 0L;
        this.f308a = str;
        this.f24725a = i2;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(dh dhVar) {
        if (dhVar == null) {
            return 1;
        }
        return dhVar.f24725a - this.f24725a;
    }

    public synchronized dh a(JSONObject jSONObject) {
        this.f307a = jSONObject.getLong("tt");
        this.f24725a = jSONObject.getInt("wt");
        this.f308a = jSONObject.getString(com.alipay.sdk.cons.c.f3231f);
        JSONArray jSONArray = jSONObject.getJSONArray("ah");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.f309a.add(new cx().a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }

    public synchronized JSONObject a() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put("tt", this.f307a);
        jSONObject.put("wt", this.f24725a);
        jSONObject.put(com.alipay.sdk.cons.c.f3231f, this.f308a);
        JSONArray jSONArray = new JSONArray();
        Iterator<cx> it = this.f309a.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().m303a());
        }
        jSONObject.put("ah", jSONArray);
        return jSONObject;
    }

    public synchronized void a(cx cxVar) {
        if (cxVar != null) {
            this.f309a.add(cxVar);
            int iA = cxVar.a();
            if (iA > 0) {
                this.f24725a += cxVar.a();
            } else {
                int i2 = 0;
                for (int size = this.f309a.size() - 1; size >= 0 && this.f309a.get(size).a() < 0; size--) {
                    i2++;
                }
                this.f24725a += iA * i2;
            }
            if (this.f309a.size() > 30) {
                this.f24725a -= this.f309a.remove().a();
            }
        }
    }

    public String toString() {
        return this.f308a + ":" + this.f24725a;
    }
}
