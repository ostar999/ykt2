package io.crossbar.autobahn.utils;

import android.util.Log;
import c.h;

/* loaded from: classes8.dex */
class ABALogger implements IABLogger {

    /* renamed from: a, reason: collision with root package name */
    public final String f27148a;

    public ABALogger(String str) {
        this.f27148a = str;
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void a(String str) {
        h.d(this.f27148a, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void b(String str, Throwable th) {
        Log.v(this.f27148a, str, th);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void c(String str) {
        h.c(this.f27148a, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void d(String str) {
        Log.v(this.f27148a, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void e(String str) {
        h.e(this.f27148a, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void a(String str, Throwable th) {
        Log.w(this.f27148a, str, th);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void b(String str) {
        h.a(this.f27148a, str);
    }
}
