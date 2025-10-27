package com.vivo.push.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private String f24394a;

    /* renamed from: b, reason: collision with root package name */
    private String f24395b;

    public a(String str, String str2) {
        this.f24394a = str;
        this.f24395b = str2;
    }

    public final String a() {
        return this.f24394a;
    }

    public final String b() {
        return this.f24395b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || a.class != obj.getClass()) {
            return false;
        }
        a aVar = (a) obj;
        String str = this.f24394a;
        if (str == null) {
            if (aVar.f24394a != null) {
                return false;
            }
        } else if (!str.equals(aVar.f24394a)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        String str = this.f24394a;
        return (str == null ? 0 : str.hashCode()) + 31;
    }

    public final String toString() {
        return "ConfigItem{mKey='" + this.f24394a + CharPool.SINGLE_QUOTE + ", mValue='" + this.f24395b + CharPool.SINGLE_QUOTE + '}';
    }
}
