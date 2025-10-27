package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class bd implements NativeExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private final Context f17682a;

    /* renamed from: b, reason: collision with root package name */
    private final as f17683b;

    /* renamed from: c, reason: collision with root package name */
    private final aa f17684c;

    /* renamed from: d, reason: collision with root package name */
    private final ac f17685d;

    public bd(Context context, aa aaVar, as asVar, ac acVar) {
        this.f17682a = context;
        this.f17683b = asVar;
        this.f17684c = aaVar;
        this.f17685d = acVar;
    }

    private static Map<String, String> a(String[] strArr) {
        HashMap map = new HashMap(strArr == null ? 1 : strArr.length);
        if (strArr != null) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                if (str != null) {
                    al.a("Extra message[%d]: %s", Integer.valueOf(i2), str);
                    String[] strArrSplit = str.split("=");
                    if (strArrSplit.length == 2) {
                        map.put(strArrSplit[0], strArrSplit[1]);
                    } else {
                        al.d("bad extraMsg %s", str);
                    }
                }
            }
        } else {
            al.c("not found extraMsg", new Object[0]);
        }
        return map;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final boolean getAndUpdateAnrState() {
        if (ay.a() == null) {
            return false;
        }
        ay ayVarA = ay.a();
        if (ayVarA.f17644a.get()) {
            al.c("anr is processing, return", new Object[0]);
            return false;
        }
        ActivityManager activityManager = ayVarA.f17645b;
        if (!((z.a(activityManager) || az.a(activityManager, 0L) == null) ? false : true)) {
            al.c("proc is not in anr, wait next check", new Object[0]);
            return false;
        }
        if (ayVarA.a(System.currentTimeMillis())) {
            return false;
        }
        return ayVarA.a(true);
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final void handleNativeException(int i2, int i3, long j2, long j3, String str, String str2, String str3, String str4, int i4, String str5, int i5, int i6, int i7, String str6, String str7) {
        al.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i2, i3, j2, j3, str, str2, str3, str4, i4, str5, i5, i6, i7, str6, str7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x017c A[PHI: r4
      0x017c: PHI (r4v10 java.lang.String) = (r4v9 java.lang.String), (r4v14 java.lang.String) binds: [B:37:0x0152, B:41:0x0162] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01a7 A[Catch: all -> 0x0225, TryCatch #1 {all -> 0x0225, blocks: (B:46:0x01a1, B:48:0x01a7, B:50:0x01b0), top: B:80:0x01a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01b0 A[Catch: all -> 0x0225, TRY_LEAVE, TryCatch #1 {all -> 0x0225, blocks: (B:46:0x01a1, B:48:0x01a7, B:50:0x01b0), top: B:80:0x01a1 }] */
    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleNativeException2(int r28, int r29, long r30, long r32, java.lang.String r34, java.lang.String r35, java.lang.String r36, java.lang.String r37, int r38, java.lang.String r39, int r40, int r41, int r42, java.lang.String r43, java.lang.String r44, java.lang.String[] r45) {
        /*
            Method dump skipped, instructions count: 565
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.bd.handleNativeException2(int, int, long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, int, int, int, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final CrashDetailBean packageCrashDatas(String str, String str2, long j2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z2, boolean z3) throws IOException {
        int i2;
        String str12;
        int iIndexOf;
        boolean zI = at.a().i();
        if (zI) {
            al.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.f17360b = 1;
        crashDetailBean.f17363e = this.f17684c.g();
        aa aaVar = this.f17684c;
        crashDetailBean.f17364f = aaVar.f17429o;
        crashDetailBean.f17365g = aaVar.q();
        crashDetailBean.f17371m = this.f17684c.f();
        crashDetailBean.f17372n = str3;
        crashDetailBean.f17373o = zI ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.f17374p = str4;
        String str13 = str5 != null ? str5 : "";
        crashDetailBean.f17375q = str13;
        crashDetailBean.f17376r = j2;
        crashDetailBean.f17379u = ap.c(str13.getBytes());
        crashDetailBean.A = str;
        crashDetailBean.B = str2;
        crashDetailBean.L = this.f17684c.s();
        crashDetailBean.f17366h = this.f17684c.p();
        crashDetailBean.f17367i = this.f17684c.A();
        crashDetailBean.f17380v = str8;
        String dumpFilePath = NativeCrashHandler.getInstance() != null ? NativeCrashHandler.getDumpFilePath() : null;
        String strA = be.a(dumpFilePath, str8);
        if (!ap.b(strA)) {
            crashDetailBean.Z = strA;
        }
        crashDetailBean.aa = be.b(dumpFilePath);
        crashDetailBean.f17381w = be.a(str9, at.f17581f, at.f17586k, at.f17591p);
        crashDetailBean.f17382x = be.a(str10, at.f17581f, null, true);
        crashDetailBean.N = str7;
        crashDetailBean.O = str6;
        crashDetailBean.P = str11;
        crashDetailBean.F = this.f17684c.k();
        crashDetailBean.G = this.f17684c.j();
        crashDetailBean.H = this.f17684c.l();
        crashDetailBean.I = ab.b(this.f17682a);
        crashDetailBean.J = ab.g();
        crashDetailBean.K = ab.h();
        if (z2) {
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.f17383y = ao.a();
            aa aaVar2 = this.f17684c;
            crashDetailBean.Q = aaVar2.f17415a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.f17384z = ap.a(this.f17684c.Q, at.f17583h);
            int iIndexOf2 = crashDetailBean.f17375q.indexOf("java:\n");
            if (iIndexOf2 > 0 && (i2 = iIndexOf2 + 6) < crashDetailBean.f17375q.length()) {
                String str14 = crashDetailBean.f17375q;
                String strSubstring = str14.substring(i2, str14.length() - 1);
                if (strSubstring.length() > 0 && crashDetailBean.f17384z.containsKey(crashDetailBean.B) && (iIndexOf = (str12 = crashDetailBean.f17384z.get(crashDetailBean.B)).indexOf(strSubstring)) > 0) {
                    String strSubstring2 = str12.substring(iIndexOf);
                    crashDetailBean.f17384z.put(crashDetailBean.B, strSubstring2);
                    crashDetailBean.f17375q = crashDetailBean.f17375q.substring(0, i2);
                    crashDetailBean.f17375q += strSubstring2;
                }
            }
            if (str == null) {
                crashDetailBean.A = this.f17684c.f17418d;
            }
            crashDetailBean.U = this.f17684c.z();
            aa aaVar3 = this.f17684c;
            crashDetailBean.V = aaVar3.f17438x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = this.f17684c.y();
        } else {
            crashDetailBean.C = -1L;
            crashDetailBean.D = -1L;
            crashDetailBean.E = -1L;
            if (crashDetailBean.f17381w == null) {
                crashDetailBean.f17381w = "This crash occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.Q = -1L;
            crashDetailBean.U = -1;
            crashDetailBean.V = -1;
            crashDetailBean.W = map;
            crashDetailBean.X = this.f17684c.y();
            crashDetailBean.f17384z = null;
            if (str == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (bArr != null) {
                crashDetailBean.f17383y = bArr;
            }
        }
        return crashDetailBean;
    }
}
