package com.tencent.tbs.logger;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public final class f {

    /* renamed from: b, reason: collision with root package name */
    public static b f21655b;

    /* renamed from: c, reason: collision with root package name */
    public static List<d> f21656c;

    /* renamed from: d, reason: collision with root package name */
    public static boolean f21657d;

    /* renamed from: a, reason: collision with root package name */
    public static volatile Boolean f21654a = Boolean.FALSE;

    /* renamed from: e, reason: collision with root package name */
    public static c f21658e = c.ALL;

    /* renamed from: f, reason: collision with root package name */
    public static String f21659f = "";

    /* renamed from: g, reason: collision with root package name */
    public static String f21660g = "";

    public static class b implements e {

        /* renamed from: a, reason: collision with root package name */
        public String f21661a = b.class.getName();

        public b() {
        }

        public /* synthetic */ b(a aVar) {
        }

        public void a(int i2, String str) {
            c cVar = i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? c.NONE : c.ERROR : c.WARN : c.INFO : c.DEBUG : c.VERBOSE;
            String str2 = this.f21661a;
            try {
                if (f.a() && cVar.f21653a >= f.f21658e.f21653a) {
                    com.tencent.tbs.logger.b bVar = new com.tencent.tbs.logger.b(System.currentTimeMillis(), cVar, str2, str);
                    if (f.f21657d) {
                        Iterator<d> it = f.f21656c.iterator();
                        while (it.hasNext()) {
                            it.next().a(bVar);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void a(Context context, String str, long j2, long j3) {
        String absolutePath;
        try {
            if (f21654a.booleanValue()) {
                return;
            }
            f21655b = new b(0 == true ? 1 : 0);
            ArrayList arrayList = new ArrayList();
            f21656c = arrayList;
            arrayList.add(new com.tencent.tbs.logger.file.b(context.getApplicationContext(), true, true, new com.tencent.tbs.logger.file.naming.a(), new com.tencent.tbs.logger.file.clean.a(j2), new com.tencent.tbs.logger.file.backup.a(j3)));
            if (TextUtils.isEmpty(f21660g)) {
                File externalFilesDir = "mounted".equals(Environment.getExternalStorageState()) ? context.getExternalFilesDir(str) : null;
                if (externalFilesDir != null) {
                    absolutePath = externalFilesDir.getAbsolutePath();
                } else {
                    absolutePath = context.getFilesDir() + File.separator + str;
                }
            } else {
                absolutePath = f21660g;
            }
            f21659f = absolutePath;
            f21658e = c.ALL;
            f21657d = true;
            f21654a = Boolean.TRUE;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str) {
        f21660g = str;
    }

    public static boolean a() {
        if (!f21654a.booleanValue()) {
            new Throwable("TBSLogger has not been initialized, please call TBSLogger.initialize() first.").printStackTrace();
        }
        return f21654a.booleanValue();
    }
}
