package com.mobile.auth.i;

import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class g {
    public abstract String a();

    public abstract String a(String str);

    public abstract JSONObject b();

    public String u(String str) {
        return com.mobile.auth.l.d.a(a(str)).toLowerCase();
    }
}
