package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.hw;
import com.xiaomi.push.ii;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.ir;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import com.xiaomi.push.jv;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/* loaded from: classes6.dex */
public class w {

    /* renamed from: a, reason: collision with root package name */
    private static volatile w f24582a;

    /* renamed from: a, reason: collision with other field name */
    private Context f166a;

    /* renamed from: a, reason: collision with other field name */
    private final String f167a = "GeoFenceRegMessageProcessor.";

    private w(Context context) {
        this.f166a = context;
    }

    public static w a(Context context) {
        if (f24582a == null) {
            synchronized (w.class) {
                if (f24582a == null) {
                    f24582a = new w(context);
                }
            }
        }
        return f24582a;
    }

    private ii a(je jeVar, boolean z2) {
        if (z2 && !com.xiaomi.push.service.j.a(this.f166a)) {
            return null;
        }
        if (z2 && !com.xiaomi.push.service.j.c(this.f166a)) {
            return null;
        }
        try {
            ii iiVar = new ii();
            jp.a(iiVar, jeVar.m615a());
            return iiVar;
        } catch (jv e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private ir a(boolean z2) {
        ir irVar = new ir();
        TreeSet treeSet = new TreeSet();
        if (z2) {
            Iterator<ii> it = com.xiaomi.push.service.g.a(this.f166a).a().iterator();
            while (it.hasNext()) {
                treeSet.add(it.next());
            }
        }
        irVar.a(treeSet);
        return irVar;
    }

    public static void a(Context context, boolean z2) {
        je jeVar = new je(com.xiaomi.push.service.ar.a(), false);
        jeVar.b(d.m156a(context).m157a());
        jeVar.c(in.GeoAuthorized.f622a);
        HashMap map = new HashMap();
        jeVar.f769a = map;
        map.put("permission_to_location", String.valueOf(z2));
        az.a(context).a((az) jeVar, hw.Notification, false, (iq) null);
    }

    private void a(ii iiVar) {
        byte[] bArrA = jp.a(iiVar);
        je jeVar = new je(com.xiaomi.push.service.ar.a(), false);
        jeVar.c(in.GeoPackageUninstalled.f622a);
        jeVar.a(bArrA);
        az.a(this.f166a).a((az) jeVar, hw.Notification, true, (iq) null);
        com.xiaomi.channel.commonutils.logger.b.c("GeoFenceRegMessageProcessor. report package not exist geo_fencing id:" + iiVar.m520a());
    }

    private void a(ii iiVar, boolean z2, boolean z3) {
        byte[] bArrA = jp.a(iiVar);
        je jeVar = new je(com.xiaomi.push.service.ar.a(), false);
        jeVar.c((z2 ? in.GeoRegsiterResult : in.GeoUnregsiterResult).f622a);
        jeVar.a(bArrA);
        if (z3) {
            jeVar.a("permission_to_location", com.xiaomi.push.service.bb.f25641b);
        }
        az.a(this.f166a).a((az) jeVar, hw.Notification, true, (iq) null);
        StringBuilder sb = new StringBuilder();
        sb.append("GeoFenceRegMessageProcessor. report geo_fencing id:");
        sb.append(iiVar.m520a());
        sb.append(" ");
        sb.append(z2 ? "geo_reg" : "geo_unreg");
        sb.append("  isUnauthorized:");
        sb.append(z3);
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
    }

    private boolean a(je jeVar) {
        return a(jeVar.m610a()) && com.xiaomi.push.service.j.d(this.f166a);
    }

    public static boolean a(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        return TextUtils.equals("1", map.get("__geo_local_cache"));
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m186a(je jeVar) {
        String str;
        boolean zA = a(jeVar);
        ii iiVarA = a(jeVar, zA);
        if (iiVarA == null) {
            str = "GeoFenceRegMessageProcessor. registration convert geofence object failed notification_id:" + jeVar.a();
        } else {
            if (!com.xiaomi.push.service.j.e(this.f166a)) {
                a(iiVarA, true, true);
                return;
            }
            if (!com.xiaomi.push.g.m442b(this.f166a, iiVarA.c())) {
                if (zA) {
                    a(iiVarA);
                    return;
                }
                return;
            } else {
                if (!zA) {
                    a(iiVarA, true, false);
                    return;
                }
                if (com.xiaomi.push.service.g.a(this.f166a).a(iiVarA) == -1) {
                    com.xiaomi.channel.commonutils.logger.b.m117a("GeoFenceRegMessageProcessor. insert a new geofence failed about geo_id:" + iiVarA.m520a());
                }
                new x(this.f166a).a(iiVarA);
                a(iiVarA, true, false);
                str = "GeoFenceRegMessageProcessor. receive geo reg notification";
            }
        }
        com.xiaomi.channel.commonutils.logger.b.c(str);
    }

    public void b(je jeVar) {
        boolean zA = a(jeVar);
        ii iiVarA = a(jeVar, zA);
        if (iiVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.c("GeoFenceRegMessageProcessor. unregistration convert geofence object failed notification_id:" + jeVar.a());
            return;
        }
        if (!com.xiaomi.push.service.j.e(this.f166a)) {
            a(iiVarA, false, true);
            return;
        }
        if (!com.xiaomi.push.g.m442b(this.f166a, iiVarA.c())) {
            if (zA) {
                a(iiVarA);
                return;
            }
            return;
        }
        if (!zA) {
            a(iiVarA, false, false);
            return;
        }
        if (com.xiaomi.push.service.g.a(this.f166a).a(iiVarA.m520a()) == 0) {
            com.xiaomi.channel.commonutils.logger.b.m117a("GeoFenceRegMessageProcessor. delete a geofence about geo_id:" + iiVarA.m520a() + " falied");
        }
        if (com.xiaomi.push.service.i.a(this.f166a).b(iiVarA.m520a()) == 0) {
            com.xiaomi.channel.commonutils.logger.b.m117a("GeoFenceRegMessageProcessor. delete all geofence messages about geo_id:" + iiVarA.m520a() + " failed");
        }
        new x(this.f166a).a(iiVarA.m520a());
        a(iiVarA, false, false);
        com.xiaomi.channel.commonutils.logger.b.c("GeoFenceRegMessageProcessor. receive geo unreg notification");
    }

    public void c(je jeVar) {
        if (com.xiaomi.push.service.j.e(this.f166a)) {
            boolean zA = a(jeVar);
            if (!zA || com.xiaomi.push.service.j.a(this.f166a)) {
                if ((!zA || com.xiaomi.push.service.j.c(this.f166a)) && com.xiaomi.push.g.m442b(this.f166a, jeVar.f776f)) {
                    ir irVarA = a(zA);
                    byte[] bArrA = jp.a(irVarA);
                    je jeVar2 = new je("-1", false);
                    jeVar2.c(in.GeoUpload.f622a);
                    jeVar2.a(bArrA);
                    az.a(this.f166a).a((az) jeVar2, hw.Notification, true, (iq) null);
                    com.xiaomi.channel.commonutils.logger.b.c("GeoFenceRegMessageProcessor. sync_geo_data. geos size:" + irVarA.a().size());
                }
            }
        }
    }
}
