package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.push.ai;
import com.xiaomi.push.hw;
import com.xiaomi.push.ic;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.je;
import com.xiaomi.push.service.o;
import com.yikaobang.yixue.R2;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class bh implements o.a {
    public bh(Context context) {
        com.xiaomi.push.service.o.a(context).a(this);
    }

    private void b(String str, Context context) {
        je jeVar = new je();
        jeVar.c(in.ClientMIIDUpdate.f622a);
        jeVar.b(d.m156a(context).m157a());
        jeVar.a(com.xiaomi.push.service.ar.a());
        HashMap map = new HashMap();
        com.xiaomi.push.p.a(map, Constants.EXTRA_KEY_MIID, str);
        jeVar.a(map);
        int iA = com.xiaomi.push.j.a();
        if (iA >= 0) {
            jeVar.m610a().put("space_id", Integer.toString(iA));
        }
        az.a(context).a((az) jeVar, hw.Notification, true, (iq) null);
    }

    public void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        long j2 = sharedPreferences.getLong("last_sync_miid_time", -1L);
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        int iA = com.xiaomi.push.service.ao.a(context).a(ic.SyncMIIDFrequency.a(), R2.layout.fmt_course_download_manage);
        if (j2 != -1) {
            if (Math.abs(jCurrentTimeMillis - j2) <= iA) {
                return;
            } else {
                com.xiaomi.push.ai.a(context).a((ai.a) new bi(context), iA);
            }
        }
        sharedPreferences.edit().putLong("last_sync_miid_time", jCurrentTimeMillis).commit();
    }

    @Override // com.xiaomi.push.service.o.a
    public void a(String str, Context context) {
        b(str, context);
    }
}
