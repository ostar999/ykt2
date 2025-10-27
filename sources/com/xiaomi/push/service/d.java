package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.xiaomi.push.fv;
import com.xiaomi.push.gr;
import com.xiaomi.push.gs;
import com.xiaomi.push.gt;
import com.xiaomi.push.gv;
import com.xiaomi.push.service.at;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private x f25680a = new x();

    public static String a(at.b bVar) {
        StringBuilder sb;
        String str;
        if (Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(bVar.f25597g)) {
            sb = new StringBuilder();
            sb.append(bVar.f1012a);
            str = ".permission.MIMC_RECEIVE";
        } else {
            sb = new StringBuilder();
            sb.append(bVar.f1012a);
            str = ".permission.MIPUSH_RECEIVE";
        }
        sb.append(str);
        return sb.toString();
    }

    private static void a(Context context, Intent intent, at.b bVar) {
        if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, a(bVar));
        }
    }

    public at.b a(fv fvVar) {
        Collection<at.b> collectionM717a = at.a().m717a(Integer.toString(fvVar.a()));
        if (collectionM717a.isEmpty()) {
            return null;
        }
        Iterator<at.b> it = collectionM717a.iterator();
        if (collectionM717a.size() == 1) {
            return it.next();
        }
        String strG = fvVar.g();
        while (it.hasNext()) {
            at.b next = it.next();
            if (TextUtils.equals(strG, next.f1015b)) {
                return next;
            }
        }
        return null;
    }

    public at.b a(gt gtVar) {
        Collection<at.b> collectionM717a = at.a().m717a(gtVar.k());
        if (collectionM717a.isEmpty()) {
            return null;
        }
        Iterator<at.b> it = collectionM717a.iterator();
        if (collectionM717a.size() == 1) {
            return it.next();
        }
        String strM = gtVar.m();
        String strL = gtVar.l();
        while (it.hasNext()) {
            at.b next = it.next();
            if (TextUtils.equals(strM, next.f1015b) || TextUtils.equals(strL, next.f1015b)) {
                return next;
            }
        }
        return null;
    }

    public void a(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.service_started");
        context.sendBroadcast(intent);
    }

    public void a(Context context, at.b bVar, int i2) throws RemoteException {
        if ("5".equalsIgnoreCase(bVar.f25597g)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_closed");
        intent.setPackage(bVar.f1012a);
        intent.putExtra(ax.f25628r, bVar.f25597g);
        intent.putExtra("ext_reason", i2);
        intent.putExtra(ax.f25626p, bVar.f1015b);
        intent.putExtra(ax.C, bVar.f25599i);
        if (bVar.f1006a == null || !Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(bVar.f25597g)) {
            a(context, intent, bVar);
            return;
        }
        try {
            bVar.f1006a.send(Message.obtain(null, 17, intent));
        } catch (RemoteException unused) {
            bVar.f1006a = null;
            StringBuilder sb = new StringBuilder();
            sb.append("peer may died: ");
            String str = bVar.f1015b;
            sb.append(str.substring(str.lastIndexOf(64)));
            com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
        }
    }

    public void a(Context context, at.b bVar, String str, String str2) {
        if ("5".equalsIgnoreCase(bVar.f25597g)) {
            com.xiaomi.channel.commonutils.logger.b.d("mipush kicked by server");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.kicked");
        intent.setPackage(bVar.f1012a);
        intent.putExtra("ext_kick_type", str);
        intent.putExtra("ext_kick_reason", str2);
        intent.putExtra("ext_chid", bVar.f25597g);
        intent.putExtra(ax.f25626p, bVar.f1015b);
        intent.putExtra(ax.C, bVar.f25599i);
        a(context, intent, bVar);
    }

    public void a(Context context, at.b bVar, boolean z2, int i2, String str) {
        if ("5".equalsIgnoreCase(bVar.f25597g)) {
            this.f25680a.a(context, bVar, z2, i2, str);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_opened");
        intent.setPackage(bVar.f1012a);
        intent.putExtra("ext_succeeded", z2);
        if (!z2) {
            intent.putExtra("ext_reason", i2);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("ext_reason_msg", str);
        }
        intent.putExtra("ext_chid", bVar.f25597g);
        intent.putExtra(ax.f25626p, bVar.f1015b);
        intent.putExtra(ax.C, bVar.f25599i);
        a(context, intent, bVar);
    }

    public void a(XMPushService xMPushService, String str, fv fvVar) throws RemoteException {
        at.b bVarA = a(fvVar);
        if (bVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("error while notify channel closed! channel " + str + " not registered");
            return;
        }
        if ("5".equalsIgnoreCase(str)) {
            this.f25680a.a(xMPushService, fvVar, bVarA);
            return;
        }
        String str2 = bVarA.f1012a;
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.new_msg");
        intent.setPackage(str2);
        intent.putExtra("ext_chid", str);
        intent.putExtra("ext_raw_packet", fvVar.m433a(bVarA.f25598h));
        intent.putExtra(ax.C, bVarA.f25599i);
        intent.putExtra(ax.f25632v, bVarA.f25598h);
        if (bVarA.f1006a != null) {
            try {
                bVarA.f1006a.send(Message.obtain(null, 17, intent));
                return;
            } catch (RemoteException unused) {
                bVarA.f1006a = null;
                StringBuilder sb = new StringBuilder();
                sb.append("peer may died: ");
                String str3 = bVarA.f1015b;
                sb.append(str3.substring(str3.lastIndexOf(64)));
                com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
            }
        }
        if ("com.xiaomi.xmsf".equals(str2)) {
            return;
        }
        a(xMPushService, intent, bVarA);
    }

    public void a(XMPushService xMPushService, String str, gt gtVar) {
        String str2;
        String str3;
        at.b bVarA = a(gtVar);
        if (bVarA != null) {
            if ("5".equalsIgnoreCase(str)) {
                this.f25680a.a(xMPushService, gtVar, bVarA);
                return;
            }
            String str4 = bVarA.f1012a;
            if (gtVar instanceof gs) {
                str3 = "com.xiaomi.push.new_msg";
            } else if (gtVar instanceof gr) {
                str3 = "com.xiaomi.push.new_iq";
            } else if (gtVar instanceof gv) {
                str3 = "com.xiaomi.push.new_pres";
            } else {
                str2 = "unknown packet type, drop it";
            }
            Intent intent = new Intent();
            intent.setAction(str3);
            intent.setPackage(str4);
            intent.putExtra("ext_chid", str);
            intent.putExtra("ext_packet", gtVar.a());
            intent.putExtra(ax.C, bVarA.f25599i);
            intent.putExtra(ax.f25632v, bVarA.f25598h);
            a(xMPushService, intent, bVarA);
            return;
        }
        str2 = "error while notify channel closed! channel " + str + " not registered";
        com.xiaomi.channel.commonutils.logger.b.d(str2);
    }
}
