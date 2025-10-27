package com.beizi.fusion.g;

import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes2.dex */
public class x {
    public static String a(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            return Base64.encodeToString(byteArray, 2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String b(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 0);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrDecode);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = gZIPInputStream.read(bArr, 0, 1024);
                if (i2 <= 0) {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toString();
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
