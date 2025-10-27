package com.plv.foundationsdk.net.api;

import com.plv.foundationsdk.net.PLVApiHostConstant;
import com.plv.foundationsdk.net.PLVRetrofitHelper;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public class PLVFoundationApiManager {
    private static OkHttpClient createOkHttpClient() {
        return PLVRetrofitHelper.userAgentOkHttpClient();
    }

    public static PLVFoundationApi getFoundationApi() {
        return (PLVFoundationApi) PLVRetrofitHelper.createApi(PLVFoundationApi.class, "https://api.polyv.net/", createOkHttpClient());
    }

    public static PLVUrlApi getPlvUrlApi() {
        return (PLVUrlApi) PLVRetrofitHelper.createApi(PLVUrlApi.class, PLVApiHostConstant.COMPANY_URL, createOkHttpClient());
    }
}
