package com.xiaomi.push;

import android.net.wifi.ScanResult;
import java.util.Comparator;

/* loaded from: classes6.dex */
class ep implements Comparator<ScanResult> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ eo f24768a;

    public ep(eo eoVar) {
        this.f24768a = eoVar;
    }

    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.level - scanResult.level;
    }
}
