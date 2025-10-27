package com.meizu.cloud.pushsdk.platform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class b {
    public static String a(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b3 : bArrDigest) {
                int i2 = b3 & 255;
                if (i2 < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(Map<String, String> map, String str) {
        Set<Map.Entry> setEntrySet = new TreeMap(map).entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : setEntrySet) {
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append((String) entry.getValue());
        }
        sb.append(str);
        return a(sb.toString());
    }
}
