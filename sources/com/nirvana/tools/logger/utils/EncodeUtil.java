package com.nirvana.tools.logger.utils;

import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class EncodeUtil {
    public static String decode(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer(str);
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(str.length() / 2);
            int i2 = 0;
            while (i2 < str.length()) {
                int i3 = i2 + 1;
                int i4 = Integer.parseInt(stringBuffer.substring(i2, i3), 16) << 4;
                i2 += 2;
                byteBufferAllocate.put((byte) (Integer.parseInt(stringBuffer.substring(i3, i2), 16) | i4));
            }
            byte[] bArrArray = byteBufferAllocate.array();
            for (int i5 = 0; i5 < bArrArray.length; i5++) {
                if (i5 == bArrArray.length - 1) {
                    int length = bArrArray.length - 1;
                    bArrArray[length] = (byte) (bArrArray[length] ^ 98);
                } else {
                    byte b3 = bArrArray[i5];
                    byte b4 = bArrArray[i5 + 1];
                    if (b3 != b4) {
                        bArrArray[i5] = (byte) (b3 ^ b4);
                    }
                }
            }
            for (int length2 = bArrArray.length - 1; length2 >= 0; length2--) {
                if (length2 == 0) {
                    bArrArray[0] = (byte) (bArrArray[0] ^ 69);
                } else {
                    byte b5 = bArrArray[length2];
                    byte b6 = bArrArray[length2 - 1];
                    if (b5 != b6) {
                        bArrArray[length2] = (byte) (b5 ^ b6);
                    }
                }
            }
            return new String(bArrArray, "UTF-8");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 == 0) {
                bytes[0] = (byte) (bytes[0] ^ 69);
            } else {
                byte b3 = bytes[i2];
                byte b4 = bytes[i2 - 1];
                if (b3 != b4) {
                    bytes[i2] = (byte) (b3 ^ b4);
                }
            }
        }
        int i3 = length - 1;
        for (int i4 = i3; i4 >= 0; i4--) {
            if (i4 == i3) {
                bytes[i4] = (byte) (bytes[i4] ^ 98);
            } else {
                byte b5 = bytes[i4];
                byte b6 = bytes[i4 + 1];
                if (b5 != b6) {
                    bytes[i4] = (byte) (b5 ^ b6);
                }
            }
        }
        String str2 = "";
        for (byte b7 : bytes) {
            str2 = str2 + Integer.toHexString((b7 & 255) | InputDeviceCompat.SOURCE_ANY).substring(6);
        }
        return str2;
    }
}
