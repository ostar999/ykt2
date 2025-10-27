package com.plv.livescenes.net.api;

import com.plv.foundationsdk.net.PLVResponseBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/* loaded from: classes5.dex */
public interface PLVLiveApi {
    @GET("service/lts3/enc_{userId}_{channelId}.json")
    Observable<PLVResponseBean> getChannelJsonEncrypt(@Path("userId") String str, @Path("channelId") String str2);
}
