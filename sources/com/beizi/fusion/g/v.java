package com.beizi.fusion.g;

import android.content.Context;

/* loaded from: classes2.dex */
public class v {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final v f5265a = new v();
    }

    public static final v a() {
        return a.f5265a;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int b() {
        /*
            r6 = this;
            java.lang.String r0 = "ro.hardware"
            java.lang.String r0 = r6.a(r0)
            r1 = 0
            if (r0 != 0) goto La
            return r1
        La:
            java.lang.String r0 = r0.toLowerCase()
            r0.hashCode()
            int r2 = r0.hashCode()
            r3 = 2
            r4 = 1
            r5 = -1
            switch(r2) {
                case -1367724016: goto L5f;
                case -822798509: goto L54;
                case 109271: goto L49;
                case 3570999: goto L3e;
                case 3613077: goto L33;
                case 100361430: goto L28;
                case 937844646: goto L1d;
                default: goto L1b;
            }
        L1b:
            r1 = r5
            goto L68
        L1d:
            java.lang.String r1 = "android_x86"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L26
            goto L1b
        L26:
            r1 = 6
            goto L68
        L28:
            java.lang.String r1 = "intel"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L31
            goto L1b
        L31:
            r1 = 5
            goto L68
        L33:
            java.lang.String r1 = "vbox"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L3c
            goto L1b
        L3c:
            r1 = 4
            goto L68
        L3e:
            java.lang.String r1 = "ttvm"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L47
            goto L1b
        L47:
            r1 = 3
            goto L68
        L49:
            java.lang.String r1 = "nox"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L52
            goto L1b
        L52:
            r1 = r3
            goto L68
        L54:
            java.lang.String r1 = "vbox86"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L5d
            goto L1b
        L5d:
            r1 = r4
            goto L68
        L5f:
            java.lang.String r2 = "cancro"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L68
            goto L1b
        L68:
            switch(r1) {
                case 0: goto L6c;
                case 1: goto L6c;
                case 2: goto L6c;
                case 3: goto L6c;
                case 4: goto L6c;
                case 5: goto L6c;
                case 6: goto L6c;
                default: goto L6b;
            }
        L6b:
            goto L6d
        L6c:
            r3 = r4
        L6d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.g.v.b():int");
    }

    private int c() {
        String strA = a("ro.build.flavor");
        if (strA == null) {
            return 0;
        }
        String lowerCase = strA.toLowerCase();
        return (lowerCase.contains("vbox") || lowerCase.contains("sdk_gphone")) ? 1 : 2;
    }

    private int d() {
        String strA = a("ro.product.model");
        if (strA == null) {
            return 0;
        }
        String lowerCase = strA.toLowerCase();
        return (lowerCase.contains("google_sdk") || lowerCase.contains("emulator") || lowerCase.contains("android sdk built for x86")) ? 1 : 2;
    }

    private int e() {
        String strA = a("ro.product.manufacturer");
        if (strA == null) {
            return 0;
        }
        String lowerCase = strA.toLowerCase();
        return (lowerCase.contains("genymotion") || lowerCase.contains("netease")) ? 1 : 2;
    }

    private int f() {
        String strA = a("ro.product.board");
        if (strA == null) {
            return 0;
        }
        String lowerCase = strA.toLowerCase();
        return (lowerCase.contains("android") || lowerCase.contains("goldfish")) ? 1 : 2;
    }

    private int g() {
        String strA = a("ro.board.platform");
        if (strA == null) {
            return 0;
        }
        return strA.toLowerCase().contains("android") ? 1 : 2;
    }

    private int h() {
        String strA = a("gsm.version.baseband");
        if (strA == null) {
            return 0;
        }
        return strA.contains("1.0.0.0") ? 1 : 2;
    }

    private v() {
    }

    public boolean a(Context context) {
        int i2;
        if (context == null) {
            return true;
        }
        int iB = b();
        if (iB == 0) {
            i2 = 1;
        } else {
            if (iB == 1) {
                return true;
            }
            i2 = 0;
        }
        int iC = c();
        if (iC == 0) {
            i2++;
        } else if (iC == 1) {
            return true;
        }
        int iD = d();
        if (iD == 0) {
            i2++;
        } else if (iD == 1) {
            return true;
        }
        int iE = e();
        if (iE == 0) {
            i2++;
        } else if (iE == 1) {
            return true;
        }
        int iF = f();
        if (iF == 0) {
            i2++;
        } else if (iF == 1) {
            return true;
        }
        int iG = g();
        if (iG == 0) {
            i2++;
        } else if (iG == 1) {
            return true;
        }
        int iH = h();
        if (iH == 0) {
            i2 += 2;
        } else if (iH == 1) {
            return true;
        }
        if (!c(context)) {
            i2++;
        }
        if (!b(context)) {
            i2++;
        }
        if (!d(context)) {
            i2++;
        }
        return i2 > 3;
    }

    private boolean b(Context context) {
        try {
            return context.getPackageManager().hasSystemFeature("android.hardware.camera");
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private boolean c(Context context) {
        try {
            return context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private boolean d(Context context) {
        try {
            return context.getPackageManager().hasSystemFeature("android.hardware.bluetooth");
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private String a(String str) {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
