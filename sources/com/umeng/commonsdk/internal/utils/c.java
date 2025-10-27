package com.umeng.commonsdk.internal.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public class c {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f23223a;

        /* renamed from: b, reason: collision with root package name */
        public String f23224b;

        /* renamed from: c, reason: collision with root package name */
        public int f23225c;

        /* renamed from: d, reason: collision with root package name */
        public String f23226d;

        /* renamed from: e, reason: collision with root package name */
        public String f23227e;

        /* renamed from: f, reason: collision with root package name */
        public String f23228f;

        /* renamed from: g, reason: collision with root package name */
        public String f23229g;

        /* renamed from: h, reason: collision with root package name */
        public String f23230h;

        /* renamed from: i, reason: collision with root package name */
        public String f23231i;

        /* renamed from: j, reason: collision with root package name */
        public String f23232j;

        /* renamed from: k, reason: collision with root package name */
        public String f23233k;

        /* renamed from: l, reason: collision with root package name */
        public String f23234l;
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0130 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x012b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x013d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0138 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.umeng.commonsdk.internal.utils.c.a a() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 324
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.c.a():com.umeng.commonsdk.internal.utils.c$a");
    }

    public static String b() throws IOException {
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
        } catch (Exception unused) {
        }
        return str.trim();
    }

    public static String c() throws IOException {
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
        } catch (Exception unused) {
        }
        return str.trim();
    }

    public static String d() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"));
        } catch (Exception unused) {
        } catch (Throwable th2) {
            bufferedReader = null;
            th = th2;
        }
        try {
            String strTrim = bufferedReader.readLine().trim();
            try {
                bufferedReader.close();
                return strTrim;
            } catch (Throwable unused2) {
                return strTrim;
            }
        } catch (Exception unused3) {
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (Throwable unused4) {
                }
            }
            return "";
        } catch (Throwable th3) {
            th = th3;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Throwable unused5) {
                }
            }
            throw th;
        }
    }
}
