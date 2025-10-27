package com.xiaomi.push;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class ay {
    public static String a(int i2) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    public static String a(String str) throws NoSuchAlgorithmException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(m210a(str));
            return String.format("%1$032X", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException unused) {
            return str;
        }
    }

    public static String a(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        if (i2 <= 0 || length < i2) {
            i2 = length / 3;
            if (i2 <= 1) {
                i2 = 1;
            }
            if (i2 > 3) {
                i2 = 3;
            }
        }
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 + 1;
            if (i4 % i2 == 0) {
                sb.append("*");
            } else {
                sb.append(str.charAt(i3));
            }
            i3 = i4;
        }
        return sb.toString();
    }

    public static String a(Collection<?> collection, String str) {
        if (collection == null) {
            return null;
        }
        return a(collection.iterator(), str);
    }

    public static String a(Iterator<?> it, String str) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return next.toString();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        if (next != null) {
            stringBuffer.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                stringBuffer.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                stringBuffer.append(next2);
            }
        }
        return stringBuffer.toString();
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr);
        }
    }

    public static String a(Object[] objArr, String str) {
        if (objArr == null) {
            return null;
        }
        return a(objArr, str, 0, objArr.length);
    }

    public static String a(Object[] objArr, String str, int i2, int i3) {
        if (objArr == null) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        int i4 = i3 - i2;
        if (i4 <= 0) {
            return "";
        }
        Object obj = objArr[i2];
        StringBuffer stringBuffer = new StringBuffer(i4 * ((obj == null ? 16 : obj.toString().length()) + str.length()));
        for (int i5 = i2; i5 < i3; i5++) {
            if (i5 > i2) {
                stringBuffer.append(str);
            }
            Object obj2 = objArr[i5];
            if (obj2 != null) {
                stringBuffer.append(obj2);
            }
        }
        return stringBuffer.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m209a(String str) {
        if (str == null) {
            return true;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 0 || cCharAt > 127) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m210a(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    public static String b(String str) throws NoSuchAlgorithmException {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(m210a(str));
            return String.format("%1$032X", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException unused) {
            return str;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m211b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("^[A-Za-z0-9]+$").matcher(str).matches();
    }

    public static String c(String str) {
        return TextUtils.isEmpty(str) ? "" : str.toUpperCase();
    }

    /* renamed from: c, reason: collision with other method in class */
    public static boolean m212c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char cCharAt = str.charAt(0);
        for (int i2 = 1; i2 < str.length(); i2++) {
            if (str.charAt(i2) != cCharAt) {
                return false;
            }
        }
        return true;
    }
}
