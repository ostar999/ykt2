package com.aliyun.sls.android.producer.internal;

import com.alipay.sdk.util.h;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class HttpHeader {
    private static final String DEFAULT_UA = "sls-android-sdk/2.7.0";

    private HttpHeader() {
    }

    public static String[] getHeadersWithUA(String[] srcHeaders, String... ua) {
        String[] strArr = (String[]) Arrays.copyOf(srcHeaders, srcHeaders.length + 2);
        strArr[srcHeaders.length] = "User-agent";
        if (ua == null || ua.length == 0) {
            strArr[srcHeaders.length + 1] = DEFAULT_UA;
        } else {
            StringBuilder sb = new StringBuilder(DEFAULT_UA);
            for (String str : ua) {
                sb.append(h.f3376b);
                sb.append(str);
            }
            strArr[srcHeaders.length + 1] = sb.substring(0, sb.length());
        }
        return strArr;
    }
}
