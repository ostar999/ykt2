package com.huawei.hms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.ads.R;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public abstract class d {

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f7456a = new byte[0];

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f7457b = new byte[0];

    /* renamed from: c, reason: collision with root package name */
    private static SoftReference<byte[]> f7458c;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final Long f7461a = 120000L;

        /* renamed from: h, reason: collision with root package name */
        private static final byte[] f7462h = new byte[0];

        /* renamed from: i, reason: collision with root package name */
        private static volatile a f7463i;

        /* renamed from: b, reason: collision with root package name */
        private SharedPreferences f7464b;

        /* renamed from: c, reason: collision with root package name */
        private SharedPreferences f7465c;

        /* renamed from: d, reason: collision with root package name */
        private SharedPreferences f7466d;

        /* renamed from: e, reason: collision with root package name */
        private final byte[] f7467e = new byte[0];

        /* renamed from: f, reason: collision with root package name */
        private final byte[] f7468f = new byte[0];

        /* renamed from: g, reason: collision with root package name */
        private final byte[] f7469g = new byte[0];

        /* renamed from: j, reason: collision with root package name */
        private Context f7470j;

        private a(Context context) {
            this.f7464b = null;
            this.f7465c = null;
            this.f7466d = null;
            try {
                this.f7470j = context.getApplicationContext();
                Context contextA = e.a(context);
                this.f7464b = contextA.getSharedPreferences("identifier_sp_story_book_file", 4);
                this.f7465c = contextA.getSharedPreferences("identifier_hiad_sp_bed_rock_file", 4);
                this.f7466d = contextA.getSharedPreferences("identifier_hiad_sp_red_stone_file", 4);
            } catch (Throwable th) {
                Log.w("Aes128", "get SharedPreference error: " + th.getClass().getSimpleName());
            }
        }

        public static a a(Context context) {
            a aVar;
            if (f7463i != null) {
                return f7463i;
            }
            synchronized (f7462h) {
                if (f7463i == null) {
                    f7463i = new a(context);
                }
                aVar = f7463i;
            }
            return aVar;
        }

        public String a() {
            synchronized (this.f7468f) {
                SharedPreferences sharedPreferences = this.f7466d;
                if (sharedPreferences == null) {
                    return "";
                }
                String string = sharedPreferences.getString("read_first_chapter", "");
                if (TextUtils.isEmpty(string)) {
                    return string;
                }
                return d.a(string, d.a(this.f7470j));
            }
        }

        public void a(String str) {
            synchronized (this.f7468f) {
                if (this.f7466d == null) {
                    return;
                }
                this.f7466d.edit().putString("read_first_chapter", d.b(str, d.a(this.f7470j))).apply();
            }
        }

        public void b() {
            synchronized (this.f7469g) {
                SharedPreferences sharedPreferences = this.f7464b;
                if (sharedPreferences == null) {
                    return;
                }
                sharedPreferences.edit().putLong("read_first_chapter_time", System.currentTimeMillis()).apply();
            }
        }

        public void b(String str) {
            synchronized (this.f7467e) {
                SharedPreferences sharedPreferences = this.f7465c;
                if (sharedPreferences == null) {
                    return;
                }
                sharedPreferences.edit().putString("get_a_book", str).commit();
            }
        }

        public void c(String str) {
            synchronized (this.f7469g) {
                SharedPreferences sharedPreferences = this.f7464b;
                if (sharedPreferences == null) {
                    return;
                }
                sharedPreferences.edit().putString("catch_a_cat", str).commit();
            }
        }

        public boolean c() {
            synchronized (this.f7469g) {
                SharedPreferences sharedPreferences = this.f7464b;
                if (sharedPreferences == null) {
                    return false;
                }
                long j2 = sharedPreferences.getLong("read_first_chapter_time", -1L);
                if (j2 < 0) {
                    return false;
                }
                return j2 + f7461a.longValue() > System.currentTimeMillis();
            }
        }

        public void d() {
            synchronized (this.f7469g) {
                SharedPreferences sharedPreferences = this.f7464b;
                if (sharedPreferences == null) {
                    return;
                }
                sharedPreferences.edit().putBoolean("has_read_first_chapter", true).apply();
            }
        }

        public void d(String str) {
            synchronized (this.f7469g) {
                this.f7464b.edit().putString("read_second_chapter", str).apply();
            }
        }

        public boolean e() {
            synchronized (this.f7469g) {
                SharedPreferences sharedPreferences = this.f7464b;
                if (sharedPreferences == null) {
                    return false;
                }
                return sharedPreferences.getBoolean("has_read_first_chapter", false);
            }
        }

        public String f() {
            synchronized (this.f7467e) {
                SharedPreferences sharedPreferences = this.f7465c;
                if (sharedPreferences == null) {
                    return null;
                }
                return sharedPreferences.getString("get_a_book", null);
            }
        }

        public String g() {
            synchronized (this.f7469g) {
                SharedPreferences sharedPreferences = this.f7464b;
                if (sharedPreferences == null) {
                    return null;
                }
                String string = sharedPreferences.getString("catch_a_cat", null);
                if (string == null) {
                    string = d.a(d.a());
                    c(string);
                }
                return string;
            }
        }

        public String h() {
            String string;
            synchronized (this.f7469g) {
                string = this.f7464b.getString("read_second_chapter", "");
            }
            return string;
        }
    }

    private static String a(Context context, a aVar) {
        String strA = a(b());
        aVar.b(b(strA, d(context)));
        return strA;
    }

    public static String a(String str, String str2) {
        String strA;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        synchronized (f7456a) {
            try {
                strA = a(str, b(str2));
            } catch (Throwable th) {
                Log.w("Aes128", "decrypt oaid ex: " + th.getClass().getSimpleName());
                return null;
            }
        }
        return strA;
    }

    public static String a(String str, byte[] bArr) {
        if (!TextUtils.isEmpty(str) && str.length() >= 32 && bArr != null && bArr.length != 0) {
            try {
                if (d()) {
                    return d(str, bArr);
                }
            } catch (Throwable th) {
                Log.w("Aes128", "fail to decrypt: " + th.getClass().getSimpleName());
            }
        }
        return "";
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            String hexString = Integer.toHexString(b3 & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static byte[] a() {
        return a(16);
    }

    public static byte[] a(int i2) {
        byte[] bArr = new byte[i2];
        c().nextBytes(bArr);
        return bArr;
    }

    public static byte[] a(Context context) {
        byte[] bArr;
        byte[] bArrC;
        synchronized (f7457b) {
            SoftReference<byte[]> softReference = f7458c;
            bArr = softReference != null ? softReference.get() : null;
            if (bArr == null) {
                try {
                    try {
                        bArrC = b(b(context));
                    } catch (UnsupportedEncodingException unused) {
                        Log.w("Aes128", "getWorkKeyBytes UnsupportedEncodingException");
                        bArrC = c(context);
                        bArr = bArrC;
                        f7458c = new SoftReference<>(bArr);
                        return bArr;
                    }
                } catch (Throwable th) {
                    Log.w("Aes128", "getWorkKeyBytes " + th.getClass().getSimpleName());
                    bArrC = c(context);
                    bArr = bArrC;
                    f7458c = new SoftReference<>(bArr);
                    return bArr;
                }
                bArr = bArrC;
                f7458c = new SoftReference<>(bArr);
            }
        }
        return bArr;
    }

    private static byte[] a(Context context, String str) {
        return a(str, context.getString(R.string.identifier_hiad_str_2), context.getString(R.string.identifier_hiad_str_3));
    }

    public static byte[] a(String str) {
        byte[] bArr = new byte[0];
        try {
            return b(str);
        } catch (Throwable th) {
            Log.e("Aes128", "hex string 2 byte: " + th.getClass().getSimpleName());
            return bArr;
        }
    }

    private static byte[] a(String str, String str2, String str3) {
        byte[] bArrA = a(str);
        byte[] bArrA2 = a(str2);
        return a(a(bArrA, bArrA2), a(str3));
    }

    private static byte[] a(String str, byte[] bArr, byte[] bArr2) {
        if (!TextUtils.isEmpty(str) && c(bArr) && b(bArr2) && d()) {
            try {
                return a(str.getBytes("UTF-8"), bArr, bArr2);
            } catch (UnsupportedEncodingException e2) {
                Log.e("Aes128", "GCM encrypt data error" + e2.getMessage());
            }
        } else {
            Log.i("Aes128", "gcm encrypt param is not right");
        }
        return new byte[0];
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr.length <= bArr2.length) {
            bArr2 = bArr;
            bArr = bArr2;
        }
        int length = bArr.length;
        int length2 = bArr2.length;
        byte[] bArr3 = new byte[length];
        int i2 = 0;
        while (i2 < length2) {
            bArr3[i2] = (byte) (bArr2[i2] ^ bArr[i2]);
            i2++;
        }
        while (i2 < bArr.length) {
            bArr3[i2] = bArr[i2];
            i2++;
        }
        return bArr3;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        String str;
        if (bArr == null || bArr.length == 0) {
            str = "encrypt, contentBytes invalid.";
        } else if (bArr2 == null || bArr2.length < 16) {
            str = "encrypt, keyBytes invalid.";
        } else if (!d()) {
            str = "encrypt, osVersion too low.";
        } else if (bArr3 == null || bArr3.length < 12) {
            str = "encrypt, random invalid.";
        } else {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(1, secretKeySpec, d(bArr3));
                return cipher.doFinal(bArr);
            } catch (GeneralSecurityException e2) {
                Log.e("Aes128", "GCM encrypt data error" + e2.getMessage());
            }
        }
        Log.i("Aes128", str);
        return new byte[0];
    }

    public static byte[] a(char[] cArr, byte[] bArr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance(Build.VERSION.SDK_INT > 26 ? "PBKDF2WithHmacSHA256" : "PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(cArr, bArr, 10000, 256)).getEncoded();
    }

    private static String b(int i2) {
        try {
            SecureRandom secureRandomC = c();
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < i2; i3++) {
                sb.append(Integer.toHexString(secureRandomC.nextInt(16)));
            }
            return sb.toString();
        } catch (Throwable th) {
            Log.w("Aes128", "generate aes key1 err:" + th.getClass().getSimpleName());
            return "";
        }
    }

    private static String b(Context context) {
        String strA;
        if (context == null) {
            return "";
        }
        synchronized (f7457b) {
            a aVarA = a.a(context);
            String strF = aVarA.f();
            if (strF != null) {
                String strA2 = a(strF, d(context));
                strA = TextUtils.isEmpty(strA2) ? a(context, aVarA) : strA2;
            }
        }
        return strA;
    }

    public static String b(String str, byte[] bArr) {
        StringBuilder sb;
        if (!TextUtils.isEmpty(str) && bArr != null && bArr.length != 0) {
            try {
                if (d()) {
                    return c(str, bArr);
                }
            } catch (Exception e2) {
                e = e2;
                sb = new StringBuilder();
                sb.append("fail to cipher: ");
                sb.append(e.getClass().getSimpleName());
                Log.w("Aes128", sb.toString());
                return "";
            } catch (Throwable th) {
                e = th;
                sb = new StringBuilder();
                sb.append("fail to cipher: ");
                sb.append(e.getClass().getSimpleName());
                Log.w("Aes128", sb.toString());
                return "";
            }
        }
        return "";
    }

    private static boolean b(byte[] bArr) {
        return bArr != null && bArr.length >= 12;
    }

    public static byte[] b() {
        return a(16);
    }

    public static byte[] b(String str) throws NumberFormatException, UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        int length = upperCase.length() / 2;
        byte[] bArr = new byte[length];
        byte[] bytes = upperCase.getBytes("UTF-8");
        for (int i2 = 0; i2 < length; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append("0x");
            int i3 = i2 * 2;
            sb.append(new String(new byte[]{bytes[i3]}, "UTF-8"));
            bArr[i2] = (byte) (((byte) (Byte.decode(sb.toString()).byteValue() << 4)) ^ Byte.decode("0x" + new String(new byte[]{bytes[i3 + 1]}, "UTF-8")).byteValue());
        }
        return bArr;
    }

    private static String c(String str) {
        if (!TextUtils.isEmpty(str) && str.length() >= 24) {
            return str.substring(0, 24);
        }
        Log.i("Aes128", "IV is invalid.");
        return "";
    }

    private static String c(String str, byte[] bArr) {
        byte[] bArrA;
        byte[] bArrA2;
        if (TextUtils.isEmpty(str) || bArr == null || bArr.length < 16 || !d() || (bArrA2 = a(str, bArr, (bArrA = a(12)))) == null || bArrA2.length == 0) {
            return "";
        }
        return a(bArrA) + a(bArrA2);
    }

    private static SecureRandom c() {
        SecureRandom instanceStrong;
        try {
            instanceStrong = Build.VERSION.SDK_INT >= 26 ? SecureRandom.getInstanceStrong() : SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e2) {
            Log.w("Aes128", "getInstanceStrong, exception: " + e2.getClass().getSimpleName());
            instanceStrong = null;
        }
        return instanceStrong == null ? new SecureRandom() : instanceStrong;
    }

    private static boolean c(byte[] bArr) {
        return bArr != null && bArr.length >= 16;
    }

    private static byte[] c(Context context) {
        Log.i("Aes128", "regenerateWorkKey");
        a.a(context).b("");
        return a(b(context));
    }

    private static String d(String str) {
        return (TextUtils.isEmpty(str) || str.length() < 24) ? "" : str.substring(24);
    }

    private static String d(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (!TextUtils.isEmpty(str) && bArr != null && bArr.length >= 16 && d()) {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                String strC = c(str);
                String strD = d(str);
                if (TextUtils.isEmpty(strC) || TextUtils.isEmpty(strD)) {
                    Log.i("Aes128", "ivParameter or encrypedWord is null");
                    return "";
                }
                cipher.init(2, secretKeySpec, d(a(strC)));
                return new String(cipher.doFinal(a(strD)), "UTF-8");
            } catch (UnsupportedEncodingException | GeneralSecurityException e2) {
                Log.e("Aes128", "GCM decrypt data exception: " + e2.getMessage());
            }
        }
        return "";
    }

    private static AlgorithmParameterSpec d(byte[] bArr) {
        return new GCMParameterSpec(128, bArr);
    }

    private static boolean d() {
        return true;
    }

    private static byte[] d(Context context) {
        String str;
        if (context == null) {
            return new byte[0];
        }
        a aVarA = a.a(context);
        try {
            return a(a(e(context)).toCharArray(), a(aVarA.g()));
        } catch (NoSuchAlgorithmException unused) {
            str = "get userRootKey NoSuchAlgorithmException";
            Log.w("Aes128", str);
            return null;
        } catch (InvalidKeySpecException unused2) {
            str = "get userRootKey InvalidKeySpecException";
            Log.w("Aes128", str);
            return null;
        }
    }

    private static byte[] e(Context context) {
        return a(context, f(context));
    }

    private static String f(Context context) {
        final a aVarA = a.a(context);
        String strH = aVarA.h();
        if (!TextUtils.isEmpty(strH)) {
            return strH;
        }
        final String strB = b(64);
        e.f7471a.execute(new Runnable() { // from class: com.huawei.hms.ads.identifier.d.1
            @Override // java.lang.Runnable
            public void run() {
                aVarA.d(strB);
            }
        });
        return strB;
    }
}
