package com.huawei.hms.hatool;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class l0 implements n0 {

    /* renamed from: a, reason: collision with root package name */
    public String f7812a;

    /* renamed from: b, reason: collision with root package name */
    public String f7813b;

    /* renamed from: c, reason: collision with root package name */
    public String f7814c;

    /* renamed from: d, reason: collision with root package name */
    public List<q> f7815d;

    public l0(List<q> list, String str, String str2, String str3) {
        this.f7812a = str;
        this.f7813b = str2;
        this.f7814c = str3;
        this.f7815d = list;
    }

    public final void a() {
        g0.a(b.i(), "backup_event", u0.a(this.f7812a, this.f7814c, this.f7813b));
    }

    @Override // java.lang.Runnable
    public void run() {
        List<q> list = this.f7815d;
        if (list == null || list.size() == 0) {
            y.d("hmsSdk", "failed events is empty");
            return;
        }
        if (q0.a(b.i(), "cached_v2_1", b.k() * 1048576)) {
            y.e("hmsSdk", "The cacheFile is full,Can not writing data! reqID:" + this.f7813b);
            return;
        }
        String strA = u0.a(this.f7812a, this.f7814c);
        List<q> list2 = w.b(b.i(), "cached_v2_1", strA).get(strA);
        if (list2 != null && list2.size() != 0) {
            this.f7815d.addAll(list2);
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<q> it = this.f7815d.iterator();
        while (it.hasNext()) {
            try {
                jSONArray.put(it.next().d());
            } catch (JSONException unused) {
                y.e("hmsSdk", "event to json error");
            }
        }
        String string = jSONArray.toString();
        if (string.length() > b.h() * 1048576) {
            y.e("hmsSdk", "this failed data is too long,can not writing it");
            this.f7815d = null;
            return;
        }
        y.d("hmsSdk", "data send failed, write to cache file...reqID:" + this.f7813b);
        g0.b(b.i(), "cached_v2_1", strA, string);
        a();
    }
}
