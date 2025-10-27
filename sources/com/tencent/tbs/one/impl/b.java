package com.tencent.tbs.one.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.tencent.tbs.logger.f;
import com.tencent.tbs.one.impl.a.e;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.a.n;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.d.a;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: g, reason: collision with root package name */
    public static int f21769g = 10001;

    /* renamed from: h, reason: collision with root package name */
    public static String f21770h = "https://tbsone.imtt.qq.com";

    /* renamed from: i, reason: collision with root package name */
    public static WeakReference<Context> f21771i;

    /* renamed from: a, reason: collision with root package name */
    public String f21772a = "";

    /* renamed from: b, reason: collision with root package name */
    public int f21773b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f21774c = 0;

    /* renamed from: d, reason: collision with root package name */
    public int f21775d = 0;

    /* renamed from: e, reason: collision with root package name */
    public int f21776e = 0;

    /* renamed from: f, reason: collision with root package name */
    public String f21777f = "";

    /* renamed from: j, reason: collision with root package name */
    public String f21778j;

    /* renamed from: k, reason: collision with root package name */
    public int f21779k;

    /* renamed from: com.tencent.tbs.one.impl.b$1, reason: invalid class name */
    public static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f21780a;

        public AnonymousClass1(Context context) {
            this.f21780a = context;
        }

        @Override // java.lang.Runnable
        public final void run() {
            new com.tencent.tbs.one.impl.common.a.a(f.f21659f).a((m) new m<byte[]>() { // from class: com.tencent.tbs.one.impl.b.1.1
                @Override // com.tencent.tbs.one.impl.a.m
                public final /* synthetic */ void a(byte[] bArr) {
                    byte[] bArr2 = bArr;
                    if (bArr2.length > 1048576 && com.tencent.tbs.one.impl.common.a.c.a(AnonymousClass1.this.f21780a) != 3) {
                        g.b("Log size greater than 1MB, and current network is not WI-FI, log report halt!", new Object[0]);
                        return;
                    }
                    HashMap map = new HashMap();
                    map.put("Content-Type", "application/octet-stream");
                    map.put("Charset", "UTF-8");
                    StringBuilder sb = new StringBuilder();
                    sb.append(bArr2.length);
                    map.put("Content-length", sb.toString());
                    map.put("q-proxy-log", "tbsonelog");
                    map.put("q-guid", com.tencent.tbs.one.impl.common.a.c.a());
                    map.put("q-params", "");
                    map.put("openrandom", "tbsonelog");
                    map.put("openmode", com.tencent.tbs.one.impl.common.a.c.d(AnonymousClass1.this.f21780a));
                    new com.tencent.tbs.one.impl.d.a(AnonymousClass1.this.f21780a, "https://qprostat.imtt.qq.com", "POST", map, bArr2).a((m) new m<Integer>() { // from class: com.tencent.tbs.one.impl.b.1.1.1
                        @Override // com.tencent.tbs.one.impl.a.m
                        public final /* synthetic */ void a(Integer num) throws Throwable {
                            b.b(AnonymousClass1.this.f21780a);
                        }
                    });
                }
            });
        }
    }

    public b(String str, int i2) {
        this.f21778j = str;
        this.f21779k = i2;
    }

    public static b a(String str, int i2) {
        return new b(str, i2);
    }

    public static synchronized void a() {
        Context context = f21771i.get();
        if (context == null) {
            g.c("Null context! Have you initialized Statistic with null context or haven't initialized Statistic?", new Throwable("Null context!"));
            return;
        }
        int iC = c();
        if (iC > 0 && iC == com.tencent.tbs.one.impl.common.a.c.c(context)) {
            g.a("Detected logs have been uploaded in this version, log report ignored.", new Object[0]);
        } else {
            com.tencent.tbs.one.impl.common.a.b.a();
            com.tencent.tbs.one.impl.common.a.b.a(new AnonymousClass1(context));
        }
    }

    public static void a(Context context) {
        f21771i = new WeakReference<>(context);
        c(context);
    }

    public static void a(boolean z2) {
        f21770h = z2 ? "https://tbsone.sparta.html5.qq.com" : "https://tbsone.imtt.qq.com";
    }

    public static /* synthetic */ void b(Context context) throws Throwable {
        try {
            File file = new File(f.f21659f, "log.lock");
            if (!file.exists()) {
                if (file.createNewFile()) {
                    file.getAbsolutePath();
                } else {
                    g.c("Create log upload lock failed!", new Object[0]);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(com.tencent.tbs.one.impl.common.a.c.c(context));
            com.tencent.tbs.one.impl.a.d.a(sb.toString(), "utf-8", file);
        } catch (IOException e2) {
            g.c("Exception when write log upload lock:" + e2.getMessage(), new Object[0]);
            e2.printStackTrace();
        }
    }

    public static int c() {
        File file = new File(f.f21659f, "log.lock");
        if (file.exists()) {
            try {
                String strA = com.tencent.tbs.one.impl.a.d.a(new FileInputStream(file), "utf-8");
                if (TextUtils.isEmpty(strA)) {
                    return -1;
                }
                return Integer.parseInt(strA);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return -1;
    }

    public static void c(Context context) {
        if (context != null && com.tencent.tbs.one.impl.common.a.d.c(context)) {
            try {
                d(context);
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void d(final Context context) {
        ArrayList<String> arrayListA = com.tencent.tbs.one.impl.common.a.d.a(context);
        if (arrayListA.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("PROTV", 1);
        map.put("FUNC", 2);
        map.put("CPUABI", Build.CPU_ABI);
        map.put("APPVERNAME", com.tencent.tbs.one.impl.common.a.c.b(context));
        map.put("APPPKG", context.getPackageName());
        map.put("ONECODE", "5");
        map.put("MODEL", com.tencent.tbs.one.impl.common.a.c.d(context));
        map.put("ADV", Build.VERSION.RELEASE);
        map.put("IMEI", n.a(context));
        StringBuilder sb = new StringBuilder();
        sb.append(f21769g);
        map.put(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_CODE, sb.toString());
        HashMap map2 = new HashMap(map);
        map2.put("DESCRIPTION", e.a(",", arrayListA));
        String string = new JSONObject(map2).toString();
        g.a(string, new Object[0]);
        com.tencent.tbs.one.impl.d.a aVar = new com.tencent.tbs.one.impl.d.a(context, f21770h, "POST", null, string.getBytes());
        aVar.f22045f = new a.InterfaceC0368a() { // from class: com.tencent.tbs.one.impl.b.2
            @Override // com.tencent.tbs.one.impl.d.a.InterfaceC0368a
            public final void a(int i2, Map<String, List<String>> map3, InputStream inputStream) {
                if (inputStream == null) {
                    return;
                }
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i3 = inputStream.read(bArr);
                        if (i3 == -1) {
                            break;
                        } else {
                            byteArrayOutputStream.write(bArr, 0, i3);
                        }
                    }
                    byteArrayOutputStream.close();
                    inputStream.close();
                    String str = new String(byteArrayOutputStream.toByteArray());
                    g.a(str, new Object[0]);
                    int i4 = new JSONObject(str).getInt(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_CODE);
                    if (i4 == 0) {
                        g.a("Statistic report successfully!", new Object[0]);
                        return;
                    }
                    g.c("Statistic report error! Code: " + i4, new Object[0]);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        };
        aVar.a((m) new m<Integer>() { // from class: com.tencent.tbs.one.impl.b.3
            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str, Throwable th) {
                g.c(str, th);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final /* synthetic */ void a(Integer num) {
                com.tencent.tbs.one.impl.common.a.d.b(context);
                g.a("Statistic upload complete with http response code: %d", num);
            }
        });
    }

    public final void b() {
        try {
            Context context = f21771i.get();
            int i2 = 1;
            if (context == null) {
                g.c("Null context! Have you initialized Statistic with null context or haven't initialized Statistic?", new Throwable("Null context!"));
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.f21772a);
            sb.append(StrPool.UNDERLINE);
            sb.append(this.f21773b);
            sb.append(StrPool.DOT);
            sb.append(this.f21774c);
            sb.append(StrPool.UNDERLINE);
            sb.append(this.f21775d);
            sb.append(StrPool.DOT);
            sb.append(this.f21776e);
            if (TextUtils.equals(Statistics.EVENT_ACTION, this.f21778j)) {
                i2 = 0;
            } else if (!TextUtils.equals(Statistics.EVENT_ERROR, this.f21778j)) {
                i2 = -1;
            }
            StringBuilder sb2 = new StringBuilder();
            if (!TextUtils.isEmpty(sb)) {
                sb2.append(":D=");
                sb2.append((CharSequence) sb);
            }
            sb2.append(":C=");
            sb2.append(i2);
            sb2.append(StrPool.DOT);
            sb2.append(this.f21779k);
            sb2.append(":A=");
            sb2.append(com.tencent.tbs.one.impl.common.a.c.a(context));
            if (!TextUtils.isEmpty(this.f21777f)) {
                sb2.append(":V=");
                sb2.append(this.f21777f);
            }
            sb2.append(":T=");
            sb2.append(System.currentTimeMillis());
            String string = sb2.toString();
            ArrayList<String> arrayListA = com.tencent.tbs.one.impl.common.a.d.a(context);
            if (arrayListA.size() >= 20) {
                arrayListA.remove(0);
            }
            arrayListA.add(string);
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.tencent.tbs.one.report", 4).edit();
            editorEdit.putString("stat_not_yet_sent", e.a(",", arrayListA));
            editorEdit.apply();
            c(context);
        } catch (Throwable unused) {
        }
    }
}
