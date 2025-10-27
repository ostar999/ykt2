package org.repackage.com.meizu.flyme.openidsdk;

import cn.hutool.core.text.CharPool;

/* loaded from: classes9.dex */
class ValueData {

    /* renamed from: a, reason: collision with root package name */
    public String f28002a;

    /* renamed from: b, reason: collision with root package name */
    public int f28003b;

    /* renamed from: c, reason: collision with root package name */
    public long f28004c = System.currentTimeMillis() + 86400000;

    public ValueData(String str, int i2) {
        this.f28002a = str;
        this.f28003b = i2;
    }

    public String toString() {
        return "ValueData{value='" + this.f28002a + CharPool.SINGLE_QUOTE + ", code=" + this.f28003b + ", expired=" + this.f28004c + '}';
    }
}
