package com.beizi.fusion.e.a;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.beizi.fusion.g.ac;
import com.hyphenate.easeui.utils.RomUtils;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private a f5004a;

    public interface a {
        void a(String str);
    }

    public b(a aVar) {
        this.f5004a = aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String e() {
        return Build.MANUFACTURER.toUpperCase();
    }

    public boolean c() throws ClassNotFoundException {
        String strA = a("ro.build.freeme.label");
        return !TextUtils.isEmpty(strA) && strA.equalsIgnoreCase("FREEMEOS");
    }

    public boolean d() throws ClassNotFoundException {
        String strA = a("ro.ssui.product");
        return (TextUtils.isEmpty(strA) || strA.equalsIgnoreCase("unknown")) ? false : true;
    }

    public static boolean b() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
            String str = Build.BRAND;
            if (!str.equalsIgnoreCase("HUAWEI") && !str.equalsIgnoreCase("HONOR")) {
                return false;
            }
        }
        return true;
    }

    public void a(Context context) {
        String strA;
        a aVar;
        try {
            ac.c("BeiZis", "init oaid " + e());
            if ("ASUS".equals(e().toUpperCase()) || b()) {
                b(context);
            } else if (RomUtils.ROM_LENOVO.equals(e().toUpperCase()) || "MOTOLORA".equals(e().toUpperCase())) {
                new d(context).a(this.f5004a);
            } else {
                if (!"MEIZU".equals(e().toUpperCase())) {
                    if (RomUtils.ROM_NUBIA.equals(e().toUpperCase())) {
                        strA = new f(context).a();
                    } else if (a() || "SAMSUNG".equals(e().toUpperCase())) {
                        b(context);
                    } else if (RomUtils.ROM_VIVO.equals(e().toUpperCase())) {
                        strA = new j(context).a();
                    } else if ("XIAOMI".equals(e().toUpperCase()) || "BLACKSHARK".equals(e().toUpperCase())) {
                        strA = new k(context).a();
                    } else if ("ONEPLUS".equals(e().toUpperCase()) || RomUtils.ROM_ZTE.equals(e().toUpperCase()) || "FERRMEOS".equals(e().toUpperCase()) || c() || "SSUI".equals(e().toUpperCase()) || d()) {
                        b(context);
                    }
                    aVar = this.f5004a;
                    if (aVar != null || strA == null) {
                    }
                    aVar.a(strA);
                    return;
                }
                new e(context).a(this.f5004a);
            }
            strA = null;
            aVar = this.f5004a;
            if (aVar != null) {
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void b(final Context context) {
        new Thread(new Runnable() { // from class: com.beizi.fusion.e.a.b.1
            @Override // java.lang.Runnable
            public void run() {
                if (context == null) {
                    return;
                }
                try {
                    if ("ASUS".equals(b.this.e().toUpperCase())) {
                        new com.beizi.fusion.e.a.a(context).a(b.this.f5004a);
                    } else if (b.b()) {
                        new c(context).a(b.this.f5004a);
                    } else if (b.a()) {
                        new h(context).a(b.this.f5004a);
                    } else if ("ONEPLUS".equals(b.this.e().toUpperCase())) {
                        new g(context).a(b.this.f5004a);
                    } else if (RomUtils.ROM_ZTE.equals(b.this.e().toUpperCase()) || "FERRMEOS".equals(b.this.e().toUpperCase()) || b.this.c() || "SSUI".equals(b.this.e().toUpperCase()) || b.this.d()) {
                        new l(context).a(b.this.f5004a);
                    } else if ("SAMSUNG".equals(b.this.e().toUpperCase())) {
                        new i(context).a(b.this.f5004a);
                    }
                } catch (Throwable unused) {
                    ac.c("BeiZis", "getIDFromNewThead exception");
                }
            }
        }).start();
    }

    public static boolean a() {
        if (!Build.MANUFACTURER.equalsIgnoreCase(RomUtils.ROM_OPPO)) {
            String str = Build.BRAND;
            if (!str.equalsIgnoreCase(RomUtils.ROM_OPPO) && !str.equalsIgnoreCase("REALME") && TextUtils.isEmpty(a("ro.build.version.opporom", ""))) {
                return false;
            }
        }
        return true;
    }

    public static String a(String str, String str2) throws ClassNotFoundException {
        String str3;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str3 = (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            ac.c("BeiZis", "System property invoke error: " + e2);
            str3 = null;
        }
        return str3 == null ? "" : str3;
    }

    private String a(String str) throws ClassNotFoundException {
        if (str == null) {
            return null;
        }
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "unknown");
        } catch (Exception unused) {
            return null;
        }
    }
}
