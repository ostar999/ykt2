package com.plv.livescenes.net.api;

import com.plv.livescenes.model.interact.PLVCardPushVO;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVLiveImagesApi {
    @GET("cardPush/{channelId}/{cardId}.json")
    Observable<PLVCardPushVO> getCardPushInfo(@Path("channelId") String str, @Path("cardId") String str2, @Query("timestamp") String str3);

    @POST("/")
    Observable<ResponseBody> uploadLiveImages(@Body RequestBody requestBody);
}
