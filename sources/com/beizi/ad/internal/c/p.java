package com.beizi.ad.internal.c;

import cn.hutool.core.text.CharPool;

/* loaded from: classes2.dex */
public class p {

    /* renamed from: a, reason: collision with root package name */
    public final String f4118a;

    /* renamed from: b, reason: collision with root package name */
    public final int f4119b;

    /* renamed from: c, reason: collision with root package name */
    public final String f4120c;

    public p(String str, int i2, String str2) {
        this.f4118a = str;
        this.f4119b = i2;
        this.f4120c = str2;
    }

    public String toString() {
        return "SourceInfo{url='" + this.f4118a + CharPool.SINGLE_QUOTE + ", length=" + this.f4119b + ", mime='" + this.f4120c + CharPool.SINGLE_QUOTE + '}';
    }
}
