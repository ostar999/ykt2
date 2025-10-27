package com.huawei.hms.opendevice;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.huawei.android.hms.openid.R;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.IOUtils;
import com.huawei.secure.android.common.encrypt.utils.BaseKeyUtil;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.RootKeyUtil;
import com.huawei.secure.android.common.encrypt.utils.WorkKeyCryptUtil;
import com.huawei.secure.android.common.util.IOUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class c {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7900a = "c";

    /* renamed from: b, reason: collision with root package name */
    public static Map<String, String> f7901b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    public static final Object f7902c = new Object();

    public static String a() {
        return "2A57086C86EF54970C1E6EB37BFC72B1";
    }

    public static byte[] a(String str, String str2, String str3, String str4) {
        return Build.VERSION.SDK_INT >= 26 ? BaseKeyUtil.exportRootKey(str, str2, str3, str4, 32, true) : BaseKeyUtil.exportRootKey(str, str2, str3, str4, 32, false);
    }

    public static byte[] b() {
        return a(d(), e(), c(), g());
    }

    public static void c(Context context) {
        synchronized (f7902c) {
            d(context.getApplicationContext());
            if (i()) {
                HMSLog.i(f7900a, "The local secret is already in separate file mode.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(p.c(context.getApplicationContext()));
            sb.append("/shared_prefs/LocalAvengers.xml");
            File file = new File(sb.toString());
            if (file.exists()) {
                IOUtil.deleteSecure(file);
                HMSLog.i(f7900a, "destroy C, delete file LocalAvengers.xml.");
            }
            byte[] bArrGenerateSecureRandom = EncryptUtil.generateSecureRandom(32);
            byte[] bArrGenerateSecureRandom2 = EncryptUtil.generateSecureRandom(32);
            byte[] bArrGenerateSecureRandom3 = EncryptUtil.generateSecureRandom(32);
            byte[] bArrGenerateSecureRandom4 = EncryptUtil.generateSecureRandom(32);
            String strA = a.a(bArrGenerateSecureRandom);
            String strA2 = a.a(bArrGenerateSecureRandom2);
            String strA3 = a.a(bArrGenerateSecureRandom3);
            String strA4 = a.a(bArrGenerateSecureRandom4);
            a(strA, strA2, strA3, strA4, WorkKeyCryptUtil.encryptWorkKey(a.a(EncryptUtil.generateSecureRandom(32)), a(strA, strA2, strA3, strA4)), context);
            HMSLog.i(f7900a, "generate D.");
        }
    }

    public static void d(Context context) throws Throwable {
        if (i()) {
            HMSLog.i(f7900a, "secretKeyCache not empty.");
            return;
        }
        f7901b.clear();
        String strC = p.c(context);
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        String strA = s.a(strC + "/files/math/m");
        String strA2 = s.a(strC + "/files/panda/p");
        String strA3 = s.a(strC + "/files/panda/d");
        String strA4 = s.a(strC + "/files/math/t");
        String strA5 = s.a(strC + "/files/s");
        if (t.a(strA, strA2, strA3, strA4, strA5)) {
            f7901b.put("m", strA);
            f7901b.put("p", strA2);
            f7901b.put("d", strA3);
            f7901b.put("t", strA4);
            f7901b.put("s", strA5);
        }
    }

    public static synchronized String e(Context context) {
        String strDecryptWorkKey = WorkKeyCryptUtil.decryptWorkKey(f(), b());
        if (t.a(strDecryptWorkKey)) {
            HMSLog.i(f7900a, "keyS has been upgraded, no require operate again.");
            return strDecryptWorkKey;
        }
        String strDecryptWorkKey2 = WorkKeyCryptUtil.decryptWorkKey(f(), h());
        if (t.a(strDecryptWorkKey2)) {
            HMSLog.i(f7900a, "keyS is encrypt by RootKeyUtil, upgrade encrypt mode.");
            a(WorkKeyCryptUtil.encryptWorkKey(strDecryptWorkKey2, b()), context);
            return strDecryptWorkKey2;
        }
        String strDecryptWorkKey3 = WorkKeyCryptUtil.decryptWorkKey(f(), BaseKeyUtil.exportRootKey(d(), e(), c(), g(), 32, false));
        if (!t.a(strDecryptWorkKey3)) {
            HMSLog.e(f7900a, "all mode unable to decrypt root key.");
            return "";
        }
        HMSLog.i(f7900a, "keyS is encrypt by ExportRootKey with sha1, upgrade encrypt mode to sha256.");
        a(WorkKeyCryptUtil.encryptWorkKey(strDecryptWorkKey3, b()), context);
        return strDecryptWorkKey3;
    }

    public static String f() {
        return a("s");
    }

    public static String g() {
        return a("t");
    }

    public static RootKeyUtil h() {
        return RootKeyUtil.newInstance(d(), e(), c(), g());
    }

    public static boolean i() {
        return !TextUtils.isEmpty(f());
    }

    public static String b(Context context) {
        if (!i()) {
            HMSLog.i(f7900a, "work key is empty, execute init.");
            c(context);
        }
        String strDecryptWorkKey = WorkKeyCryptUtil.decryptWorkKey(f(), b());
        return t.a(strDecryptWorkKey) ? strDecryptWorkKey : e(context);
    }

    public static byte[] a(Context context) {
        byte[] bArrA = a.a(context.getString(R.string.push_cat_head));
        byte[] bArrA2 = a.a(context.getString(R.string.push_cat_body));
        return a(a(a(bArrA, bArrA2), a.a(a())));
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length == 0 || bArr2.length == 0) {
            return new byte[0];
        }
        int length = bArr.length;
        if (length != bArr2.length) {
            return new byte[0];
        }
        byte[] bArr3 = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
        return bArr3;
    }

    public static byte[] a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return new byte[0];
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (bArr[i2] >> 2);
        }
        return bArr;
    }

    public static void a(String str, String str2, String str3, String str4, String str5, Context context) throws Throwable {
        String strC = p.c(context.getApplicationContext());
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(strC);
            sb.append("/files/math/m");
            a("m", str, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(strC);
            sb2.append("/files/panda/p");
            a("p", str2, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(strC);
            sb3.append("/files/panda/d");
            a("d", str3, sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(strC);
            sb4.append("/files/math/t");
            a("t", str4, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(strC);
            sb5.append("/files/s");
            a("s", str5, sb5.toString());
        } catch (IOException unused) {
            HMSLog.e(f7900a, "save key IOException.");
        }
    }

    public static String e() {
        return a("p");
    }

    public static String d() {
        return a("m");
    }

    public static String c() {
        return a("d");
    }

    public static void a(String str, Context context) throws Throwable {
        String strC = p.c(context.getApplicationContext());
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(strC);
            sb.append("/files/s");
            a("s", str, sb.toString());
        } catch (IOException unused) {
            HMSLog.e(f7900a, "save keyS IOException.");
        }
    }

    public static void a(String str, String str2, String str3) throws Throwable {
        OutputStreamWriter outputStreamWriter;
        BufferedWriter bufferedWriter;
        HMSLog.i(f7900a, "save local secret key.");
        BufferedWriter bufferedWriter2 = null;
        try {
            File file = new File(str3);
            s.a(file);
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            try {
                bufferedWriter = new BufferedWriter(outputStreamWriter);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            outputStreamWriter = null;
        }
        try {
            bufferedWriter.write(str2);
            bufferedWriter.flush();
            f7901b.put(str, str2);
            IOUtils.closeQuietly((Writer) outputStreamWriter);
            IOUtils.closeQuietly((Writer) bufferedWriter);
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            IOUtils.closeQuietly((Writer) outputStreamWriter);
            IOUtils.closeQuietly((Writer) bufferedWriter2);
            throw th;
        }
    }

    public static String a(String str) {
        String str2 = f7901b.get(str);
        return TextUtils.isEmpty(str2) ? "" : str2;
    }
}
