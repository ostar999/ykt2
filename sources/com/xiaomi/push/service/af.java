package com.xiaomi.push.service;

import android.content.Context;
import android.os.Messenger;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.di;
import com.xiaomi.push.fv;
import com.xiaomi.push.gc;
import com.xiaomi.push.gn;
import com.xiaomi.push.gt;
import com.xiaomi.push.hw;
import com.xiaomi.push.iq;
import com.xiaomi.push.it;
import com.xiaomi.push.jb;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import com.xiaomi.push.jq;
import com.xiaomi.push.jv;
import com.xiaomi.push.service.at;
import java.nio.ByteBuffer;
import java.util.Map;

/* loaded from: classes6.dex */
final class af {
    public static fv a(XMPushService xMPushService, byte[] bArr) {
        jb jbVar = new jb();
        try {
            jp.a(jbVar, bArr);
            return a(t.a((Context) xMPushService), xMPushService, jbVar);
        } catch (jv e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    public static fv a(s sVar, Context context, jb jbVar) throws NumberFormatException {
        try {
            fv fvVar = new fv();
            fvVar.a(5);
            fvVar.c(sVar.f1090a);
            fvVar.b(a(jbVar));
            fvVar.a("SECMSG", "message");
            String str = sVar.f1090a;
            jbVar.f752a.f673a = str.substring(0, str.indexOf("@"));
            jbVar.f752a.f677c = str.substring(str.indexOf("/") + 1);
            fvVar.a(jp.a(jbVar), sVar.f25714c);
            fvVar.a((short) 1);
            com.xiaomi.channel.commonutils.logger.b.m117a("try send mi push message. packagename:" + jbVar.f757b + " action:" + jbVar.f750a);
            return fvVar;
        } catch (NullPointerException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    public static jb a(String str, String str2) {
        je jeVar = new je();
        jeVar.b(str2);
        jeVar.c("package uninstalled");
        jeVar.a(gt.i());
        jeVar.a(false);
        return a(str, str2, jeVar, hw.Notification);
    }

    public static <T extends jq<T, ?>> jb a(String str, String str2, T t2, hw hwVar) {
        byte[] bArrA = jp.a(t2);
        jb jbVar = new jb();
        it itVar = new it();
        itVar.f672a = 5L;
        itVar.f673a = "fakeid";
        jbVar.a(itVar);
        jbVar.a(ByteBuffer.wrap(bArrA));
        jbVar.a(hwVar);
        jbVar.b(true);
        jbVar.b(str);
        jbVar.a(false);
        jbVar.a(str2);
        return jbVar;
    }

    private static String a(jb jbVar) {
        Map<String, String> map;
        iq iqVar = jbVar.f751a;
        if (iqVar != null && (map = iqVar.f661b) != null) {
            String str = map.get("ext_traffic_source_pkg");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return jbVar.f757b;
    }

    public static String a(String str) {
        return str + ".permission.MIPUSH_RECEIVE";
    }

    public static void a(XMPushService xMPushService) {
        s sVarA = t.a(xMPushService.getApplicationContext());
        if (sVarA != null) {
            at.b bVarA = t.a(xMPushService.getApplicationContext()).a(xMPushService);
            a(xMPushService, bVarA);
            at.a().a(bVarA);
            bk.a(xMPushService).a(new ag("GAID", 172800L, xMPushService, sVarA));
        }
    }

    public static void a(XMPushService xMPushService, jb jbVar) {
        di.a(jbVar.b(), xMPushService.getApplicationContext(), jbVar, -1);
        gc gcVarM695a = xMPushService.m695a();
        if (gcVarM695a == null) {
            throw new gn("try send msg while connection is null.");
        }
        if (!gcVarM695a.mo445a()) {
            throw new gn("Don't support XMPP connection.");
        }
        fv fvVarA = a(t.a((Context) xMPushService), xMPushService, jbVar);
        if (fvVarA != null) {
            gcVarM695a.b(fvVarA);
        }
    }

    public static void a(XMPushService xMPushService, at.b bVar) {
        bVar.a((Messenger) null);
        bVar.a(new ah(xMPushService));
    }

    public static void a(XMPushService xMPushService, String str, byte[] bArr) throws gn {
        di.a(str, xMPushService.getApplicationContext(), bArr);
        gc gcVarM695a = xMPushService.m695a();
        if (gcVarM695a == null) {
            throw new gn("try send msg while connection is null.");
        }
        if (!gcVarM695a.mo445a()) {
            throw new gn("Don't support XMPP connection.");
        }
        fv fvVarA = a(xMPushService, bArr);
        if (fvVarA != null) {
            gcVarM695a.b(fvVarA);
        } else {
            w.a(xMPushService, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "not a valid message");
        }
    }
}
