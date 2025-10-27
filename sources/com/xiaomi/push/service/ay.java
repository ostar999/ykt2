package com.xiaomi.push.service;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.xiaomi.push.cy;
import com.xiaomi.push.db;
import com.xiaomi.push.dc;
import com.xiaomi.push.es;
import com.xiaomi.push.et;
import com.xiaomi.push.fr;
import com.xiaomi.push.gc;
import com.xiaomi.push.he;
import com.xiaomi.push.ho;
import com.xiaomi.push.hq;
import com.xiaomi.push.service.bi;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class ay extends bi.a implements dc.a {

    /* renamed from: a, reason: collision with root package name */
    private long f25637a;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f1021a;

    public static class a implements dc.b {
        @Override // com.xiaomi.push.dc.b
        public String a(String str) throws IOException {
            Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
            builderBuildUpon.appendQueryParameter("sdkver", String.valueOf(38));
            builderBuildUpon.appendQueryParameter("osver", String.valueOf(Build.VERSION.SDK_INT));
            builderBuildUpon.appendQueryParameter("os", he.a(Build.MODEL + ":" + Build.VERSION.INCREMENTAL));
            builderBuildUpon.appendQueryParameter("mi", String.valueOf(com.xiaomi.push.v.a()));
            String string = builderBuildUpon.toString();
            com.xiaomi.channel.commonutils.logger.b.c("fetch bucket from : " + string);
            URL url = new URL(string);
            int port = url.getPort() == -1 ? 80 : url.getPort();
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                String strA = com.xiaomi.push.as.a(com.xiaomi.push.v.m770a(), url);
                hq.a(url.getHost() + ":" + port, (int) (System.currentTimeMillis() - jCurrentTimeMillis), null);
                return strA;
            } catch (IOException e2) {
                hq.a(url.getHost() + ":" + port, -1, e2);
                throw e2;
            }
        }
    }

    public static class b extends dc {
        public b(Context context, db dbVar, dc.b bVar, String str) {
            super(context, dbVar, bVar, str);
        }

        @Override // com.xiaomi.push.dc
        public String a(ArrayList<String> arrayList, String str, String str2, boolean z2) throws IOException {
            try {
                if (ho.m488a().m493a()) {
                    str2 = bi.m731a();
                }
                return super.a(arrayList, str, str2, z2);
            } catch (IOException e2) {
                hq.a(0, fr.GSLB_ERR.a(), 1, null, com.xiaomi.push.as.b(dc.f24717a) ? 1 : 0);
                throw e2;
            }
        }
    }

    public ay(XMPushService xMPushService) {
        this.f1021a = xMPushService;
    }

    public static void a(XMPushService xMPushService) {
        ay ayVar = new ay(xMPushService);
        bi.a().a(ayVar);
        synchronized (dc.class) {
            dc.a(ayVar);
            dc.a(xMPushService, null, new a(), "0", "push", "2.2");
        }
    }

    @Override // com.xiaomi.push.dc.a
    public dc a(Context context, db dbVar, dc.b bVar, String str) {
        return new b(context, dbVar, bVar, str);
    }

    @Override // com.xiaomi.push.service.bi.a
    public void a(es.a aVar) {
    }

    @Override // com.xiaomi.push.service.bi.a
    public void a(et.b bVar) throws JSONException {
        cy cyVarB;
        boolean z2;
        if (bVar.m360b() && bVar.m359a() && System.currentTimeMillis() - this.f25637a > com.heytap.mcssdk.constant.a.f7141e) {
            com.xiaomi.channel.commonutils.logger.b.m117a("fetch bucket :" + bVar.m359a());
            this.f25637a = System.currentTimeMillis();
            dc dcVarA = dc.a();
            dcVarA.m316a();
            dcVarA.m319b();
            gc gcVarM695a = this.f1021a.m695a();
            if (gcVarM695a == null || (cyVarB = dcVarA.b(gcVarM695a.m449a().c())) == null) {
                return;
            }
            ArrayList<String> arrayListM304a = cyVarB.m304a();
            Iterator<String> it = arrayListM304a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    break;
                } else if (it.next().equals(gcVarM695a.mo450a())) {
                    z2 = false;
                    break;
                }
            }
            if (!z2 || arrayListM304a.isEmpty()) {
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.m117a("bucket changed, force reconnect");
            this.f1021a.a(0, (Exception) null);
            this.f1021a.a(false);
        }
    }
}
