package com.ta.utdid2.device;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.ta.a.e.g;
import com.ta.a.e.h;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.signers.PSSSigner;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f17268a;

    /* renamed from: a, reason: collision with other field name */
    private com.ta.utdid2.b.a.c f89a;

    /* renamed from: e, reason: collision with root package name */
    private String f17274e = null;
    private Context mContext;

    /* renamed from: b, reason: collision with root package name */
    private static Pattern f17269b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    /* renamed from: d, reason: collision with root package name */
    private static final Object f17270d = new Object();

    /* renamed from: i, reason: collision with root package name */
    private static final String f17272i = ".UTSystemConfig" + File.separator + "Global";

    /* renamed from: f, reason: collision with root package name */
    private static int f17271f = 0;

    /* renamed from: j, reason: collision with root package name */
    private static String f17273j = "";

    public interface a {
        void i();
    }

    private c(Context context) {
        this.f89a = null;
        this.mContext = context;
        com.ta.a.a.a().a(context);
        this.f89a = new com.ta.utdid2.b.a.c(context, f17272i, "Alvin2");
    }

    private static String b(byte[] bArr) throws Exception {
        byte[] bArr2 = {69, 114, 116, -33, 125, -54, -31, 86, -11, 11, -78, -96, -17, -99, SignedBytes.MAX_POWER_OF_TWO, 23, -95, -126, -82, -64, 113, 116, -16, -103, TarConstants.LF_LINK, -30, 9, -39, 33, -80, PSSSigner.TRAILER_IMPLICIT, -78, -117, TarConstants.LF_DIR, Ascii.RS, -122, SignedBytes.MAX_POWER_OF_TWO, -104, 74, -49, 106, 85, -38, -93};
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(g.b(bArr2), mac.getAlgorithm()));
        return com.ta.utdid2.a.a.b.encodeToString(mac.doFinal(bArr), 2);
    }

    public static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        if (24 == str.length()) {
            return !f17269b.matcher(str).find();
        }
        return false;
    }

    private void f(final String str) {
        if (c(str)) {
            f17271f = 6;
            h.m109a("UTUtdid", "utdid type:", 6);
            this.f89a.a(str, f17271f);
            a(new a() { // from class: com.ta.utdid2.device.c.1
                @Override // com.ta.utdid2.device.c.a
                public void i() {
                    c.this.h(str);
                    c.this.f89a.d();
                }
            });
        }
    }

    private void g(final String str) {
        if (c(str)) {
            a(new a() { // from class: com.ta.utdid2.device.c.2
                @Override // com.ta.utdid2.device.c.a
                public void i() {
                    if (str.equals(c.this.f89a.o())) {
                        return;
                    }
                    c.this.f89a.e(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h(String str) {
        if (c(str)) {
            try {
                Settings.System.putString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk", str);
            } catch (Exception unused) {
            }
        }
    }

    private String s() {
        String strT = t();
        if (c(strT)) {
            if (TextUtils.isEmpty(strT) || !strT.endsWith("\n")) {
                this.f17274e = strT;
            } else {
                this.f17274e = strT.substring(0, strT.length() - 1);
            }
            return this.f17274e;
        }
        try {
            byte[] bArrC = c();
            if (bArrC == null) {
                return null;
            }
            String strEncodeToString = com.ta.utdid2.a.a.b.encodeToString(bArrC, 2);
            this.f17274e = strEncodeToString;
            f17271f = 6;
            f(strEncodeToString);
            return this.f17274e;
        } catch (Exception e2) {
            h.a("", e2, new Object[0]);
            return null;
        }
    }

    public static void setExtendFactor(String str) {
        f17273j = str;
    }

    public static void setType(int i2) {
        f17271f = i2;
    }

    private String t() {
        String strU = u();
        if (c(strU)) {
            f17271f = 2;
            h.m109a("UTUtdid", "utdid type", 2);
            g(strU);
            return strU;
        }
        String strV = v();
        if (c(strV)) {
            f17271f = 2;
            h.m109a("UTUtdid", "utdid type", 2);
            g(strV);
            return strV;
        }
        final String strO = this.f89a.o();
        if (c(strO)) {
            int iA = this.f89a.a();
            if (iA == 0) {
                f17271f = 1;
            } else {
                f17271f = iA;
            }
            h.m109a("UTUtdid", "get utdid from sp. type", Integer.valueOf(f17271f));
            a(new a() { // from class: com.ta.utdid2.device.c.3
                @Override // com.ta.utdid2.device.c.a
                public void i() {
                    c.this.h(strO);
                    if (c.c(c.this.f89a.p())) {
                        return;
                    }
                    c.this.f89a.d();
                }
            });
            return strO;
        }
        final String strP = this.f89a.p();
        if (!c(strP)) {
            h.m109a("UTUtdid", "read utdid is null");
            Log.d("UTUtdid", "read utdid is null");
            return null;
        }
        f17271f = 3;
        h.m109a("UTUtdid", "utdid type", 3);
        this.f89a.a(f17271f);
        a(new a() { // from class: com.ta.utdid2.device.c.4
            @Override // com.ta.utdid2.device.c.a
            public void i() {
                c.this.h(strP);
            }
        });
        return strP;
    }

    private String u() {
        try {
            return Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
        } catch (Exception unused) {
            return "";
        }
    }

    private String v() {
        String string;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused) {
            string = null;
        }
        try {
            if (com.ta.utdid2.a.a.f.b(string)) {
                return "";
            }
            e eVar = new e();
            String strH = eVar.h(string);
            if (c(strH)) {
                h.m109a("UTUtdid", "OldSettings_1", strH);
                h(strH);
                return strH;
            }
            String strG = eVar.g(string);
            if (c(strG)) {
                h.m109a("UTUtdid", "OldSettings_2", strG);
                h(strG);
                return strG;
            }
            String strG2 = new d().g(string);
            if (!c(strG2)) {
                return "";
            }
            h.m109a("UTUtdid", "OldSettings_3", strG2);
            h(strG2);
            return strG2;
        } catch (Throwable th) {
            h.b("UTUtdid", th, new Object[0]);
            return "";
        }
    }

    public synchronized String getValue() {
        String str = this.f17274e;
        if (str != null) {
            return str;
        }
        return s();
    }

    public static c a(Context context) {
        if (context != null && f17268a == null) {
            synchronized (f17270d) {
                if (f17268a == null) {
                    f17268a = new c(context);
                }
            }
        }
        return f17268a;
    }

    private byte[] c() throws Exception {
        String str;
        h.m109a("UTUtdid", "generateUtdid");
        Log.d("UTUtdid", "generateUtdid");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bytes = com.ta.utdid2.a.a.d.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = com.ta.utdid2.a.a.d.getBytes(iNextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            str = f17273j + com.ta.utdid2.a.a.e.e(this.mContext);
        } catch (Exception unused) {
            str = f17273j + new Random().nextInt();
        }
        byteArrayOutputStream.write(com.ta.utdid2.a.a.d.getBytes(com.ta.utdid2.a.a.f.a(str)), 0, 4);
        byteArrayOutputStream.write(com.ta.utdid2.a.a.d.getBytes(com.ta.utdid2.a.a.f.a(b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    public static void a(final a aVar) {
        new Thread(new Runnable() { // from class: com.ta.utdid2.device.c.5
            @Override // java.lang.Runnable
            public void run() {
                if (!com.ta.a.e.e.a()) {
                    com.ta.a.e.e.e();
                } else {
                    aVar.i();
                    com.ta.a.e.e.e();
                }
            }
        }).start();
    }
}
