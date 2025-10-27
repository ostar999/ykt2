package com.tencent.liteav.audio;

import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final Map<Long, a> f18167a = new ConcurrentHashMap();

    /* renamed from: b, reason: collision with root package name */
    private Method f18168b;

    /* renamed from: c, reason: collision with root package name */
    private Method f18169c;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public long f18170a;

        /* renamed from: b, reason: collision with root package name */
        public String f18171b;

        /* renamed from: c, reason: collision with root package name */
        public String f18172c;

        /* renamed from: d, reason: collision with root package name */
        public long f18173d;

        public a(long j2, String str, String str2) {
            this.f18170a = j2;
            this.f18171b = str;
            this.f18172c = str2;
        }
    }

    public c() throws ClassNotFoundException, SecurityException {
        try {
            Class<?> cls = Class.forName("com.tencent.txcopyrightedmedia.impl.utils.TXBGMUtils");
            this.f18168b = cls.getDeclaredMethod("getMusicURI", String.class);
            this.f18169c = cls.getDeclaredMethod("uploadMusicPlayInfo", String.class, String.class, Long.TYPE);
            this.f18168b.setAccessible(true);
            this.f18169c.setAccessible(true);
        } catch (Exception e2) {
            TXCLog.w("TXCopyrightedMediaProcessor", "init TXCopyrightedMediaProcessor failed. " + e2);
        }
    }

    public String a(String str) {
        if (this.f18168b != null && b(str)) {
            try {
                String str2 = (String) this.f18168b.invoke(null, str);
                if (TextUtils.isEmpty(str2)) {
                    Monitor.a(3, "TXCopyrightedMediaProcessor: getCopyrightedMusicUri failed with empty result.", "", 0);
                }
                return str2;
            } catch (Exception e2) {
                TXCLog.e("TXCopyrightedMediaProcessor", "getCopyrightedMusicUri failed. " + e2);
            }
        }
        return str;
    }

    public void b(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(j2, "PausePlay", j3);
    }

    public void c(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(j2, "ResumePlay", j3);
    }

    public void d(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(j2, "SeekEvent", j3);
    }

    public void e(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(j2, "FinishPlay", j3);
        a(j2, "StartPlay", 0L);
    }

    public void f(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(j2, "FinishPlay", j3);
        this.f18167a.remove(Long.valueOf(j2));
    }

    private boolean b(String str) {
        return str != null && str.startsWith("CopyRightMusic://");
    }

    public void a(long j2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (b(str)) {
            this.f18167a.put(Long.valueOf(j2), new a(j2, str, a(str)));
            a(j2, "StartPlay", 0L);
        }
    }

    public void a(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a aVar = this.f18167a.get(Long.valueOf(j2));
        if (aVar == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - aVar.f18173d < TimeUnit.SECONDS.toMillis(10L)) {
            return;
        }
        aVar.f18173d = jCurrentTimeMillis;
        a(j2, "TimedEvent", j3);
    }

    private void a(long j2, String str, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a aVar;
        if (this.f18169c == null || (aVar = this.f18167a.get(Long.valueOf(j2))) == null || !b(aVar.f18171b)) {
            return;
        }
        TXCLog.i("TXCopyrightedMediaProcessor", "reportPlayInfo id:" + j2 + " action:" + str + " position:" + j3);
        try {
            this.f18169c.invoke(null, str, aVar.f18172c, Long.valueOf(j3));
        } catch (Exception e2) {
            TXCLog.e("TXCopyrightedMediaProcessor", "reportPlayInfo failed. " + e2);
        }
    }
}
