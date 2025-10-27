package com.xiaomi.push.service;

import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.push.cy;
import com.xiaomi.push.dc;
import com.xiaomi.push.dl;
import com.xiaomi.push.et;
import com.xiaomi.push.fr;
import com.xiaomi.push.fv;
import com.xiaomi.push.gd;
import com.xiaomi.push.gq;
import com.xiaomi.push.gr;
import com.xiaomi.push.gs;
import com.xiaomi.push.gt;
import com.xiaomi.push.hh;
import com.xiaomi.push.hq;
import com.xiaomi.push.service.at;
import java.util.Date;

/* loaded from: classes6.dex */
public class as {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f25589a;

    public as(XMPushService xMPushService) {
        this.f25589a = xMPushService;
    }

    private void a(gq gqVar) {
        String strC = gqVar.c();
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        String[] strArrSplit = strC.split(com.alipay.sdk.util.h.f3376b);
        cy cyVarA = dc.a().a(gd.a(), false);
        if (cyVarA == null || strArrSplit.length <= 0) {
            return;
        }
        cyVarA.a(strArrSplit);
        this.f25589a.a(20, (Exception) null);
        this.f25589a.a(true);
    }

    private void b(gt gtVar) {
        at.b bVarA;
        String strL = gtVar.l();
        String strK = gtVar.k();
        if (TextUtils.isEmpty(strL) || TextUtils.isEmpty(strK) || (bVarA = at.a().a(strK, strL)) == null) {
            return;
        }
        hh.a(this.f25589a, bVarA.f1012a, hh.a(gtVar.mo468a()), true, true, System.currentTimeMillis());
    }

    private void c(fv fvVar) {
        at.b bVarA;
        String strG = fvVar.g();
        String string = Integer.toString(fvVar.a());
        if (TextUtils.isEmpty(strG) || TextUtils.isEmpty(string) || (bVarA = at.a().a(string, strG)) == null) {
            return;
        }
        hh.a(this.f25589a, bVarA.f1012a, fvVar.c(), true, true, System.currentTimeMillis());
    }

    public void a(fv fvVar) {
        if (5 != fvVar.a()) {
            c(fvVar);
        }
        try {
            b(fvVar);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a("handle Blob chid = " + fvVar.a() + " cmd = " + fvVar.m428a() + " packetid = " + fvVar.e() + " failure ", e2);
        }
    }

    public void a(gt gtVar) {
        if (!"5".equals(gtVar.k())) {
            b(gtVar);
        }
        String strK = gtVar.k();
        if (TextUtils.isEmpty(strK)) {
            strK = "1";
            gtVar.l("1");
        }
        if (strK.equals("0")) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Received wrong packet with chid = 0 : " + gtVar.mo468a());
        }
        if (gtVar instanceof gr) {
            gq gqVarA = gtVar.a("kick");
            if (gqVarA != null) {
                String strL = gtVar.l();
                String strA = gqVarA.a("type");
                String strA2 = gqVarA.a("reason");
                com.xiaomi.channel.commonutils.logger.b.m117a("kicked by server, chid=" + strK + " res=" + at.b.a(strL) + " type=" + strA + " reason=" + strA2);
                if (!"wait".equals(strA)) {
                    this.f25589a.a(strK, strL, 3, strA2, strA);
                    at.a().m721a(strK, strL);
                    return;
                }
                at.b bVarA = at.a().a(strK, strL);
                if (bVarA != null) {
                    this.f25589a.a(bVarA);
                    bVarA.a(at.c.unbind, 3, 0, strA2, strA);
                    return;
                }
                return;
            }
        } else if (gtVar instanceof gs) {
            gs gsVar = (gs) gtVar;
            if ("redir".equals(gsVar.b())) {
                gq gqVarA2 = gsVar.a("hosts");
                if (gqVarA2 != null) {
                    a(gqVarA2);
                    return;
                }
                return;
            }
        }
        this.f25589a.b().a(this.f25589a, strK, gtVar);
    }

    public void b(fv fvVar) throws RemoteException {
        StringBuilder sb;
        String strM399a;
        String string;
        at.c cVar;
        int i2;
        int i3;
        String strM428a = fvVar.m428a();
        if (fvVar.a() != 0) {
            String string2 = Integer.toString(fvVar.a());
            if (!"SECMSG".equals(fvVar.m428a())) {
                if (!"BIND".equals(strM428a)) {
                    if ("KICK".equals(strM428a)) {
                        et.g gVarA = et.g.a(fvVar.m432a());
                        String strG = fvVar.g();
                        String strM394a = gVarA.m394a();
                        String strM396b = gVarA.m396b();
                        com.xiaomi.channel.commonutils.logger.b.m117a("kicked by server, chid=" + string2 + " res= " + at.b.a(strG) + " type=" + strM394a + " reason=" + strM396b);
                        if (!"wait".equals(strM394a)) {
                            this.f25589a.a(string2, strG, 3, strM396b, strM394a);
                            at.a().m721a(string2, strG);
                            return;
                        }
                        at.b bVarA = at.a().a(string2, strG);
                        if (bVarA != null) {
                            this.f25589a.a(bVarA);
                            bVarA.a(at.c.unbind, 3, 0, strM396b, strM394a);
                            return;
                        }
                        return;
                    }
                    return;
                }
                et.d dVarA = et.d.a(fvVar.m432a());
                String strG2 = fvVar.g();
                at.b bVarA2 = at.a().a(string2, strG2);
                if (bVarA2 == null) {
                    return;
                }
                if (dVarA.m373a()) {
                    com.xiaomi.channel.commonutils.logger.b.m117a("SMACK: channel bind succeeded, chid=" + fvVar.a());
                    bVarA2.a(at.c.binded, 1, 0, (String) null, (String) null);
                    return;
                }
                String strM372a = dVarA.m372a();
                if ("auth".equals(strM372a)) {
                    if ("invalid-sig".equals(dVarA.m374b())) {
                        com.xiaomi.channel.commonutils.logger.b.m117a("SMACK: bind error invalid-sig token = " + bVarA2.f25593c + " sec = " + bVarA2.f25598h);
                        hq.a(0, fr.BIND_INVALID_SIG.a(), 1, null, 0);
                    }
                    cVar = at.c.unbind;
                    i2 = 1;
                    i3 = 5;
                } else {
                    if (!"cancel".equals(strM372a)) {
                        if ("wait".equals(strM372a)) {
                            this.f25589a.a(bVarA2);
                            bVarA2.a(at.c.unbind, 1, 7, dVarA.m374b(), strM372a);
                        }
                        string = "SMACK: channel bind failed, chid=" + string2 + " reason=" + dVarA.m374b();
                        com.xiaomi.channel.commonutils.logger.b.m117a(string);
                    }
                    cVar = at.c.unbind;
                    i2 = 1;
                    i3 = 7;
                }
                bVarA2.a(cVar, i2, i3, dVarA.m374b(), strM372a);
                at.a().m721a(string2, strG2);
                string = "SMACK: channel bind failed, chid=" + string2 + " reason=" + dVarA.m374b();
                com.xiaomi.channel.commonutils.logger.b.m117a(string);
            }
            if (!fvVar.m431a()) {
                this.f25589a.b().a(this.f25589a, string2, fvVar);
                return;
            }
            sb = new StringBuilder();
            sb.append("Recv SECMSG errCode = ");
            sb.append(fvVar.b());
            sb.append(" errStr = ");
            strM399a = fvVar.m435c();
        } else {
            if ("PING".equals(strM428a)) {
                byte[] bArrM432a = fvVar.m432a();
                if (bArrM432a != null && bArrM432a.length > 0) {
                    et.j jVarA = et.j.a(bArrM432a);
                    if (jVarA.m407b()) {
                        bi.a().a(jVarA.m405a());
                    }
                }
                if (!"com.xiaomi.xmsf".equals(this.f25589a.getPackageName())) {
                    this.f25589a.m697a();
                }
                if ("1".equals(fvVar.e())) {
                    com.xiaomi.channel.commonutils.logger.b.m117a("received a server ping");
                } else {
                    hq.b();
                }
                this.f25589a.m700b();
                return;
            }
            if ("SYNC".equals(strM428a)) {
                if ("CONF".equals(fvVar.m434b())) {
                    bi.a().a(et.b.a(fvVar.m432a()));
                    return;
                }
                if (TextUtils.equals("U", fvVar.m434b())) {
                    et.k kVarA = et.k.a(fvVar.m432a());
                    dl.a(this.f25589a).a(kVarA.m409a(), kVarA.m412b(), new Date(kVarA.m408a()), new Date(kVarA.m411b()), kVarA.c() * 1024, kVarA.e());
                    fv fvVar2 = new fv();
                    fvVar2.a(0);
                    fvVar2.a(fvVar.m428a(), "UCA");
                    fvVar2.a(fvVar.e());
                    XMPushService xMPushService = this.f25589a;
                    xMPushService.a(new be(xMPushService, fvVar2));
                    return;
                }
                if (!TextUtils.equals("P", fvVar.m434b())) {
                    return;
                }
                et.i iVarA = et.i.a(fvVar.m432a());
                fv fvVar3 = new fv();
                fvVar3.a(0);
                fvVar3.a(fvVar.m428a(), "PCA");
                fvVar3.a(fvVar.e());
                et.i iVar = new et.i();
                if (iVarA.m403a()) {
                    iVar.a(iVarA.m402a());
                }
                fvVar3.a(iVar.m334a(), (String) null);
                XMPushService xMPushService2 = this.f25589a;
                xMPushService2.a(new be(xMPushService2, fvVar3));
                sb = new StringBuilder();
                sb.append("ACK msgP: id = ");
                strM399a = fvVar.e();
            } else {
                if (!"NOTIFY".equals(fvVar.m428a())) {
                    return;
                }
                et.h hVarA = et.h.a(fvVar.m432a());
                sb = new StringBuilder();
                sb.append("notify by server err = ");
                sb.append(hVarA.c());
                sb.append(" desc = ");
                strM399a = hVarA.m399a();
            }
        }
        sb.append(strM399a);
        string = sb.toString();
        com.xiaomi.channel.commonutils.logger.b.m117a(string);
    }
}
