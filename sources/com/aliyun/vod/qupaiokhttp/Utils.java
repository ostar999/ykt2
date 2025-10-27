package com.aliyun.vod.qupaiokhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/* loaded from: classes2.dex */
class Utils {
    public static String getFullUrl(String str, List<Part> list, boolean z2) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        int i2 = 0;
        if (stringBuffer.indexOf("?", 0) < 0 && list.size() > 0) {
            stringBuffer.append("?");
        }
        for (Part part : list) {
            String key = part.getKey();
            String value = part.getValue();
            if (z2) {
                try {
                    key = URLEncoder.encode(key, "UTF-8");
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
            stringBuffer.append(key);
            stringBuffer.append("=");
            stringBuffer.append(value);
            i2++;
            if (i2 != list.size()) {
                stringBuffer.append("&");
            }
        }
        return stringBuffer.toString();
    }
}
