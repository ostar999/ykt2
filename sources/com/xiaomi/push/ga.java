package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.push.et;
import com.xiaomi.push.gc;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class ga extends gj {

    /* renamed from: a, reason: collision with root package name */
    private fw f24916a;

    /* renamed from: a, reason: collision with other field name */
    private fx f467a;

    /* renamed from: a, reason: collision with other field name */
    private Thread f468a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f469a;

    public ga(XMPushService xMPushService, gd gdVar) {
        super(xMPushService, gdVar);
    }

    private fv a(boolean z2) {
        fz fzVar = new fz();
        if (z2) {
            fzVar.a("1");
        }
        byte[] bArrM494a = hq.m494a();
        if (bArrM494a != null) {
            et.j jVar = new et.j();
            jVar.a(a.a(bArrM494a));
            fzVar.a(jVar.m334a(), (String) null);
        }
        return fzVar;
    }

    private void i() throws gn {
        try {
            this.f24916a = new fw(((gj) this).f490a.getInputStream(), this);
            this.f467a = new fx(((gj) this).f490a.getOutputStream(), this);
            gb gbVar = new gb(this, "Blob Reader (" + ((gc) this).f24919b + ")");
            this.f468a = gbVar;
            gbVar.start();
        } catch (Exception e2) {
            throw new gn("Error to init reader and writer", e2);
        }
    }

    @Override // com.xiaomi.push.gj
    /* renamed from: a */
    public synchronized void mo462a() {
        i();
        this.f467a.a();
    }

    @Override // com.xiaomi.push.gj
    public synchronized void a(int i2, Exception exc) {
        fw fwVar = this.f24916a;
        if (fwVar != null) {
            fwVar.b();
            this.f24916a = null;
        }
        fx fxVar = this.f467a;
        if (fxVar != null) {
            try {
                fxVar.b();
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
            this.f467a = null;
            this.f469a = null;
            super.a(i2, exc);
        } else {
            this.f469a = null;
            super.a(i2, exc);
        }
    }

    public void a(fv fvVar) {
        if (fvVar == null) {
            return;
        }
        if (fvVar.m431a()) {
            com.xiaomi.channel.commonutils.logger.b.m117a("[Slim] RCV blob chid=" + fvVar.a() + "; id=" + fvVar.e() + "; errCode=" + fvVar.b() + "; err=" + fvVar.m435c());
        }
        if (fvVar.a() == 0) {
            if ("PING".equals(fvVar.m428a())) {
                com.xiaomi.channel.commonutils.logger.b.m117a("[Slim] RCV ping id=" + fvVar.e());
                h();
            } else if ("CLOSE".equals(fvVar.m428a())) {
                c(13, null);
            }
        }
        Iterator<gc.a> it = ((gc) this).f479a.values().iterator();
        while (it.hasNext()) {
            it.next().a(fvVar);
        }
    }

    @Override // com.xiaomi.push.gc
    @Deprecated
    public void a(gt gtVar) throws gn {
        b(fv.a(gtVar, (String) null));
    }

    @Override // com.xiaomi.push.gc
    public synchronized void a(at.b bVar) {
        fu.a(bVar, c(), this);
    }

    @Override // com.xiaomi.push.gc
    public synchronized void a(String str, String str2) {
        fu.a(str, str2, this);
    }

    @Override // com.xiaomi.push.gj
    /* renamed from: a, reason: collision with other method in class */
    public void mo444a(boolean z2) throws gn {
        if (this.f467a == null) {
            throw new gn("The BlobWriter is null.");
        }
        fv fvVarA = a(z2);
        com.xiaomi.channel.commonutils.logger.b.m117a("[Slim] SND ping id=" + fvVarA.e());
        b(fvVarA);
        g();
    }

    @Override // com.xiaomi.push.gj, com.xiaomi.push.gc
    public void a(fv[] fvVarArr) throws gn {
        for (fv fvVar : fvVarArr) {
            b(fvVar);
        }
    }

    @Override // com.xiaomi.push.gc
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo445a() {
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized byte[] m446a() {
        if (this.f469a == null && !TextUtils.isEmpty(((gc) this).f476a)) {
            String strM731a = com.xiaomi.push.service.bi.m731a();
            StringBuilder sb = new StringBuilder();
            String str = ((gc) this).f476a;
            sb.append(str.substring(str.length() / 2));
            sb.append(strM731a.substring(strM731a.length() / 2));
            this.f469a = com.xiaomi.push.service.bc.a(((gc) this).f476a.getBytes(), sb.toString().getBytes());
        }
        return this.f469a;
    }

    @Override // com.xiaomi.push.gc
    public void b(fv fvVar) throws gn {
        fx fxVar = this.f467a;
        if (fxVar == null) {
            throw new gn("the writer is null.");
        }
        try {
            int iA = fxVar.a(fvVar);
            ((gc) this).f24921d = System.currentTimeMillis();
            String strF = fvVar.f();
            if (!TextUtils.isEmpty(strF)) {
                hh.a(((gc) this).f475a, strF, iA, false, true, System.currentTimeMillis());
            }
            Iterator<gc.a> it = ((gc) this).f482b.values().iterator();
            while (it.hasNext()) {
                it.next().a(fvVar);
            }
        } catch (Exception e2) {
            throw new gn(e2);
        }
    }

    public void b(gt gtVar) {
        if (gtVar == null) {
            return;
        }
        Iterator<gc.a> it = ((gc) this).f479a.values().iterator();
        while (it.hasNext()) {
            it.next().a(gtVar);
        }
    }
}
