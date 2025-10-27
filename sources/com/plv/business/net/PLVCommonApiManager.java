package com.plv.business.net;

import com.plv.business.net.api.PLVApiApi;
import com.plv.business.net.api.PLVElogApi;
import com.plv.business.net.api.PLVPlayerApi;
import com.plv.foundationsdk.net.PLVApiHostConstant;
import com.plv.foundationsdk.net.PLVRetrofitHelper;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public class PLVCommonApiManager {
    private static OkHttpClient createOkHttpClient() {
        return PLVRetrofitHelper.userAgentOkHttpClient();
    }

    public static PLVElogApi getElogApi() {
        return (PLVElogApi) PLVRetrofitHelper.createApi(PLVElogApi.class, PLVApiHostConstant.ELOG_PLV_NET, createOkHttpClient());
    }

    public static PLVApiApi getPlvApiApi() {
        return (PLVApiApi) PLVRetrofitHelper.createApi(PLVApiApi.class, "https://api.polyv.net/", createOkHttpClient());
    }

    public static PLVPlayerApi getPlvPlayerApi() {
        return (PLVPlayerApi) PLVRetrofitHelper.createApi(PLVPlayerApi.class, "https://player.polyv.net", createOkHttpClient());
    }
}
