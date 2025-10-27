package com.plv.linkmic.repository.api;

import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.net.PLVApiHostConstant;
import com.plv.foundationsdk.net.PLVRetrofitHelper;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public class PLVLinkMicApiManager {
    private static OkHttpClient createOkHttpClient() {
        return PLVRetrofitHelper.userAgentOkHttpClient();
    }

    public static PLVApiLiveApi getApiLiveApi() {
        return (PLVApiLiveApi) PLVRetrofitHelper.createApi(PLVApiLiveApi.class, "https://api.polyv.net/live/", createOkHttpClient());
    }

    public static PLVHiClassApi getHiClassApi() {
        return (PLVHiClassApi) PLVRetrofitHelper.createApi(PLVHiClassApi.class, PLVApiHostConstant.API_HI_CLASS, createOkHttpClient());
    }

    public static PLVApiLiveApi getPolyvLinkMicApi() {
        return (PLVApiLiveApi) PLVRetrofitHelper.createApi(PLVApiLiveApi.class, "https://api.polyv.net/live/", createOkHttpClient());
    }

    public static PLVLinkMicApi getPolyvLinkMicChatApi() {
        return (PLVLinkMicApi) PLVRetrofitHelper.createApi(PLVLinkMicApi.class, PLVChatDomainManager.getInstance().getChatDomain().getChatApiDomain(), createOkHttpClient());
    }
}
