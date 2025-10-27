package com.plv.foundationsdk.net.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/* loaded from: classes4.dex */
public interface PLVUrlApi {
    @GET
    Observable<ResponseBody> requestMarQueeUrl(@Url String str);

    @GET
    Observable<ResponseBody> requestQosUrl(@Url String str);

    @GET
    Observable<ResponseBody> requestUrl(@Url String str);
}
