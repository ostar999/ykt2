package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.xiaomi.push.al;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class dl {

    /* renamed from: a, reason: collision with root package name */
    private static volatile dl f24730a;

    /* renamed from: a, reason: collision with other field name */
    private Context f315a;

    /* renamed from: a, reason: collision with other field name */
    private final ConcurrentLinkedQueue<b> f316a;

    public class a extends b {
        public a() {
            super();
        }

        @Override // com.xiaomi.push.dl.b, com.xiaomi.push.al.b
        public void b() {
            dl.this.b();
        }
    }

    public class b extends al.b {

        /* renamed from: a, reason: collision with root package name */
        long f24732a = System.currentTimeMillis();

        public b() {
        }

        public boolean a() {
            return true;
        }

        @Override // com.xiaomi.push.al.b
        public void b() {
        }

        /* renamed from: b, reason: collision with other method in class */
        public final boolean m327b() {
            return System.currentTimeMillis() - this.f24732a > 172800000;
        }
    }

    public class c extends b {

        /* renamed from: a, reason: collision with root package name */
        int f24734a;

        /* renamed from: a, reason: collision with other field name */
        File f318a;

        /* renamed from: a, reason: collision with other field name */
        String f319a;

        /* renamed from: a, reason: collision with other field name */
        boolean f320a;

        /* renamed from: b, reason: collision with root package name */
        String f24735b;

        /* renamed from: b, reason: collision with other field name */
        boolean f321b;

        public c(String str, String str2, File file, boolean z2) {
            super();
            this.f319a = str;
            this.f24735b = str2;
            this.f318a = file;
            this.f321b = z2;
        }

        private boolean c() throws JSONException {
            int i2;
            int i3 = 0;
            SharedPreferences sharedPreferences = dl.this.f315a.getSharedPreferences("log.timestamp", 0);
            String string = sharedPreferences.getString("log.requst", "");
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                JSONObject jSONObject = new JSONObject(string);
                jCurrentTimeMillis = jSONObject.getLong(CrashHianalyticsData.TIME);
                i2 = jSONObject.getInt("times");
            } catch (JSONException unused) {
                i2 = 0;
            }
            if (System.currentTimeMillis() - jCurrentTimeMillis >= 86400000) {
                jCurrentTimeMillis = System.currentTimeMillis();
            } else {
                if (i2 > 10) {
                    return false;
                }
                i3 = i2;
            }
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put(CrashHianalyticsData.TIME, jCurrentTimeMillis);
                jSONObject2.put("times", i3 + 1);
                sharedPreferences.edit().putString("log.requst", jSONObject2.toString()).commit();
            } catch (JSONException e2) {
                com.xiaomi.channel.commonutils.logger.b.c("JSONException on put " + e2.getMessage());
            }
            return true;
        }

        @Override // com.xiaomi.push.dl.b
        public boolean a() {
            return as.d(dl.this.f315a) || (this.f321b && as.b(dl.this.f315a));
        }

        @Override // com.xiaomi.push.dl.b, com.xiaomi.push.al.b
        public void b() {
            try {
                if (c()) {
                    HashMap map = new HashMap();
                    map.put("uid", com.xiaomi.push.service.bi.m731a());
                    map.put("token", this.f24735b);
                    map.put(com.alipay.sdk.app.statistic.c.f3111a, as.m201a(dl.this.f315a));
                    as.a(this.f319a, map, this.f318a, "file");
                }
                this.f320a = true;
            } catch (IOException unused) {
            }
        }

        @Override // com.xiaomi.push.al.b
        /* renamed from: c, reason: collision with other method in class */
        public void mo328c() {
            if (!this.f320a) {
                int i2 = this.f24734a + 1;
                this.f24734a = i2;
                if (i2 < 3) {
                    dl.this.f316a.add(this);
                }
            }
            if (this.f320a || this.f24734a >= 3) {
                this.f318a.delete();
            }
            dl.this.a((1 << this.f24734a) * 1000);
        }
    }

    private dl(Context context) {
        ConcurrentLinkedQueue<b> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        this.f316a = concurrentLinkedQueue;
        this.f315a = context;
        concurrentLinkedQueue.add(new a());
        b(0L);
    }

    public static dl a(Context context) {
        if (f24730a == null) {
            synchronized (dl.class) {
                if (f24730a == null) {
                    f24730a = new dl(context);
                }
            }
        }
        f24730a.f315a = context;
        return f24730a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j2) {
        b bVarPeek = this.f316a.peek();
        if (bVarPeek == null || !bVarPeek.a()) {
            return;
        }
        b(j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (aa.b() || aa.m192a()) {
            return;
        }
        try {
            File file = new File(this.f315a.getExternalFilesDir(null) + "/.logcache");
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    file2.delete();
                }
            }
        } catch (NullPointerException unused) {
        }
    }

    private void b(long j2) {
        if (this.f316a.isEmpty()) {
            return;
        }
        hf.a(new dn(this), j2);
    }

    private void c() {
        while (!this.f316a.isEmpty()) {
            b bVarPeek = this.f316a.peek();
            if (bVarPeek != null) {
                if (!bVarPeek.m327b() && this.f316a.size() <= 6) {
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.c("remove Expired task");
                this.f316a.remove(bVarPeek);
            }
        }
    }

    public void a() {
        c();
        a(0L);
    }

    public void a(String str, String str2, Date date, Date date2, int i2, boolean z2) {
        this.f316a.add(new dm(this, i2, date, date2, str, str2, z2));
        b(0L);
    }
}
