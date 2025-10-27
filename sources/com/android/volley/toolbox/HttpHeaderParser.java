package com.android.volley.toolbox;

import com.alipay.sdk.util.h;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes2.dex */
public class HttpHeaderParser {
    public static Cache.Entry parseCacheHeaders(NetworkResponse networkResponse) throws NumberFormatException {
        long j2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = networkResponse.headers;
        String str = map.get("Date");
        long j3 = 0;
        long dateAsEpoch = str != null ? parseDateAsEpoch(str) : 0L;
        String str2 = map.get("Cache-Control");
        int i2 = 0;
        if (str2 != null) {
            String[] strArrSplit = str2.split(",");
            j2 = 0;
            while (i2 < strArrSplit.length) {
                String strTrim = strArrSplit[i2].trim();
                if (strTrim.equals(HttpHeaderValues.NO_CACHE) || strTrim.equals("no-store")) {
                    return null;
                }
                if (strTrim.startsWith("max-age=")) {
                    try {
                        j2 = Long.parseLong(strTrim.substring(8));
                    } catch (Exception unused) {
                    }
                } else if (strTrim.equals("must-revalidate") || strTrim.equals("proxy-revalidate")) {
                    j2 = 0;
                }
                i2++;
            }
            i2 = 1;
        } else {
            j2 = 0;
        }
        String str3 = map.get("Expires");
        long dateAsEpoch2 = str3 != null ? parseDateAsEpoch(str3) : 0L;
        String str4 = map.get("ETag");
        if (i2 != 0) {
            j3 = jCurrentTimeMillis + (j2 * 1000);
        } else if (dateAsEpoch > 0 && dateAsEpoch2 >= dateAsEpoch) {
            j3 = jCurrentTimeMillis + (dateAsEpoch2 - dateAsEpoch);
        }
        Cache.Entry entry = new Cache.Entry();
        entry.data = networkResponse.data;
        entry.etag = str4;
        entry.softTtl = j3;
        entry.ttl = j3;
        entry.serverDate = dateAsEpoch;
        entry.responseHeaders = map;
        return entry;
    }

    public static String parseCharset(Map<String, String> map) {
        String str = map.get("Content-Type");
        if (str == null) {
            return "ISO-8859-1";
        }
        String[] strArrSplit = str.split(h.f3376b);
        for (int i2 = 1; i2 < strArrSplit.length; i2++) {
            String[] strArrSplit2 = strArrSplit[i2].trim().split("=");
            if (strArrSplit2.length == 2 && strArrSplit2[0].equals("charset")) {
                return strArrSplit2[1];
            }
        }
        return "ISO-8859-1";
    }

    public static long parseDateAsEpoch(String str) {
        try {
            return DateUtils.parseDate(str).getTime();
        } catch (DateParseException unused) {
            return 0L;
        }
    }
}
