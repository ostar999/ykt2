package com.beizi.ad.internal.c;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.beizi.ad.internal.utilities.HaoboLog;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class n {
    public static String a(String str) {
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (TextUtils.isEmpty(fileExtensionFromUrl)) {
            return null;
        }
        return singleton.getMimeTypeFromExtension(fileExtensionFromUrl);
    }

    public static String b(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Error encoding url", e2);
        }
    }

    public static String c(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Error decoding url", e2);
        }
    }

    public static String d(String str) {
        try {
            return a(MessageDigest.getInstance("MD5").digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public static void a(byte[] bArr, long j2, int i2) {
        k.a(bArr, "Buffer must be not null!");
        k.a(j2 >= 0, "Data offset must be positive!");
        k.a(i2 >= 0 && i2 <= bArr.length, "Length must be in range [0..buffer.length]");
    }

    public static void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
                HaoboLog.e(HaoboLog.proxyCacheUtilsLogTag, "Error closing resource", e2);
            }
        }
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b3 : bArr) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(b3)));
        }
        return stringBuffer.toString();
    }
}
