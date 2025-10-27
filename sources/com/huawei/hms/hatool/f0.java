package com.huawei.hms.hatool;

import cn.hutool.core.text.StrPool;
import java.util.Calendar;
import java.util.UUID;

/* loaded from: classes4.dex */
public class f0 {

    /* renamed from: a, reason: collision with root package name */
    public long f7727a = 1800000;

    /* renamed from: b, reason: collision with root package name */
    public volatile boolean f7728b = false;

    /* renamed from: c, reason: collision with root package name */
    public a f7729c = null;

    public class a {

        /* renamed from: a, reason: collision with root package name */
        public String f7730a = UUID.randomUUID().toString().replace("-", "");

        /* renamed from: b, reason: collision with root package name */
        public boolean f7731b;

        /* renamed from: c, reason: collision with root package name */
        public long f7732c;

        public a(long j2) {
            this.f7730a += StrPool.UNDERLINE + j2;
            this.f7732c = j2;
            this.f7731b = true;
            f0.this.f7728b = false;
        }

        public void a(long j2) {
            if (f0.this.f7728b) {
                f0.this.f7728b = false;
                b(j2);
            } else if (b(this.f7732c, j2) || a(this.f7732c, j2)) {
                b(j2);
            } else {
                this.f7732c = j2;
                this.f7731b = false;
            }
        }

        public final boolean a(long j2, long j3) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(j3);
            return (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) ? false : true;
        }

        public final void b(long j2) {
            y.c("hmsSdk", "getNewSession() session is flush!");
            String string = UUID.randomUUID().toString();
            this.f7730a = string;
            this.f7730a = string.replace("-", "");
            this.f7730a += StrPool.UNDERLINE + j2;
            this.f7732c = j2;
            this.f7731b = true;
        }

        public final boolean b(long j2, long j3) {
            return j3 - j2 >= f0.this.f7727a;
        }
    }

    public String a() {
        a aVar = this.f7729c;
        if (aVar != null) {
            return aVar.f7730a;
        }
        y.f("hmsSdk", "getSessionName(): session not prepared. onEvent() must be called first.");
        return "";
    }

    public void a(long j2) {
        a aVar = this.f7729c;
        if (aVar != null) {
            aVar.a(j2);
        } else {
            y.c("hmsSdk", "Session is first flush");
            this.f7729c = new a(j2);
        }
    }

    public boolean b() {
        a aVar = this.f7729c;
        if (aVar != null) {
            return aVar.f7731b;
        }
        y.f("hmsSdk", "isFirstEvent(): session not prepared. onEvent() must be called first.");
        return false;
    }
}
