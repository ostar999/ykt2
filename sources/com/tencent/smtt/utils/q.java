package com.tencent.smtt.utils;

import android.os.Build;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    private b f21597a = null;

    /* renamed from: b, reason: collision with root package name */
    private b f21598b = null;

    public class a {

        /* renamed from: b, reason: collision with root package name */
        private String f21600b;

        /* renamed from: c, reason: collision with root package name */
        private long f21601c;

        /* renamed from: d, reason: collision with root package name */
        private long f21602d;

        public a(String str, long j2, long j3) {
            this.f21600b = str;
            this.f21601c = j2;
            this.f21602d = j3;
        }

        public long a() {
            return this.f21601c;
        }

        public long b() {
            return this.f21602d;
        }
    }

    public class b {

        /* renamed from: b, reason: collision with root package name */
        private Map<String, a> f21604b;

        public b(File file) {
            HashMap map = new HashMap();
            this.f21604b = map;
            map.clear();
            a(file);
        }

        private void a(File file) {
            if (!file.isDirectory()) {
                if (file.isFile()) {
                    a(file.getName(), file.length(), file.lastModified());
                    return;
                }
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null || Build.VERSION.SDK_INT < 26) {
                for (File file2 : fileArrListFiles) {
                    a(file2);
                }
            }
        }

        private void a(String str, long j2, long j3) {
            if (str == null || str.length() <= 0 || j2 <= 0 || j3 <= 0) {
                return;
            }
            a aVar = q.this.new a(str, j2, j3);
            if (this.f21604b.containsKey(str)) {
                return;
            }
            this.f21604b.put(str, aVar);
        }

        public Map<String, a> a() {
            return this.f21604b;
        }
    }

    private boolean a(b bVar, b bVar2) {
        if (bVar == null || bVar.a() == null || bVar2 == null || bVar2.a() == null) {
            return false;
        }
        Map<String, a> mapA = bVar.a();
        Map<String, a> mapA2 = bVar2.a();
        for (Map.Entry<String, a> entry : mapA.entrySet()) {
            String key = entry.getKey();
            a value = entry.getValue();
            if (mapA2.containsKey(key)) {
                a aVar = mapA2.get(key);
                if (value.a() != aVar.a() || value.b() != aVar.b()) {
                }
            }
            return false;
        }
        return true;
    }

    public void a(File file) {
        this.f21597a = new b(file);
    }

    public boolean a() {
        b bVar = this.f21598b;
        return bVar != null && this.f21597a != null && bVar.a().size() == this.f21597a.a().size() && a(this.f21597a, this.f21598b);
    }

    public void b(File file) {
        this.f21598b = new b(file);
    }
}
