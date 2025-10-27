package com.plv.livescenes.net.api;

import com.plv.livescenes.model.PLVLiveRestrictVO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/* loaded from: classes5.dex */
public interface PLVLiveJsonApi {
    @GET("service/v3/restrict_{userId}_{channelId}.json")
    Observable<PLVLiveRestrictVO> getRestrictJson(@Path("userId") String str, @Path("channelId") String str2);
}
