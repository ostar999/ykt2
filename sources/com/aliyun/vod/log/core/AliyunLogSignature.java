package com.aliyun.vod.log.core;

import android.text.TextUtils;
import com.aliyun.vod.log.util.Base64Coder;
import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public class AliyunLogSignature {

    public static class ParamsComparator implements Comparator<String> {
        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            return str.compareTo(str2);
        }
    }

    public static List<String> getAllParams(Map<String, String> map, Map<String, String> map2) {
        ArrayList arrayList = new ArrayList();
        if (map != null) {
            for (String str : map.keySet()) {
                String str2 = map.get(str);
                arrayList.add(percentEncode(str) + "=" + percentEncode(str2));
            }
        }
        if (map2 != null) {
            for (String str3 : map2.keySet()) {
                String str4 = map2.get(str3);
                if (!TextUtils.isEmpty(str4)) {
                    arrayList.add(percentEncode(str3) + "=" + percentEncode(str4));
                }
            }
        }
        return arrayList;
    }

    public static String getCQS(List<String> list) {
        Collections.sort(list, new ParamsComparator());
        String str = "";
        for (int i2 = 0; i2 < list.size(); i2++) {
            str = str + list.get(i2);
            if (i2 != list.size() - 1) {
                str = str + "&";
            }
        }
        return str;
    }

    public static String hmacSHA1Signature(String str, String str2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec((str + "&").getBytes(), "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(secretKeySpec);
                return new String(newStringByBase64(mac.doFinal(str2.getBytes())));
            } catch (Exception e2) {
                throw new SignatureException("Failed to generate HMAC : " + e2.getMessage());
            }
        } catch (SignatureException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static String newStringByBase64(byte[] bArr) throws UnsupportedEncodingException {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return new String(Base64Coder.encode(bArr, 2));
    }

    public static String percentEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace(Marker.ANY_NON_NULL_MARKER, "%20").replace("*", "%2A").replace("%7E", Constants.WAVE_SEPARATOR);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return str;
        }
    }
}
