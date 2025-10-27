package com.tencent.tbs.logger.file.naming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes6.dex */
public class a implements b {

    /* renamed from: a, reason: collision with root package name */
    public ThreadLocal<SimpleDateFormat> f21680a = new C0359a(this);

    /* renamed from: com.tencent.tbs.logger.file.naming.a$a, reason: collision with other inner class name */
    public class C0359a extends ThreadLocal<SimpleDateFormat> {
        public C0359a(a aVar) {
        }

        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        }
    }

    public String a(com.tencent.tbs.logger.b bVar) {
        long jCurrentTimeMillis = bVar != null ? bVar.f21641a : System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = this.f21680a.get();
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date(jCurrentTimeMillis));
    }

    public boolean a() {
        return true;
    }
}
