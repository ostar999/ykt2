package com.plv.livescenes.net.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/* loaded from: classes5.dex */
public interface PLVNGBPushApi {
    @Headers({"WS_RETIP_NUM:1", "WS_URL_TYPE:3"})
    @GET
    Observable<ResponseBody> getNGBPushStreamUrl(@Header("WS_URL") String str, @Url String str2);
}
