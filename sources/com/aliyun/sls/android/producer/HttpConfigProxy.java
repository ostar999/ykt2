package com.aliyun.sls.android.producer;

import com.alipay.sdk.util.h;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class HttpConfigProxy {
    private static final Map<String, String> PLUGIN_USER_AGENTS = new LinkedHashMap();

    static {
        addPluginUserAgent("sls-android-sdk", BuildConfig.VERSION_NAME);
    }

    private HttpConfigProxy() {
    }

    public static void addPluginUserAgent(String plugin, String version) {
        PLUGIN_USER_AGENTS.put(plugin, version);
    }

    public static String getUserAgent() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : PLUGIN_USER_AGENTS.entrySet()) {
            sb.append(entry.getKey());
            sb.append("/");
            sb.append(entry.getValue());
            sb.append(h.f3376b);
        }
        return sb.substring(0, sb.length() - 1);
    }
}
