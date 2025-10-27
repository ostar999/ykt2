package com.xiaomi.push;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class aq {

    /* renamed from: a, reason: collision with root package name */
    public int f24610a;

    /* renamed from: a, reason: collision with other field name */
    public String f192a;

    /* renamed from: a, reason: collision with other field name */
    public Map<String, String> f193a = new HashMap();

    public String a() {
        return this.f192a;
    }

    public String toString() {
        return String.format("resCode = %1$d, headers = %2$s, response = %3$s", Integer.valueOf(this.f24610a), this.f193a.toString(), this.f192a);
    }
}
