package com.plv.business.net.api;

import com.plv.business.model.video.PLVVideoVO;
import com.plv.foundationsdk.net.PLVResponseBean;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/* loaded from: classes4.dex */
public interface PLVPlayerApi {
    @GET("service/lts3/enc_{userId}_{channelId}.json")
    Observable<PLVResponseBean> getChannelJsonEncrypt(@Path("userId") String str, @Path("channelId") String str2);

    @GET("/videojson/{vid}.json")
    Observable<PLVResponseBean> getVideoVO(@Path("vid") String str);

    @GET("/videojson/{vid}.js")
    Call<PLVVideoVO> getVideoVOSync(@Path("vid") String str);
}
