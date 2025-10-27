package com.aliyun.vod.log.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes2.dex */
public class CRC64Util {
    public static String calculateCrc64(String str) throws IOException {
        byte[] bArr = new byte[1024];
        CRC64 crc64 = new CRC64();
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            while (true) {
                int i2 = bufferedInputStream.read(bArr);
                if (i2 <= 0) {
                    fileInputStream.close();
                    bufferedInputStream.close();
                    return String.valueOf(crc64.getValue());
                }
                crc64.update(bArr, 0, i2);
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
