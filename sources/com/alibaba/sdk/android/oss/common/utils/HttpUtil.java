package com.alibaba.sdk.android.oss.common.utils;

import com.xiaomi.mipush.sdk.Constants;
import java.net.URLEncoder;
import java.util.Map;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public class HttpUtil {
    public static String paramToQueryString(Map<String, String> map, String str) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!z2) {
                sb.append("&");
            }
            sb.append(urlEncode(key, str));
            if (value != null) {
                sb.append("=");
                sb.append(urlEncode(value, str));
            }
            z2 = false;
        }
        return sb.toString();
    }

    public static String urlEncode(String str, String str2) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, str2).replace(Marker.ANY_NON_NULL_MARKER, "%20").replace("*", "%2A").replace("%7E", Constants.WAVE_SEPARATOR).replace("%2F", "/");
        } catch (Exception e2) {
            throw new IllegalArgumentException("failed to encode url!", e2);
        }
    }
}
