package com.plv.socket.net.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVApiChatApi {
    @GET("front/heartbeat")
    Observable<ResponseBody> requestHeartbeat(@Query("uid") String str);
}
