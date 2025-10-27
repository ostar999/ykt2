package com.xiaomi.push;

/* loaded from: classes6.dex */
public class ke {

    /* renamed from: a, reason: collision with root package name */
    private static int f25511a = Integer.MAX_VALUE;

    public static void a(kb kbVar, byte b3) {
        a(kbVar, b3, f25511a);
    }

    public static void a(kb kbVar, byte b3, int i2) throws jv {
        if (i2 <= 0) {
            throw new jv("Maximum skip depth exceeded");
        }
        int i3 = 0;
        switch (b3) {
            case 2:
                kbVar.mo672a();
                return;
            case 3:
                kbVar.a();
                return;
            case 4:
                kbVar.mo659a();
                return;
            case 5:
            case 7:
            case 9:
            default:
                return;
            case 6:
                kbVar.mo669a();
                return;
            case 8:
                kbVar.mo660a();
                return;
            case 10:
                kbVar.mo661a();
                return;
            case 11:
                kbVar.mo668a();
                return;
            case 12:
                kbVar.mo666a();
                while (true) {
                    byte b4 = kbVar.mo662a().f25502a;
                    if (b4 == 0) {
                        kbVar.g();
                        return;
                    } else {
                        a(kbVar, b4, i2 - 1);
                        kbVar.h();
                    }
                }
            case 13:
                ka kaVarMo664a = kbVar.mo664a();
                while (i3 < kaVarMo664a.f932a) {
                    int i4 = i2 - 1;
                    a(kbVar, kaVarMo664a.f25507a, i4);
                    a(kbVar, kaVarMo664a.f25508b, i4);
                    i3++;
                }
                kbVar.i();
                return;
            case 14:
                kf kfVarMo665a = kbVar.mo665a();
                while (i3 < kfVarMo665a.f933a) {
                    a(kbVar, kfVarMo665a.f25512a, i2 - 1);
                    i3++;
                }
                kbVar.k();
                return;
            case 15:
                jz jzVarMo663a = kbVar.mo663a();
                while (i3 < jzVarMo663a.f929a) {
                    a(kbVar, jzVarMo663a.f25503a, i2 - 1);
                    i3++;
                }
                kbVar.j();
                return;
        }
    }
}
