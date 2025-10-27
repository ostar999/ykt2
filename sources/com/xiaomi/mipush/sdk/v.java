package com.xiaomi.mipush.sdk;

import android.net.wifi.ScanResult;
import java.util.Comparator;

/* loaded from: classes6.dex */
final class v implements Comparator<ScanResult> {
    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.level - scanResult.level;
    }
}
