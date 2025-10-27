package com.tencent.tbs.one.impl.a;

import android.util.Log;
import java.util.Locale;

/* loaded from: classes6.dex */
public final class g {

    /* renamed from: a, reason: collision with root package name */
    public static a f21744a = new a(0);

    public static class a implements b {

        /* renamed from: a, reason: collision with root package name */
        public b f21745a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f21746b;

        public a() {
            this.f21746b = false;
        }

        public /* synthetic */ a(byte b3) {
            this();
        }

        @Override // com.tencent.tbs.one.impl.a.g.b
        public final void a(int i2, String str) {
            b bVar = this.f21745a;
            if (bVar != null) {
                bVar.a(i2, str);
            }
            if (5 == i2 || this.f21746b) {
                Log.println(i2, "TBSOne", str);
            }
        }
    }

    public interface b {
        void a(int i2, String str);
    }

    public static String a(String str, Throwable th, Object... objArr) {
        if (objArr != null && ((th == null && objArr.length > 0) || objArr.length > 1)) {
            str = String.format(Locale.US, str, objArr);
        }
        if (th == null) {
            return str;
        }
        return str + '\n' + Log.getStackTraceString(th);
    }

    public static Throwable a(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }
        Object obj = objArr[objArr.length - 1];
        if (obj instanceof Throwable) {
            return (Throwable) obj;
        }
        return null;
    }

    public static void a(b bVar) {
        f21744a.f21745a = bVar;
    }

    public static void a(String str, Object... objArr) {
        try {
            f21744a.a(4, a(str, a(objArr), objArr));
        } catch (Throwable th) {
            th.getMessage();
        }
    }

    public static void a(boolean z2) {
        f21744a.f21746b = z2;
    }

    public static void b(String str, Object... objArr) {
        try {
            f21744a.a(5, a(str, a(objArr), objArr));
        } catch (Throwable th) {
            th.getMessage();
        }
    }

    public static void c(String str, Object... objArr) {
        try {
            f21744a.a(6, a(str, a(objArr), objArr));
        } catch (Throwable th) {
            th.getMessage();
        }
    }
}
