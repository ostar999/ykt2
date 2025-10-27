package com.xiaomi.push;

/* loaded from: classes6.dex */
public class ap implements ar {

    /* renamed from: a, reason: collision with root package name */
    private final String f24608a;

    /* renamed from: b, reason: collision with root package name */
    private final String f24609b;

    public ap(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.f24608a = str;
        this.f24609b = str2;
    }

    @Override // com.xiaomi.push.ar
    public String a() {
        return this.f24608a;
    }

    @Override // com.xiaomi.push.ar
    public String b() {
        return this.f24609b;
    }
}
