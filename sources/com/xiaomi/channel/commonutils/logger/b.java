package com.xiaomi.channel.commonutils.logger;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public abstract class b {

    /* renamed from: a, reason: collision with root package name */
    private static int f24491a = 2;

    /* renamed from: a, reason: collision with other field name */
    private static LoggerInterface f90a = new a();

    /* renamed from: a, reason: collision with other field name */
    private static final HashMap<Integer, Long> f92a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private static final HashMap<Integer, String> f24492b = new HashMap<>();

    /* renamed from: a, reason: collision with other field name */
    private static final Integer f91a = -1;

    /* renamed from: a, reason: collision with other field name */
    private static AtomicInteger f93a = new AtomicInteger(1);

    public static int a() {
        return f24491a;
    }

    public static Integer a(String str) {
        if (f24491a > 1) {
            return f91a;
        }
        Integer numValueOf = Integer.valueOf(f93a.incrementAndGet());
        f92a.put(numValueOf, Long.valueOf(System.currentTimeMillis()));
        f24492b.put(numValueOf, str);
        f90a.log(str + " starts");
        return numValueOf;
    }

    public static void a(int i2) {
        if (i2 < 0 || i2 > 5) {
            a(2, "set log level as " + i2);
        }
        f24491a = i2;
    }

    public static void a(int i2, String str) {
        if (i2 >= f24491a) {
            f90a.log(str);
        }
    }

    public static void a(int i2, String str, Throwable th) {
        if (i2 >= f24491a) {
            f90a.log(str, th);
        }
    }

    public static void a(int i2, Throwable th) {
        if (i2 >= f24491a) {
            f90a.log("", th);
        }
    }

    public static void a(LoggerInterface loggerInterface) {
        f90a = loggerInterface;
    }

    public static void a(Integer num) {
        if (f24491a <= 1) {
            HashMap<Integer, Long> map = f92a;
            if (map.containsKey(num)) {
                long jLongValue = map.remove(num).longValue();
                String strRemove = f24492b.remove(num);
                long jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
                f90a.log(strRemove + " ends in " + jCurrentTimeMillis + " ms");
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m117a(String str) {
        a(2, "[Thread:" + Thread.currentThread().getId() + "] " + str);
    }

    public static void a(String str, Throwable th) {
        a(4, str, th);
    }

    public static void a(Throwable th) {
        a(4, th);
    }

    public static void b(String str) {
        a(0, str);
    }

    public static void c(String str) {
        a(1, "[Thread:" + Thread.currentThread().getId() + "] " + str);
    }

    public static void d(String str) {
        a(4, str);
    }
}
