package com.plv.foundationsdk.utils;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVOkHttpDns;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class PLVDnsUtil {
    private static final String IP_HTTP_PATTERN = "^http://((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
    private static final String IP_PATTERN = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
    private static final String TAG = "PolyvDnsUtil";
    private static final String URL_HOST_REG = "([\\da-z\\.-]+)\\.([a-z\\.]{2,6})";
    private static final String HTTP_HOST_REG = "^(https?:\\/\\/)+([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w\\.-]*)(\\?\\w*=.*)?";
    private static final Pattern HTTP_PATTERN = Pattern.compile(HTTP_HOST_REG);
    private static final Pattern TS_PATTERN = Pattern.compile("https?://.*\\.ts");

    public static String getIP(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (HTTP_PATTERN.matcher(str).find()) {
            try {
                String ipFromHost = getIpFromHost(new URL(str).getHost());
                return ipFromHost != null ? ipFromHost : "";
            } catch (MalformedURLException unused) {
                return getIpFromHost(str);
            }
        }
        PLVCommonLog.d(TAG, "connot find host :" + str);
        return getIpFromHost(str);
    }

    public static String getIPByUrl(String str) {
        URL url;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            url = new URL(str);
        } catch (MalformedURLException unused) {
            url = null;
        }
        if (url == null) {
            return "";
        }
        String host = url.getHost();
        if (!host.contains("videocc.net")) {
            return "";
        }
        String ip = PLVOkHttpDns.getInstance().getIp(host);
        return !TextUtils.isEmpty(ip) ? ip : "";
    }

    @Nullable
    private static String getIpFromHost(String str) {
        String ip = PLVOkHttpDns.getInstance().getIp(str);
        PLVCommonLog.d(TAG, "httpdns  getIP: " + ip + "  from host :" + str);
        return !TextUtils.isEmpty(ip) ? ip : "";
    }

    public static String getUrlHost(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new URL(str).getHost();
        } catch (MalformedURLException unused) {
            return str;
        }
    }

    public static boolean isIpAddr(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile(IP_PATTERN).matcher(str).find();
    }

    public static boolean isIpHost(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile(IP_HTTP_PATTERN).matcher(str).find();
    }

    public static String tsReplaceInM3U8(String str) {
        URL url;
        Matcher matcher = TS_PATTERN.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        try {
            url = new URL(matcher.group());
        } catch (MalformedURLException unused) {
            url = null;
        }
        if (url == null || "https".equalsIgnoreCase(url.getProtocol())) {
            return str;
        }
        String host = url.getHost();
        if (!host.contains("videocc.net")) {
            return str;
        }
        String ip = PLVOkHttpDns.getInstance().getIp(host);
        if (TextUtils.isEmpty(ip)) {
            return str;
        }
        return str.replaceAll(host, ip + "/" + host);
    }
}
