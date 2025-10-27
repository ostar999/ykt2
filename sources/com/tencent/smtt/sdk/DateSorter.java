package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.interfaces.IX5DateSorter;

/* loaded from: classes6.dex */
public class DateSorter {
    public static int DAY_COUNT;

    /* renamed from: a, reason: collision with root package name */
    private android.webkit.DateSorter f20795a;

    /* renamed from: b, reason: collision with root package name */
    private IX5DateSorter f20796b;

    static {
        a();
        DAY_COUNT = 5;
    }

    public DateSorter(Context context) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            this.f20795a = new android.webkit.DateSorter(context);
        } else {
            this.f20796b = wVarA.c().h(context);
        }
    }

    private static boolean a() {
        w wVarA = w.a();
        return wVarA != null && wVarA.b();
    }

    public long getBoundary(int i2) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? this.f20795a.getBoundary(i2) : this.f20796b.getBoundary(i2);
    }

    public int getIndex(long j2) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? this.f20795a.getIndex(j2) : this.f20796b.getIndex(j2);
    }

    public String getLabel(int i2) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? this.f20795a.getLabel(i2) : this.f20796b.getLabel(i2);
    }
}
