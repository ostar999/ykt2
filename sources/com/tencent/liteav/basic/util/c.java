package com.tencent.liteav.basic.util;

import android.os.SystemClock;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final String f18707a;

    /* renamed from: b, reason: collision with root package name */
    private final int f18708b;

    /* renamed from: c, reason: collision with root package name */
    private int f18709c;

    /* renamed from: d, reason: collision with root package name */
    private int f18710d;

    /* renamed from: e, reason: collision with root package name */
    private long f18711e;

    public c(String str, int i2) {
        this.f18707a = str;
        this.f18708b = (int) Math.max(i2, TimeUnit.SECONDS.toMillis(1L));
        b();
    }

    public void a() {
        this.f18709c++;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = this.f18711e;
        if (j2 == 0) {
            this.f18711e = SystemClock.elapsedRealtime();
        } else if (jElapsedRealtime - j2 >= this.f18708b) {
            TXCLog.i("FPSMeter", "meter name: %s fps: %.2f", this.f18707a, Float.valueOf(((this.f18709c - this.f18710d) * 1000.0f) / (jElapsedRealtime - j2)));
            this.f18711e = jElapsedRealtime;
            this.f18710d = this.f18709c;
        }
    }

    public void b() {
        this.f18709c = 0;
        this.f18710d = 0;
        this.f18711e = 0L;
    }
}
