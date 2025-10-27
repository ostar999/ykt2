package com.alipay.security.mobile.module.d;

import cn.hutool.core.date.DatePattern;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private File f3428a;

    /* renamed from: b, reason: collision with root package name */
    private com.alipay.security.mobile.module.http.v2.a f3429b;

    public b(String str, com.alipay.security.mobile.module.http.v2.a aVar) {
        this.f3428a = null;
        this.f3429b = null;
        this.f3428a = new File(str);
        this.f3429b = aVar;
    }

    private static String a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "id");
            jSONObject.put("error", str);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private void b() {
        new Thread(new c(this)).start();
    }

    public final synchronized void a() {
        File file = this.f3428a;
        if (file == null) {
            return;
        }
        if (file.exists() && this.f3428a.isDirectory() && this.f3428a.list().length != 0) {
            ArrayList arrayList = new ArrayList();
            for (String str : this.f3428a.list()) {
                arrayList.add(str);
            }
            Collections.sort(arrayList);
            String str2 = (String) arrayList.get(arrayList.size() - 1);
            int size = arrayList.size();
            if (str2.equals(new SimpleDateFormat(DatePattern.PURE_DATE_PATTERN).format(Calendar.getInstance().getTime()) + ".log")) {
                if (arrayList.size() < 2) {
                    return;
                }
                str2 = (String) arrayList.get(arrayList.size() - 2);
                size--;
            }
            if (!this.f3429b.a(a(com.alipay.security.mobile.module.a.b.a(this.f3428a.getAbsolutePath(), str2)))) {
                size--;
            }
            for (int i2 = 0; i2 < size; i2++) {
                new File(this.f3428a, (String) arrayList.get(i2)).delete();
            }
        }
    }
}
