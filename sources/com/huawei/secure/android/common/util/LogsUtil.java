package com.huawei.secure.android.common.util;

import android.text.TextUtils;
import android.util.Log;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class LogsUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final Pattern f8437a = Pattern.compile("[0-9]*[a-z|A-Z]*[一-龥]*");

    /* renamed from: b, reason: collision with root package name */
    private static final char f8438b = '*';

    /* renamed from: c, reason: collision with root package name */
    private static final int f8439c = 2;

    public static class a extends Throwable {

        /* renamed from: d, reason: collision with root package name */
        private static final long f8440d = 7129050843360571879L;

        /* renamed from: a, reason: collision with root package name */
        private String f8441a;

        /* renamed from: b, reason: collision with root package name */
        private Throwable f8442b;

        /* renamed from: c, reason: collision with root package name */
        private Throwable f8443c;

        public a(Throwable th) {
            this.f8443c = th;
        }

        public void a(Throwable th) {
            this.f8442b = th;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            Throwable th = this.f8442b;
            if (th == this) {
                return null;
            }
            return th;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            return this.f8441a;
        }

        @Override // java.lang.Throwable
        public String toString() {
            Throwable th = this.f8443c;
            if (th == null) {
                return "";
            }
            String name = th.getClass().getName();
            if (this.f8441a == null) {
                return name;
            }
            String str = name + ": ";
            if (this.f8441a.startsWith(str)) {
                return this.f8441a;
            }
            return str + this.f8441a;
        }

        public void a(String str) {
            this.f8441a = str;
        }
    }

    private static String a(String str, boolean z2) {
        StringBuilder sb = new StringBuilder(512);
        if (!TextUtils.isEmpty(str)) {
            if (z2) {
                sb.append(a(str));
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (i2 % 2 == 0) {
                charArray[i2] = f8438b;
            }
        }
        return new String(charArray);
    }

    public static void d(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.d(str, a(str2, z2));
    }

    public static void e(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.e(str, a(str2, z2));
    }

    public static void i(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.i(str, a(str2, z2));
    }

    public static void w(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.w(str, a(str2, z2));
    }

    public static void d(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.d(str, a(str2, str3));
    }

    public static void e(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.e(str, a(str2, str3));
    }

    public static void i(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.i(str, a(str2, str3));
    }

    public static void w(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.w(str, a(str2, str3));
    }

    public static void d(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.d(str, a(str2, str3), a(th));
    }

    public static void e(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.e(str, a(str2, str3), a(th));
    }

    public static void i(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.i(str, a(str2, str3), a(th));
    }

    public static void w(String str, String str2, String str3, Throwable th) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        Log.w(str, a(str2, str3), a(th));
    }

    private static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder(512);
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            sb.append(a(str2));
        }
        return sb.toString();
    }

    public static void d(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.d(str, a(str2, false));
    }

    public static void e(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.e(str, a(str2, false));
    }

    public static void i(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.i(str, a(str2, false));
    }

    public static void w(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.w(str, a(str2, false));
    }

    public static void d(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.d(str, a(str2, z2), a(th));
    }

    public static void e(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.e(str, a(str2, z2), a(th));
    }

    public static void i(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.i(str, a(str2, z2), a(th));
    }

    public static void w(String str, String str2, Throwable th, boolean z2) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.w(str, a(str2, z2), a(th));
    }

    public static void d(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.d(str, a(str2, false), a(th));
    }

    public static void e(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.e(str, a(str2, false), a(th));
    }

    public static void i(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.i(str, a(str2, false), a(th));
    }

    public static void w(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str2) && th == null) {
            return;
        }
        Log.w(str, a(str2, false), a(th));
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        int i2 = 1;
        if (1 == length) {
            return String.valueOf(f8438b);
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (f8437a.matcher(String.valueOf(cCharAt)).matches()) {
                if (i2 % 2 == 0) {
                    cCharAt = '*';
                }
                i2++;
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private static Throwable a(Throwable th) {
        if (th == null) {
            return null;
        }
        a aVar = new a(th);
        aVar.setStackTrace(th.getStackTrace());
        aVar.a(b(th.getMessage()));
        Throwable cause = th.getCause();
        a aVar2 = aVar;
        while (cause != null) {
            a aVar3 = new a(cause);
            aVar3.setStackTrace(cause.getStackTrace());
            aVar3.a(b(cause.getMessage()));
            aVar2.a(aVar3);
            cause = cause.getCause();
            aVar2 = aVar3;
        }
        return aVar;
    }
}
