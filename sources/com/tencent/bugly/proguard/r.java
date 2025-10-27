package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class r {

    /* renamed from: e, reason: collision with root package name */
    private static boolean f17854e = true;

    /* renamed from: a, reason: collision with root package name */
    private Context f17855a;

    /* renamed from: b, reason: collision with root package name */
    private long f17856b;

    /* renamed from: c, reason: collision with root package name */
    private int f17857c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17858d;

    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private boolean f17864b;

        /* renamed from: c, reason: collision with root package name */
        private UserInfoBean f17865c;

        public a(UserInfoBean userInfoBean, boolean z2) {
            this.f17865c = userInfoBean;
            this.f17864b = z2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (r.this.f17858d) {
                try {
                    UserInfoBean userInfoBean = this.f17865c;
                    if (userInfoBean != null) {
                        r.a(userInfoBean);
                        al.c("[UserInfo] Record user info.", new Object[0]);
                        r.this.a(this.f17865c, false);
                    }
                    if (this.f17864b) {
                        r.this.b();
                    }
                } catch (Throwable th) {
                    if (al.a(th)) {
                        return;
                    }
                    th.printStackTrace();
                }
            }
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis < r.this.f17856b) {
                ak.a().a(r.this.new b(), (r.this.f17856b - jCurrentTimeMillis) + 5000);
            } else {
                r.this.a(3, false);
                r.this.a();
            }
        }
    }

    public class c implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private long f17868b;

        public c(long j2) {
            this.f17868b = j2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            r.this.b();
            r.this.a(this.f17868b);
        }
    }

    public r(Context context, boolean z2) {
        this.f17855a = context;
        this.f17858d = z2;
    }

    private static void b(List<UserInfoBean> list, List<UserInfoBean> list2) {
        Iterator<UserInfoBean> it = list.iterator();
        while (it.hasNext()) {
            UserInfoBean next = it.next();
            if (next.f17319f != -1) {
                it.remove();
                if (next.f17318e < ap.b()) {
                    list2.add(next);
                }
            }
        }
    }

    public final void a(int i2, boolean z2) {
        ac acVarA = ac.a();
        if (acVarA != null && !acVarA.c().f17342g && i2 != 1 && i2 != 3) {
            al.e("UserInfo is disable", new Object[0]);
            return;
        }
        if (i2 == 1 || i2 == 3) {
            this.f17857c++;
        }
        aa aaVarA = aa.a(this.f17855a);
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.f17315b = i2;
        userInfoBean.f17316c = aaVarA.f17418d;
        userInfoBean.f17317d = aaVarA.f();
        userInfoBean.f17318e = System.currentTimeMillis();
        userInfoBean.f17319f = -1L;
        userInfoBean.f17327n = aaVarA.f17429o;
        userInfoBean.f17328o = i2 == 1 ? 1 : 0;
        userInfoBean.f17325l = aaVarA.a();
        userInfoBean.f17326m = aaVarA.f17439y;
        userInfoBean.f17320g = aaVarA.f17440z;
        userInfoBean.f17321h = aaVarA.A;
        userInfoBean.f17322i = aaVarA.B;
        userInfoBean.f17324k = aaVarA.C;
        userInfoBean.f17331r = aaVarA.t();
        userInfoBean.f17332s = aaVarA.y();
        userInfoBean.f17329p = aaVarA.z();
        userInfoBean.f17330q = aaVarA.f17438x;
        ak.a().a(new a(userInfoBean, z2), 0L);
    }

    public final void b() {
        ak akVarA = ak.a();
        if (akVarA != null) {
            akVarA.a(new Runnable() { // from class: com.tencent.bugly.proguard.r.2

                /* renamed from: a, reason: collision with root package name */
                final /* synthetic */ boolean f17861a = false;

                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        r.this.a(this.f17861a);
                    } catch (Throwable th) {
                        al.a(th);
                    }
                }
            });
        }
    }

    private static void b(List<UserInfoBean> list) {
        if (list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < list.size() && i2 < 50; i2++) {
            UserInfoBean userInfoBean = list.get(i2);
            sb.append(" or _id = ");
            sb.append(userInfoBean.f17314a);
        }
        String string = sb.toString();
        if (string.length() > 0) {
            string = string.substring(4);
        }
        sb.setLength(0);
        try {
            al.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(w.a().a("t_ui", string)));
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private static ContentValues b(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j2 = userInfoBean.f17314a;
            if (j2 > 0) {
                contentValues.put(com.umeng.analytics.pro.aq.f22519d, Long.valueOf(j2));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.f17318e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f17319f));
            contentValues.put(com.umeng.analytics.pro.aq.f22520e, Integer.valueOf(userInfoBean.f17315b));
            contentValues.put("_pc", userInfoBean.f17316c);
            contentValues.put("_dt", ap.a(userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void a(long j2) {
        ak.a().a(new c(j2), j2);
    }

    public final void a() {
        this.f17856b = ap.b() + 86400000;
        ak.a().a(new b(), (this.f17856b - System.currentTimeMillis()) + 5000);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x008e A[Catch: Exception -> 0x007d, TRY_ENTER, TRY_LEAVE, TryCatch #1 {Exception -> 0x007d, blocks: (B:40:0x008e, B:29:0x0079, B:14:0x0038, B:16:0x004c, B:22:0x005b, B:25:0x0066), top: B:44:0x0036, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean b(boolean r14) throws java.io.IOException {
        /*
            r13 = this;
            boolean r0 = com.tencent.bugly.proguard.r.f17854e
            r1 = 1
            if (r0 != 0) goto L6
            return r1
        L6:
            java.io.File r0 = new java.io.File
            android.content.Context r2 = r13.f17855a
            java.io.File r2 = r2.getFilesDir()
            java.lang.String r3 = "bugly_last_us_up_tm"
            r0.<init>(r2, r3)
            long r2 = java.lang.System.currentTimeMillis()
            r4 = 1024(0x400, double:5.06E-321)
            r6 = 0
            if (r14 == 0) goto L24
            java.lang.String r14 = java.lang.String.valueOf(r2)
            com.tencent.bugly.proguard.am.a(r0, r14, r4, r6)
            return r1
        L24:
            boolean r14 = r0.exists()
            if (r14 != 0) goto L32
            java.lang.String r14 = java.lang.String.valueOf(r2)
            com.tencent.bugly.proguard.am.a(r0, r14, r4, r6)
            goto L91
        L32:
            java.io.BufferedReader r14 = com.tencent.bugly.proguard.ap.a(r0)
            if (r14 == 0) goto L8c
            java.lang.String r7 = r14.readLine()     // Catch: java.lang.Throwable -> L6e
            java.lang.String r7 = r7.trim()     // Catch: java.lang.Throwable -> L6e
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> L6e
            long r7 = r7.longValue()     // Catch: java.lang.Throwable -> L6e
            int r9 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r9 < 0) goto L58
            long r9 = r2 - r7
            r11 = 86400000(0x5265c00, double:4.2687272E-316)
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 <= 0) goto L56
            goto L58
        L56:
            r9 = r1
            goto L59
        L58:
            r9 = r6
        L59:
            if (r9 == 0) goto L66
            long r7 = r2 - r7
            r9 = 300000(0x493e0, double:1.482197E-318)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 >= 0) goto L66
            r1 = r6
            goto L8c
        L66:
            java.lang.String r7 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L6e
            com.tencent.bugly.proguard.am.a(r0, r7, r4, r6)     // Catch: java.lang.Throwable -> L6e
            goto L8c
        L6e:
            r7 = move-exception
            com.tencent.bugly.proguard.al.b(r7)     // Catch: java.lang.Throwable -> L82
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L82
            com.tencent.bugly.proguard.am.a(r0, r2, r4, r6)     // Catch: java.lang.Throwable -> L82
            r14.close()     // Catch: java.lang.Exception -> L7d
            goto L91
        L7d:
            r14 = move-exception
            com.tencent.bugly.proguard.al.a(r14)
            goto L91
        L82:
            r0 = move-exception
            r14.close()     // Catch: java.lang.Exception -> L87
            goto L8b
        L87:
            r14 = move-exception
            com.tencent.bugly.proguard.al.a(r14)
        L8b:
            throw r0
        L8c:
            if (r14 == 0) goto L91
            r14.close()     // Catch: java.lang.Exception -> L7d
        L91:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.r.b(boolean):boolean");
    }

    private static void a(List<UserInfoBean> list, List<UserInfoBean> list2) {
        int size = list.size() - 20;
        if (size > 0) {
            int i2 = 0;
            while (i2 < list.size() - 1) {
                int i3 = i2 + 1;
                for (int i4 = i3; i4 < list.size(); i4++) {
                    if (list.get(i2).f17318e > list.get(i4).f17318e) {
                        UserInfoBean userInfoBean = list.get(i2);
                        list.set(i2, list.get(i4));
                        list.set(i4, userInfoBean);
                    }
                }
                i2 = i3;
            }
            for (int i5 = 0; i5 < size; i5++) {
                list2.add(list.get(i5));
            }
        }
    }

    private static int a(List<UserInfoBean> list) {
        int i2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i3 = 0;
        for (UserInfoBean userInfoBean : list) {
            if (userInfoBean.f17318e > jCurrentTimeMillis - 600000 && ((i2 = userInfoBean.f17315b) == 1 || i2 == 4 || i2 == 3)) {
                i3++;
            }
        }
        return i3;
    }

    private void a(final List<UserInfoBean> list, boolean z2) {
        aa aaVarB;
        if (!b(z2)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            for (UserInfoBean userInfoBean : list) {
                userInfoBean.f17319f = jCurrentTimeMillis;
                a(userInfoBean, true);
            }
            al.d("uploadCheck failed", new Object[0]);
            return;
        }
        int i2 = this.f17857c == 1 ? 1 : 2;
        bv bvVar = null;
        if (list != null && list.size() != 0 && (aaVarB = aa.b()) != null) {
            aaVarB.o();
            bv bvVar2 = new bv();
            bvVar2.f17808b = aaVarB.f17418d;
            bvVar2.f17809c = aaVarB.g();
            ArrayList<bu> arrayList = new ArrayList<>();
            Iterator<UserInfoBean> it = list.iterator();
            while (it.hasNext()) {
                bu buVarA = ae.a(it.next());
                if (buVarA != null) {
                    arrayList.add(buVarA);
                }
            }
            bvVar2.f17810d = arrayList;
            HashMap map = new HashMap();
            bvVar2.f17811e = map;
            map.put("A7", new StringBuilder().toString());
            bvVar2.f17811e.put("A6", aa.n());
            bvVar2.f17811e.put("A5", aaVarB.m());
            Map<String, String> map2 = bvVar2.f17811e;
            StringBuilder sb = new StringBuilder();
            sb.append(aaVarB.k());
            map2.put("A2", sb.toString());
            Map<String, String> map3 = bvVar2.f17811e;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(aaVarB.k());
            map3.put("A1", sb2.toString());
            bvVar2.f17811e.put("A24", aaVarB.f17425k);
            Map<String, String> map4 = bvVar2.f17811e;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(aaVarB.l());
            map4.put("A17", sb3.toString());
            bvVar2.f17811e.put("A15", aaVarB.q());
            Map<String, String> map5 = bvVar2.f17811e;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(aaVarB.r());
            map5.put("A13", sb4.toString());
            bvVar2.f17811e.put("F08", aaVarB.E);
            bvVar2.f17811e.put("F09", aaVarB.F);
            Map<String, String> mapY = aaVarB.y();
            if (mapY != null && mapY.size() > 0) {
                for (Map.Entry<String, String> entry : mapY.entrySet()) {
                    bvVar2.f17811e.put("C04_" + entry.getKey(), entry.getValue());
                }
            }
            if (i2 == 1) {
                bvVar2.f17807a = (byte) 1;
            } else if (i2 != 2) {
                al.e("unknown up type %d ", Integer.valueOf(i2));
            } else {
                bvVar2.f17807a = (byte) 2;
            }
            bvVar = bvVar2;
        }
        if (bvVar == null) {
            al.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
            return;
        }
        byte[] bArrA = ae.a((m) bvVar);
        if (bArrA == null) {
            al.d("[UserInfo] Failed to encode data.", new Object[0]);
            return;
        }
        bq bqVarA = ae.a(this.f17855a, R2.attr.buttomTextColorPress, bArrA);
        if (bqVarA == null) {
            al.d("[UserInfo] Request package is null.", new Object[0]);
            return;
        }
        ai.a().a(1001, bqVarA, ac.a().c().f17352q, StrategyBean.f17336a, new ah() { // from class: com.tencent.bugly.proguard.r.1
            @Override // com.tencent.bugly.proguard.ah
            public final void a(boolean z3, String str) {
                if (z3) {
                    al.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    for (UserInfoBean userInfoBean2 : list) {
                        userInfoBean2.f17319f = jCurrentTimeMillis2;
                        r.this.a(userInfoBean2, true);
                    }
                }
            }
        }, this.f17857c == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(UserInfoBean userInfoBean, boolean z2) {
        List<UserInfoBean> listA;
        if (userInfoBean == null) {
            return;
        }
        if (!z2 && userInfoBean.f17315b != 1 && (listA = a(aa.a(this.f17855a).f17418d)) != null && listA.size() >= 20) {
            al.a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(listA.size()));
            return;
        }
        long jA = w.a().a("t_ui", b(userInfoBean), (v) null);
        if (jA >= 0) {
            al.c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(jA));
            userInfoBean.f17314a = jA;
        }
    }

    public static List<UserInfoBean> a(String str) {
        Cursor cursorA;
        String str2;
        try {
            if (ap.b(str)) {
                str2 = null;
            } else {
                str2 = "_pc = '" + str + "'";
            }
            cursorA = w.a().a("t_ui", (String[]) null, str2);
            if (cursorA == null) {
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList = new ArrayList();
                while (cursorA.moveToNext()) {
                    UserInfoBean userInfoBeanA = a(cursorA);
                    if (userInfoBeanA != null) {
                        arrayList.add(userInfoBeanA);
                    } else {
                        try {
                            long j2 = cursorA.getLong(cursorA.getColumnIndex(com.umeng.analytics.pro.aq.f22519d));
                            sb.append(" or _id = ");
                            sb.append(j2);
                        } catch (Throwable unused) {
                            al.d("[Database] unknown id.", new Object[0]);
                        }
                    }
                }
                String string = sb.toString();
                if (string.length() > 0) {
                    al.d("[Database] deleted %s error data %d", "t_ui", Integer.valueOf(w.a().a("t_ui", string.substring(4))));
                }
                cursorA.close();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                try {
                    if (!al.a(th)) {
                        th.printStackTrace();
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    return null;
                } finally {
                    if (cursorA != null) {
                        cursorA.close();
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorA = null;
        }
    }

    private static UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j2 = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.aq.f22519d));
            UserInfoBean userInfoBean = (UserInfoBean) ap.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean != null) {
                userInfoBean.f17314a = j2;
            }
            return userInfoBean;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067 A[Catch: all -> 0x0092, TryCatch #0 {, blocks: (B:3:0x0001, B:21:0x002b, B:23:0x003e, B:25:0x004c, B:28:0x0061, B:30:0x0067, B:32:0x006c, B:35:0x0073, B:38:0x0089, B:26:0x005b, B:6:0x0009, B:9:0x0010, B:12:0x0017, B:14:0x001d), top: B:44:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(boolean r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r7.f17858d     // Catch: java.lang.Throwable -> L92
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L9
        L7:
            r0 = r2
            goto L27
        L9:
            com.tencent.bugly.proguard.ai r0 = com.tencent.bugly.proguard.ai.a()     // Catch: java.lang.Throwable -> L92
            if (r0 != 0) goto L10
            goto L7
        L10:
            com.tencent.bugly.proguard.ac r3 = com.tencent.bugly.proguard.ac.a()     // Catch: java.lang.Throwable -> L92
            if (r3 != 0) goto L17
            goto L7
        L17:
            boolean r3 = r3.b()     // Catch: java.lang.Throwable -> L92
            if (r3 == 0) goto L26
            r3 = 1001(0x3e9, float:1.403E-42)
            boolean r0 = r0.b(r3)     // Catch: java.lang.Throwable -> L92
            if (r0 != 0) goto L26
            goto L7
        L26:
            r0 = r1
        L27:
            if (r0 != 0) goto L2b
            monitor-exit(r7)
            return
        L2b:
            android.content.Context r0 = r7.f17855a     // Catch: java.lang.Throwable -> L92
            com.tencent.bugly.proguard.aa r0 = com.tencent.bugly.proguard.aa.a(r0)     // Catch: java.lang.Throwable -> L92
            java.lang.String r0 = r0.f17418d     // Catch: java.lang.Throwable -> L92
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L92
            r3.<init>()     // Catch: java.lang.Throwable -> L92
            java.util.List r0 = a(r0)     // Catch: java.lang.Throwable -> L92
            if (r0 == 0) goto L5b
            a(r0, r3)     // Catch: java.lang.Throwable -> L92
            b(r0, r3)     // Catch: java.lang.Throwable -> L92
            int r4 = a(r0)     // Catch: java.lang.Throwable -> L92
            r5 = 15
            if (r4 <= r5) goto L60
            java.lang.String r5 = "[UserInfo] Upload user info too many times in 10 min: %d"
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L92
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L92
            r6[r2] = r4     // Catch: java.lang.Throwable -> L92
            com.tencent.bugly.proguard.al.d(r5, r6)     // Catch: java.lang.Throwable -> L92
            r4 = r2
            goto L61
        L5b:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L92
            r0.<init>()     // Catch: java.lang.Throwable -> L92
        L60:
            r4 = r1
        L61:
            int r5 = r3.size()     // Catch: java.lang.Throwable -> L92
            if (r5 <= 0) goto L6a
            b(r3)     // Catch: java.lang.Throwable -> L92
        L6a:
            if (r4 == 0) goto L89
            int r3 = r0.size()     // Catch: java.lang.Throwable -> L92
            if (r3 != 0) goto L73
            goto L89
        L73:
            java.lang.String r3 = "[UserInfo] Upload user info(size: %d)"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L92
            int r4 = r0.size()     // Catch: java.lang.Throwable -> L92
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L92
            r1[r2] = r4     // Catch: java.lang.Throwable -> L92
            com.tencent.bugly.proguard.al.c(r3, r1)     // Catch: java.lang.Throwable -> L92
            r7.a(r0, r8)     // Catch: java.lang.Throwable -> L92
            monitor-exit(r7)
            return
        L89:
            java.lang.String r8 = "[UserInfo] There is no user info in local database."
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L92
            com.tencent.bugly.proguard.al.c(r8, r0)     // Catch: java.lang.Throwable -> L92
            monitor-exit(r7)
            return
        L92:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.r.a(boolean):void");
    }

    public static /* synthetic */ void a(UserInfoBean userInfoBean) {
        aa aaVarB;
        if (userInfoBean == null || (aaVarB = aa.b()) == null) {
            return;
        }
        userInfoBean.f17323j = aaVarB.d();
    }
}
