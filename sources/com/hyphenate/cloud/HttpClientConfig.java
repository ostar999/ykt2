package com.hyphenate.cloud;

import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.hyphenate.chat.EMClient;
import java.net.HttpURLConnection;
import java.util.Map;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class HttpClientConfig {
    private static final String EASEMOB_PLATFORM = "Android";
    private static final String EASEMOB_USERSERVER_DOMAIN_ID = "hyphenate.com";
    public static int EM_DEFAULT_TIMEOUT = 60000;
    public static String EM_TIME_OUT_KEY = "em_timeout";
    private static final String TAG = "HttpClientConfig";

    public static Map<String, String> addDomainToHeaders(Map<String, String> map) {
        return map;
    }

    public static void checkAndProcessSSL(String str, HttpURLConnection httpURLConnection) {
    }

    public static String getBaseUrlByAppKey() {
        return EMHttpClient.getInstance().chatConfig().a(true, false);
    }

    public static String getDefaultUserAgent() {
        StringBuilder sb = new StringBuilder();
        sb.append("Easemob-SDK(Android) ");
        EMClient.getInstance();
        sb.append(EMClient.VERSION);
        return sb.toString();
    }

    public static String getEaseMobUserServerDomainId() {
        return EASEMOB_USERSERVER_DOMAIN_ID;
    }

    public static String getFileDirRemoteUrl() {
        return getBaseUrlByAppKey() + "/chatfiles/";
    }

    public static String getFileRemoteUrl(String str) {
        if (str.startsWith("http")) {
            return str;
        }
        return getFileDirRemoteUrl() + str;
    }

    public static String getNewHost(String str, String str2) {
        String strSubstring = str.substring(str.indexOf("/", 8));
        String strSubstring2 = strSubstring.substring(strSubstring.indexOf("/", 1));
        return str2 + strSubstring2.substring(strSubstring2.indexOf("/", 1));
    }

    public static int getTimeout(Map<String, String> map) {
        int i2 = EM_DEFAULT_TIMEOUT;
        if (map == null || map.get(EM_TIME_OUT_KEY) == null) {
            return i2;
        }
        int iIntValue = Integer.valueOf(map.get(EM_TIME_OUT_KEY)).intValue();
        map.remove(EM_TIME_OUT_KEY);
        return iIntValue;
    }

    public static String processUrl(String str) {
        if (str.contains(Marker.ANY_NON_NULL_MARKER)) {
            str = str.replaceAll("\\+", "%2B");
        }
        return str.contains(DictionaryFactory.SHARP) ? str.replaceAll(DictionaryFactory.SHARP, "%23") : str;
    }
}
