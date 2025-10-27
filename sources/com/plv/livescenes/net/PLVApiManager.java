package com.plv.livescenes.net;

import com.plv.business.net.api.PLVApiLiveApi;
import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.net.PLVApiHostConstant;
import com.plv.foundationsdk.net.PLVRetrofitHelper;
import com.plv.foundationsdk.net.PLVRfProgressListener;
import com.plv.livescenes.net.api.PLVApichatApi;
import com.plv.livescenes.net.api.PLVDocumentApi;
import com.plv.livescenes.net.api.PLVElogApi;
import com.plv.livescenes.net.api.PLVHiClassApi;
import com.plv.livescenes.net.api.PLVLiveApi;
import com.plv.livescenes.net.api.PLVLiveImagesApi;
import com.plv.livescenes.net.api.PLVLiveJsonApi;
import com.plv.livescenes.net.api.PLVLiveStatusApi;
import com.plv.livescenes.net.api.PLVLogApi;
import com.plv.livescenes.net.api.PLVNGBPushApi;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/* loaded from: classes5.dex */
public class PLVApiManager {
    private static OkHttpClient createOkHttpClient() {
        return PLVRetrofitHelper.userAgentOkHttpClient();
    }

    public static PLVDocumentApi getDocumentApi() {
        return (PLVDocumentApi) PLVRetrofitHelper.createApi(PLVDocumentApi.class, "https://document-2.polyv.net/", createOkHttpClient());
    }

    public static PLVElogApi getElogApi() {
        return (PLVElogApi) PLVRetrofitHelper.createApi(PLVElogApi.class, PLVApiHostConstant.ELOG_PLV_NET, createOkHttpClient());
    }

    public static PLVHiClassApi getHiClassApi() {
        return (PLVHiClassApi) PLVRetrofitHelper.createApi(PLVHiClassApi.class, PLVApiHostConstant.API_HI_CLASS, createOkHttpClient());
    }

    public static PLVLiveJsonApi getLiveJosnNetApi() {
        return (PLVLiveJsonApi) PLVRetrofitHelper.createApi(PLVLiveJsonApi.class, "https://livejson.polyv.net/", createOkHttpClient());
    }

    public static PLVNGBPushApi getNGBPushApi() {
        return (PLVNGBPushApi) PLVRetrofitHelper.createApi(PLVNGBPushApi.class, "https://sdkoptedge.chinanetcenter.com/", createOkHttpClient());
    }

    public static PLVApiLiveApi getPlvApiLiveApi() {
        return (PLVApiLiveApi) PLVRetrofitHelper.createApi(PLVApiLiveApi.class, "https://api.polyv.net/live/", createOkHttpClient());
    }

    public static PLVApichatApi getPlvApichatApi() {
        return (PLVApichatApi) PLVRetrofitHelper.createApi(PLVApichatApi.class, PLVChatDomainManager.getInstance().getChatDomain().getChatApiDomain(), createOkHttpClient());
    }

    public static PLVLiveStatusApi getPlvChannelStatusApi() {
        return (PLVLiveStatusApi) PLVRetrofitHelper.createApi(PLVLiveStatusApi.class, PLVApiHostConstant.LIVE_PLV_CN, createOkHttpClient());
    }

    public static PLVApichatApi getPlvChaptersApis() {
        return (PLVApichatApi) PLVRetrofitHelper.createApi(PLVApichatApi.class, PLVChatDomainManager.getInstance().getChatDomain().getChatApiDomain(), createOkHttpClient());
    }

    public static PLVLiveApi getPlvLiveApi() {
        return (PLVLiveApi) PLVRetrofitHelper.createApi(PLVLiveApi.class, "https://live.polyv.net/", createOkHttpClient());
    }

    public static PLVLiveImagesApi getPlvLiveImagesApi(RequestBody requestBody, PLVRfProgressListener pLVRfProgressListener) {
        return (PLVLiveImagesApi) PLVRetrofitHelper.createApi(PLVLiveImagesApi.class, "https://liveimages.videocc.net/", PLVRetrofitHelper.progressOkhttpClient(requestBody, pLVRfProgressListener));
    }

    public static PLVLiveStatusApi getPlvLiveStatusApi() {
        return (PLVLiveStatusApi) PLVRetrofitHelper.createApi(PLVLiveStatusApi.class, "https://api.polyv.net/", createOkHttpClient());
    }

    public static PLVLogApi getPlvLogApi() {
        return (PLVLogApi) PLVRetrofitHelper.createApi(PLVLogApi.class, "https://api.polyv.net/", createOkHttpClient());
    }

    public static PLVLiveImagesApi getPlvLiveImagesApi() {
        return (PLVLiveImagesApi) PLVRetrofitHelper.createApi(PLVLiveImagesApi.class, "https://liveimages.videocc.net/", createOkHttpClient());
    }
}
