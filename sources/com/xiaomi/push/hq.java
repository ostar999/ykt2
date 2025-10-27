package com.xiaomi.push;

import com.xiaomi.push.hm;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public class hq {

    /* renamed from: a, reason: collision with root package name */
    private static final int f25074a = fr.PING_RTT.a();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        static Hashtable<Integer, Long> f25075a = new Hashtable<>();
    }

    public static void a() {
        a(0, f25074a);
    }

    public static void a(int i2) {
        fs fsVarM490a = ho.m488a().m490a();
        fsVarM490a.a(fr.CHANNEL_STATS_COUNTER.a());
        fsVarM490a.c(i2);
        ho.m488a().a(fsVarM490a);
    }

    public static synchronized void a(int i2, int i3) {
        if (i3 < 16777215) {
            a.f25075a.put(Integer.valueOf((i2 << 24) | i3), Long.valueOf(System.currentTimeMillis()));
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("stats key should less than 16777215");
        }
    }

    public static void a(int i2, int i3, int i4, String str, int i5) {
        fs fsVarM490a = ho.m488a().m490a();
        fsVarM490a.a((byte) i2);
        fsVarM490a.a(i3);
        fsVarM490a.b(i4);
        fsVarM490a.b(str);
        fsVarM490a.c(i5);
        ho.m488a().a(fsVarM490a);
    }

    public static synchronized void a(int i2, int i3, String str, int i4) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i5 = (i2 << 24) | i3;
        if (a.f25075a.containsKey(Integer.valueOf(i5))) {
            fs fsVarM490a = ho.m488a().m490a();
            fsVarM490a.a(i3);
            fsVarM490a.b((int) (jCurrentTimeMillis - a.f25075a.get(Integer.valueOf(i5)).longValue()));
            fsVarM490a.b(str);
            if (i4 > -1) {
                fsVarM490a.c(i4);
            }
            ho.m488a().a(fsVarM490a);
            a.f25075a.remove(Integer.valueOf(i3));
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("stats key not found");
        }
    }

    public static void a(XMPushService xMPushService, at.b bVar) {
        new hj(xMPushService, bVar).a();
    }

    public static void a(String str, int i2, Exception exc) {
        fs fsVarM490a = ho.m488a().m490a();
        if (i2 > 0) {
            fsVarM490a.a(fr.GSLB_REQUEST_SUCCESS.a());
            fsVarM490a.b(str);
            fsVarM490a.b(i2);
            ho.m488a().a(fsVarM490a);
            return;
        }
        try {
            hm.a aVarA = hm.a(exc);
            fsVarM490a.a(aVarA.f25064a.a());
            fsVarM490a.c(aVarA.f534a);
            fsVarM490a.b(str);
            ho.m488a().a(fsVarM490a);
        } catch (NullPointerException unused) {
        }
    }

    public static void a(String str, Exception exc) {
        try {
            hm.a aVarB = hm.b(exc);
            fs fsVarM490a = ho.m488a().m490a();
            fsVarM490a.a(aVarB.f25064a.a());
            fsVarM490a.c(aVarB.f534a);
            fsVarM490a.b(str);
            ho.m488a().a(fsVarM490a);
        } catch (NullPointerException unused) {
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m494a() {
        ft ftVarM491a = ho.m488a().m491a();
        if (ftVarM491a != null) {
            return jp.a(ftVarM491a);
        }
        return null;
    }

    public static void b() {
        a(0, f25074a, null, -1);
    }

    public static void b(String str, Exception exc) {
        try {
            hm.a aVarD = hm.d(exc);
            fs fsVarM490a = ho.m488a().m490a();
            fsVarM490a.a(aVarD.f25064a.a());
            fsVarM490a.c(aVarD.f534a);
            fsVarM490a.b(str);
            ho.m488a().a(fsVarM490a);
        } catch (NullPointerException unused) {
        }
    }
}
