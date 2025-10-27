package com.plv.socket.net.api;

import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.net.PLVApiHostConstant;
import com.plv.foundationsdk.net.PLVRetrofitHelper;
import okhttp3.OkHttpClient;

/* loaded from: classes5.dex */
public class PLVSocketApiManager {
    private static OkHttpClient createOkHttpClient() {
        return PLVRetrofitHelper.userAgentOkHttpClient();
    }

    public static PLVApiChatApi getApiChatApi() {
        return (PLVApiChatApi) PLVRetrofitHelper.createApi(PLVApiChatApi.class, PLVChatDomainManager.getInstance().getChatDomain().getChatApiDomain(), createOkHttpClient());
    }

    public static PLVHiClassApi getHiClassApi() {
        return (PLVHiClassApi) PLVRetrofitHelper.createApi(PLVHiClassApi.class, PLVApiHostConstant.API_HI_CLASS, createOkHttpClient());
    }

    public static PLVApiLiveApi getSocketApi() {
        return (PLVApiLiveApi) PLVRetrofitHelper.createApi(PLVApiLiveApi.class, "https://api.polyv.net/live/", createOkHttpClient());
    }
}
