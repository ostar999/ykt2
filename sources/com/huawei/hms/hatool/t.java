package com.huawei.hms.hatool;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes4.dex */
public class t {

    /* renamed from: a, reason: collision with root package name */
    public List<q> f7864a;

    /* renamed from: b, reason: collision with root package name */
    public String f7865b;

    /* renamed from: c, reason: collision with root package name */
    public String f7866c;

    /* renamed from: d, reason: collision with root package name */
    public String f7867d;

    public t(List<q> list, String str, String str2, String str3) {
        this.f7864a = list;
        this.f7865b = str;
        this.f7866c = str2;
        this.f7867d = str3;
    }

    public void a() {
        if (!"_default_config_tag".equals(this.f7866c)) {
            a(this.f7864a, this.f7866c, this.f7865b);
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (q qVar : this.f7864a) {
            String strC = qVar.c();
            if (TextUtils.isEmpty(strC) || "oper".equals(strC)) {
                arrayList4.add(qVar);
            } else if ("maint".equals(strC)) {
                arrayList.add(qVar);
            } else if ("preins".equals(strC)) {
                arrayList2.add(qVar);
            } else if ("diffprivacy".equals(strC)) {
                arrayList3.add(qVar);
            }
        }
        a(arrayList4, "oper", "_default_config_tag");
        a(arrayList, "maint", "_default_config_tag");
        a(arrayList2, "preins", "_default_config_tag");
        a(arrayList3, "diffprivacy", "_default_config_tag");
    }

    public final void a(List<q> list, String str, String str2) {
        if (list.isEmpty()) {
            return;
        }
        int size = (list.size() / 500) + 1;
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = i2 * 500;
            List<q> listSubList = list.subList(i3, Math.min(list.size(), i3 + 500));
            String strReplace = UUID.randomUUID().toString().replace("-", "");
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jB = c.b(str2, str) * 86400000;
            ArrayList arrayList = new ArrayList();
            for (q qVar : listSubList) {
                if (!q0.a(qVar.b(), jCurrentTimeMillis, jB)) {
                    arrayList.add(qVar);
                }
            }
            if (arrayList.size() > 0) {
                new u(str2, str, this.f7867d, arrayList, strReplace).a();
            } else {
                y.e("hmsSdk", "No data to report handler");
            }
        }
    }
}
