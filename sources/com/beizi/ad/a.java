package com.beizi.ad;

import android.content.Context;
import android.util.TypedValue;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final a f3663a = new a(320, 50, "320x50_mb");

    /* renamed from: b, reason: collision with root package name */
    public static final a f3664b = new a(R2.attr.bead_margin, 60, "468x60_as");

    /* renamed from: c, reason: collision with root package name */
    public static final a f3665c = new a(320, 100, "320x100_as");

    /* renamed from: d, reason: collision with root package name */
    public static final a f3666d = new a(R2.attr.bl_unEnabled_stroke_color, 90, "728x90_as");

    /* renamed from: e, reason: collision with root package name */
    public static final a f3667e = new a(300, 250, "300x250_as");

    /* renamed from: f, reason: collision with root package name */
    public static final a f3668f = new a(160, 600, "160x600_as");

    /* renamed from: g, reason: collision with root package name */
    public static final a f3669g = new a(-1, -2, "smart_banner");

    /* renamed from: h, reason: collision with root package name */
    public static final a f3670h = new a(-3, -4, "fluid");

    /* renamed from: i, reason: collision with root package name */
    public static final a f3671i = new a(-3, 0, "search_v2");

    /* renamed from: j, reason: collision with root package name */
    private final int f3672j;

    /* renamed from: k, reason: collision with root package name */
    private final int f3673k;

    /* renamed from: l, reason: collision with root package name */
    private final String f3674l;

    public a(int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(i2 == -1 ? "FULL" : String.valueOf(i2));
        sb.append("x");
        sb.append(i3 == -2 ? "AUTO" : String.valueOf(i3));
        sb.append("_as");
        this(i2, i3, sb.toString());
    }

    public int a() {
        return this.f3673k;
    }

    public int b() {
        return this.f3672j;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.f3672j == aVar.f3672j && this.f3673k == aVar.f3673k && this.f3674l.equals(aVar.f3674l);
    }

    public int hashCode() {
        return this.f3674l.hashCode();
    }

    public String toString() {
        return this.f3674l;
    }

    private a(int i2, int i3, String str) {
        if (i2 < 0 && i2 != -1 && i2 != -3) {
            throw new IllegalArgumentException("Invalid width for AdSize: " + i2);
        }
        if (i3 >= 0 || i3 == -2 || i3 == -4) {
            this.f3672j = i2;
            this.f3673k = i3;
            this.f3674l = str;
        } else {
            throw new IllegalArgumentException("Invalid height for AdSize: " + i3);
        }
    }

    public int a(Context context) {
        int i2 = this.f3673k;
        if (i2 == -4 || i2 == -3) {
            return -1;
        }
        return i2 != -2 ? (int) TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics()) : context.getResources().getDisplayMetrics().heightPixels;
    }

    public int b(Context context) {
        int i2 = this.f3672j;
        if (i2 == -4 || i2 == -3) {
            return -1;
        }
        return i2 != -1 ? (int) TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics()) : context.getResources().getDisplayMetrics().widthPixels;
    }
}
