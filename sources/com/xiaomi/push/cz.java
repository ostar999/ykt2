package com.xiaomi.push;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class cz {

    /* renamed from: a, reason: collision with root package name */
    private String f24715a;

    /* renamed from: a, reason: collision with other field name */
    private final ArrayList<cy> f293a = new ArrayList<>();

    public cz() {
    }

    public cz(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        this.f24715a = str;
    }

    public synchronized cy a() {
        for (int size = this.f293a.size() - 1; size >= 0; size--) {
            cy cyVar = this.f293a.get(size);
            if (cyVar.m307a()) {
                dc.a().m317a(cyVar.a());
                return cyVar;
            }
        }
        return null;
    }

    public synchronized cz a(JSONObject jSONObject) {
        this.f24715a = jSONObject.getString(com.alipay.sdk.cons.c.f3231f);
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.f293a.add(new cy(this.f24715a).a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m308a() {
        return this.f24715a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ArrayList<cy> m309a() {
        return this.f293a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized JSONObject m310a() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put(com.alipay.sdk.cons.c.f3231f, this.f24715a);
        JSONArray jSONArray = new JSONArray();
        Iterator<cy> it = this.f293a.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().m305a());
        }
        jSONObject.put("fbs", jSONArray);
        return jSONObject;
    }

    public synchronized void a(cy cyVar) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.f293a.size()) {
                break;
            }
            if (this.f293a.get(i2).a(cyVar)) {
                this.f293a.set(i2, cyVar);
                break;
            }
            i2++;
        }
        if (i2 >= this.f293a.size()) {
            this.f293a.add(cyVar);
        }
    }

    public synchronized void a(boolean z2) {
        ArrayList<cy> arrayList;
        for (int size = this.f293a.size() - 1; size >= 0; size--) {
            cy cyVar = this.f293a.get(size);
            if (z2) {
                if (cyVar.c()) {
                    arrayList = this.f293a;
                    arrayList.remove(size);
                }
            } else if (!cyVar.b()) {
                arrayList = this.f293a;
                arrayList.remove(size);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f24715a);
        sb.append("\n");
        Iterator<cy> it = this.f293a.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }
}
