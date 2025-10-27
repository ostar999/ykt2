package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.au;
import com.xiaomi.push.kh;
import com.xiaomi.push.service.XMPushService;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/* loaded from: classes6.dex */
public class ho {

    /* renamed from: a, reason: collision with root package name */
    private int f25071a;

    /* renamed from: a, reason: collision with other field name */
    private long f540a;

    /* renamed from: a, reason: collision with other field name */
    private hn f542a;

    /* renamed from: a, reason: collision with other field name */
    private String f543a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f544a = false;

    /* renamed from: a, reason: collision with other field name */
    private au f541a = au.a();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        static final ho f25072a = new ho();
    }

    private fs a(au.a aVar) {
        if (aVar.f196a == 0) {
            Object obj = aVar.f197a;
            if (obj instanceof fs) {
                return (fs) obj;
            }
            return null;
        }
        fs fsVarM490a = m490a();
        fsVarM490a.a(fr.CHANNEL_STATS_COUNTER.a());
        fsVarM490a.c(aVar.f196a);
        fsVarM490a.c(aVar.f198a);
        return fsVarM490a;
    }

    private ft a(int i2) {
        ArrayList arrayList = new ArrayList();
        ft ftVar = new ft(this.f543a, arrayList);
        if (!as.d(this.f542a.f537a)) {
            ftVar.a(j.l(this.f542a.f537a));
        }
        kj kjVar = new kj(i2);
        kb kbVarA = new kh.a().a(kjVar);
        try {
            ftVar.b(kbVarA);
        } catch (jv unused) {
        }
        LinkedList<au.a> linkedListM208a = this.f541a.m208a();
        while (linkedListM208a.size() > 0) {
            try {
                fs fsVarA = a(linkedListM208a.getLast());
                if (fsVarA != null) {
                    fsVarA.b(kbVarA);
                }
                if (kjVar.a_() > i2) {
                    break;
                }
                if (fsVarA != null) {
                    arrayList.add(fsVarA);
                }
                linkedListM208a.removeLast();
            } catch (jv | NoSuchElementException unused2) {
            }
        }
        return ftVar;
    }

    public static hn a() {
        hn hnVar;
        ho hoVar = a.f25072a;
        synchronized (hoVar) {
            hnVar = hoVar.f542a;
        }
        return hnVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static ho m488a() {
        return a.f25072a;
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m489a() {
        if (!this.f544a || System.currentTimeMillis() - this.f540a <= this.f25071a) {
            return;
        }
        this.f544a = false;
        this.f540a = 0L;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized fs m490a() {
        fs fsVar;
        fsVar = new fs();
        fsVar.a(as.m201a((Context) this.f542a.f537a));
        fsVar.f433a = (byte) 0;
        fsVar.f437b = 1;
        fsVar.d((int) (System.currentTimeMillis() / 1000));
        return fsVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ft m491a() {
        ft ftVarA;
        if (b()) {
            ftVarA = a(!as.d(this.f542a.f537a) ? R2.attr.arcThumbRadiusEnlarges : R2.attr.bl_unPressed_gradient_centerY);
        } else {
            ftVarA = null;
        }
        return ftVarA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m492a(int i2) {
        if (i2 > 0) {
            int i3 = i2 * 1000;
            if (i3 > 604800000) {
                i3 = 604800000;
            }
            if (this.f25071a == i3 && this.f544a) {
                return;
            }
            this.f544a = true;
            this.f540a = System.currentTimeMillis();
            this.f25071a = i3;
            com.xiaomi.channel.commonutils.logger.b.c("enable dot duration = " + i3 + " start = " + this.f540a);
        }
    }

    public synchronized void a(fs fsVar) {
        this.f541a.a(fsVar);
    }

    public synchronized void a(XMPushService xMPushService) {
        this.f542a = new hn(xMPushService);
        this.f543a = "";
        com.xiaomi.push.service.bi.a().a(new hp(this));
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m493a() {
        return this.f544a;
    }

    public boolean b() {
        m489a();
        return this.f544a && this.f541a.m207a() > 0;
    }
}
