package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import cn.hutool.core.text.CharPool;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public final class ag {

    /* renamed from: a, reason: collision with root package name */
    private final SimpleDateFormat f17457a;

    /* renamed from: b, reason: collision with root package name */
    private final ad f17458b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final ag f17461a = new ag(0);
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        String f17462a;

        /* renamed from: b, reason: collision with root package name */
        public long f17463b;

        /* renamed from: c, reason: collision with root package name */
        public String f17464c;

        public final String toString() {
            return "SLAData{uuid='" + this.f17462a + CharPool.SINGLE_QUOTE + ", time=" + this.f17463b + ", data='" + this.f17464c + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public /* synthetic */ ag(byte b3) {
        this();
    }

    public static void c(List<b> list) {
        if (list == null || list.isEmpty()) {
            al.c("sla batch report data is empty", new Object[0]);
            return;
        }
        al.c("sla batch report list size:%s", Integer.valueOf(list.size()));
        if (list.size() > 30) {
            list = list.subList(0, 29);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<b> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().f17464c);
        }
        Pair<Integer, String> pairA = ad.a(arrayList);
        al.c("sla batch report result, rspCode:%s rspMsg:%s", pairA.first, pairA.second);
        if (((Integer) pairA.first).intValue() == 200) {
            d(list);
        }
    }

    public static void d(List<b> list) {
        if (list == null || list.isEmpty()) {
            al.c("sla batch delete list is null", new Object[0]);
            return;
        }
        al.c("sla batch delete list size:%s", Integer.valueOf(list.size()));
        try {
            String str = "_id in (" + a(",", list) + ")";
            al.c("sla batch delete where:%s", str);
            w.a().a("t_sla", str);
        } catch (Throwable th) {
            al.b(th);
        }
    }

    private static void e(List<b> list) {
        for (b bVar : list) {
            al.c("sla save id:%s time:%s msg:%s", bVar.f17462a, Long.valueOf(bVar.f17463b), bVar.f17464c);
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(com.umeng.analytics.pro.aq.f22519d, bVar.f17462a);
                contentValues.put("_tm", Long.valueOf(bVar.f17463b));
                contentValues.put("_dt", bVar.f17464c);
                w.a().a("t_sla", contentValues, (v) null);
            } catch (Throwable th) {
                al.b(th);
            }
        }
    }

    public final void a(List<c> list) {
        if (list == null || list.isEmpty()) {
            al.d("sla batch report event is null", new Object[0]);
            return;
        }
        al.c("sla batch report event size:%s", Integer.valueOf(list.size()));
        ArrayList arrayList = new ArrayList();
        Iterator<c> it = list.iterator();
        while (it.hasNext()) {
            b bVarB = b(it.next());
            if (bVarB != null) {
                arrayList.add(bVarB);
            }
        }
        e(arrayList);
        b(arrayList);
    }

    public final void b(final List<b> list) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.ag.1
                @Override // java.lang.Runnable
                public final void run() {
                    ag.c(list);
                }
            });
        } else {
            c(list);
        }
    }

    private ag() {
        this.f17457a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US);
        this.f17458b = new ad();
    }

    private b b(c cVar) {
        if (cVar != null && !TextUtils.isEmpty(cVar.f17466b)) {
            aa aaVarB = aa.b();
            if (aaVarB == null) {
                al.d("sla convert failed because ComInfoManager is null", new Object[0]);
                return null;
            }
            StringBuilder sb = new StringBuilder("&app_version=");
            sb.append(aaVarB.f17429o);
            sb.append("&app_name=");
            sb.append(aaVarB.f17431q);
            sb.append("&app_bundle_id=");
            sb.append(aaVarB.f17417c);
            sb.append("&client_type=android&user_id=");
            sb.append(aaVarB.f());
            sb.append("&sdk_version=");
            sb.append(aaVarB.f17422h);
            sb.append("&event_code=");
            sb.append(cVar.f17466b);
            sb.append("&event_result=");
            sb.append(cVar.f17468d ? 1 : 0);
            sb.append("&event_time=");
            sb.append(this.f17457a.format(new Date(cVar.f17467c)));
            sb.append("&event_cost=");
            sb.append(cVar.f17469e);
            sb.append("&device_id=");
            sb.append(aaVarB.g());
            sb.append("&debug=");
            sb.append(aaVarB.D ? 1 : 0);
            sb.append("&param_0=");
            sb.append(cVar.f17470f);
            sb.append("&param_1=");
            sb.append(cVar.f17465a);
            sb.append("&param_2=");
            sb.append(aaVarB.M ? "rqd" : SocializeProtocolConstants.PROTOCOL_KEY_EXTEND);
            sb.append("&param_4=");
            sb.append(aaVarB.e());
            String string = sb.toString();
            if (!TextUtils.isEmpty(cVar.f17471g)) {
                string = string + "&param_3=" + cVar.f17471g;
            }
            al.c("sla convert eventId:%s eventType:%s, eventTime:%s success:%s cost:%s from:%s uploadMsg:", cVar.f17465a, cVar.f17466b, Long.valueOf(cVar.f17467c), Boolean.valueOf(cVar.f17468d), Long.valueOf(cVar.f17469e), cVar.f17470f, cVar.f17471g);
            String str = cVar.f17465a + "-" + cVar.f17466b;
            b bVar = new b();
            bVar.f17462a = str;
            bVar.f17463b = cVar.f17467c;
            bVar.f17464c = string;
            return bVar;
        }
        al.d("sla convert event is null", new Object[0]);
        return null;
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        String f17465a;

        /* renamed from: b, reason: collision with root package name */
        String f17466b;

        /* renamed from: c, reason: collision with root package name */
        long f17467c;

        /* renamed from: d, reason: collision with root package name */
        boolean f17468d;

        /* renamed from: e, reason: collision with root package name */
        long f17469e;

        /* renamed from: f, reason: collision with root package name */
        String f17470f;

        /* renamed from: g, reason: collision with root package name */
        String f17471g;

        public c(String str, String str2, long j2, boolean z2, long j3, String str3, String str4) {
            this.f17465a = str;
            this.f17466b = str2;
            this.f17467c = j2;
            this.f17468d = z2;
            this.f17469e = j3;
            this.f17470f = str3;
            this.f17471g = str4;
        }

        public c() {
        }
    }

    private static String a(String str, Iterable<b> iterable) {
        Iterator<b> it = iterable.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("'");
        sb.append(it.next().f17462a);
        sb.append("'");
        while (it.hasNext()) {
            sb.append(str);
            sb.append("'");
            sb.append(it.next().f17462a);
            sb.append("'");
        }
        return sb.toString();
    }

    public static List<b> a() {
        Cursor cursorA = w.a().a("t_sla", new String[]{com.umeng.analytics.pro.aq.f22519d, "_tm", "_dt"}, (String) null, "_tm", "30");
        if (cursorA == null) {
            return null;
        }
        if (cursorA.getCount() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (cursorA.moveToNext()) {
            try {
                b bVar = new b();
                bVar.f17462a = cursorA.getString(cursorA.getColumnIndex(com.umeng.analytics.pro.aq.f22519d));
                bVar.f17463b = cursorA.getLong(cursorA.getColumnIndex("_tm"));
                bVar.f17464c = cursorA.getString(cursorA.getColumnIndex("_dt"));
                al.c(bVar.toString(), new Object[0]);
                arrayList.add(bVar);
            } finally {
                try {
                    return arrayList;
                } finally {
                }
            }
        }
        return arrayList;
    }

    public final void a(c cVar) {
        if (TextUtils.isEmpty(cVar.f17466b)) {
            al.d("sla report event is null", new Object[0]);
        } else {
            al.c("sla report single event", new Object[0]);
            a(Collections.singletonList(cVar));
        }
    }
}
